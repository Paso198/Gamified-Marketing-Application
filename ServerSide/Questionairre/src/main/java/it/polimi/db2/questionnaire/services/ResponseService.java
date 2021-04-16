package it.polimi.db2.questionnaire.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.AnswerRequest;
import it.polimi.db2.questionnaire.dto.requests.ResponseRequest;
import it.polimi.db2.questionnaire.dto.responses.LeaderboardUserResponse;
import it.polimi.db2.questionnaire.dto.responses.ResponseResponse;
import it.polimi.db2.questionnaire.exceptions.BadWordException;
import it.polimi.db2.questionnaire.exceptions.DuplicateUniqueValueException;
import it.polimi.db2.questionnaire.exceptions.QuestionnaireNotFoundException;
import it.polimi.db2.questionnaire.exceptions.UnloggedUserException;
import it.polimi.db2.questionnaire.mappers.ResponseMapper;
import it.polimi.db2.questionnaire.mappers.UserMapper;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.model.Response;
import it.polimi.db2.questionnaire.model.User;
import it.polimi.db2.questionnaire.repositories.ResponseRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResponseService {
	private final ResponseRepository responseRepository;
	private final BadWordService badWordService;
	private final QuestionnaireService questionnaireService;
	private final UserService userService;
	private final LogService logService;
	private final ResponseMapper responseMapper;
	private final UserMapper userMapper;

	@Transactional
	public void addReponse(ResponseRequest request) {
		verifyDuplicate(request.getQuestionnaireId());
		if (request.getAnswers().stream()
				.map(AnswerRequest::getText)
				.allMatch((s)->!badWordService
						.containtsBadWord(List.of(s.split("\\W+"))))) {
			
			Questionnaire questionnaire = questionnaireService.getQuestionnaire(request.getQuestionnaireId())
					.orElseThrow(()->new QuestionnaireNotFoundException("invalid id", "questionnaire not found"));
			User user = userService.getLoggedUser()
					.orElseThrow(() -> new UnloggedUserException("Not user currently logged in"));
			
				responseRepository.save(responseMapper.toResponse(request, questionnaire, user));
				logService.logSubmission(user, questionnaire);
				
		}else{
			userService.blockLogged();
			throw new BadWordException("Response not accepted", "Bad words are not allowed");
		}
	}
	
	@Transactional(readOnly = true)
	public ResponseResponse getUserResponse(Long userId, Long questionnaireId) {
		return responseMapper.toResponseResponse(responseRepository.findByQuestionnaire_IdAndUser_Id(questionnaireId, userId)
				.orElseThrow(()->new QuestionnaireNotFoundException("Invalid userId or questionnaireId", "questionnaire not found")));
	}
	
	@Transactional(readOnly = true)
	private List<Response> getResponsesOfTheDay() {
		return responseRepository.findResponsesOfTheDay();
	}
	
	@Transactional(readOnly = true)
	public List<LeaderboardUserResponse> getLeaderboard() {
		return userMapper.toLeaderboardUsersResponse(getResponsesOfTheDay());
	}
	
	@Transactional(readOnly = true)
	private void verifyDuplicate(Long questionnaireId) {
		responseRepository.findByQuestionnaire_IdAndUser_Id(questionnaireId, userService.getLoggedUser().orElseThrow(() -> new UnloggedUserException("Not user currently logged in")).getId())
		.ifPresent(u->{throw new DuplicateUniqueValueException("Questionnaire", "You already submitted a response for this questionnaire");});
	}
	
	
}
