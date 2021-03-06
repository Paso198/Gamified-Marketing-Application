package it.polimi.db2.questionnaire.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.AnswerRequest;
import it.polimi.db2.questionnaire.dto.requests.ResponseRequest;
import it.polimi.db2.questionnaire.dto.responses.ResponseResponse;
import it.polimi.db2.questionnaire.exceptions.QuestionnaireNotFoundException;
import it.polimi.db2.questionnaire.exceptions.UnloggedUserException;
import it.polimi.db2.questionnaire.mappers.ResponseMapper;
import it.polimi.db2.questionnaire.repositories.ResponseRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResponseService {
	private final ResponseRepository responseRepository;
	private final BadWordService badWordService;
	private final QuestionnaireService questionnaireService;
	private final UserService userService;
	private final ResponseMapper responseMapper;

	@Transactional
	public void addReponse(ResponseRequest request) {
		if (!badWordService //TODO split answers in list of words
				.containtsBadWord(request.getAnswers().stream().map(AnswerRequest::getText).collect(Collectors.toList())))
				responseRepository.save(responseMapper.toResponse(request, 
						questionnaireService.getQuestionnaire(request.getQuestionnaireId()).orElseThrow(()->new QuestionnaireNotFoundException("invalid id", "questionnaire not found")), 
						userService.getLoggedUser().orElseThrow(() -> new UnloggedUserException("Not user currently logged in"))));
		else
			userService.blockLogged();
	}
	
	@Transactional(readOnly = true)
	public ResponseResponse getUserResponse(Long userId, Long questionnaireId) {
		return responseMapper.toResponseResponse(responseRepository.findByQuestionnaire_IdAndUser_Id(questionnaireId, userId)
				.orElseThrow(()->new QuestionnaireNotFoundException("Invalid userId or questionnaireId", "questionnaire not found")));
	}
}
