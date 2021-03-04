package it.polimi.db2.questionnaire.services;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.polimi.db2.questionnaire.model.Answer;
import it.polimi.db2.questionnaire.model.Response;
import it.polimi.db2.questionnaire.repositories.ResponseRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResponseService {
	private final ResponseRepository responseRepository;
	private final BadWordService badWordService;
	private final UserService userService;

	public void addReponse(Response response) { // TODO: DTO
		if (!badWordService //TODO split answers in list of words
				.containtsBadWord(response.getAnswers().stream().map(Answer::getText).collect(Collectors.toList())))
				responseRepository.save(response);
		else
			userService.blockLogged();
	}
}
