package it.polimi.db2.questionnaire.services;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import it.polimi.db2.questionnaire.dto.requests.AnswerRequest;
import it.polimi.db2.questionnaire.dto.requests.ResponseRequest;
import it.polimi.db2.questionnaire.mappers.ResponseMapper;
import it.polimi.db2.questionnaire.repositories.ResponseRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResponseService {
	private final ResponseRepository responseRepository;
	private final BadWordService badWordService;
	private final UserService userService;
	private final ResponseMapper responseMapper;

	public void addReponse(@Valid ResponseRequest request) {
		if (!badWordService //TODO split answers in list of words
				.containtsBadWord(request.getAnswers().stream().map(AnswerRequest::getText).collect(Collectors.toList())))
				responseRepository.save(responseMapper.toResponse(request));
		else
			userService.blockLogged();
	}
}
