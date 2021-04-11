package it.polimi.db2.questionnaire.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.enumerations.Action;
import it.polimi.db2.questionnaire.model.Log;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.model.User;
import it.polimi.db2.questionnaire.repositories.LogRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogService {

	private final LogRepository logRepository;
	
	@Transactional
	public void logLogin(User user) {
		logRepository.save(Log.builder()
				.action(Action.LOGIN)
				.user(user)
				.build());
	}
	
	@Transactional
	public void logCancellation(User user, Questionnaire questionnaire) {
		logRepository.save(Log.builder()
				.action(Action.CANCEL_QUESTIONNAIRE)
				.user(user)
				.questionnaire(questionnaire)
				.build());
	}
	
	@Transactional
	public void logSubmission(User user, Questionnaire questionnaire) {
		logRepository.save(Log.builder()
				.action(Action.SEND_QUESTIONNAIRE)
				.user(user)
				.questionnaire(questionnaire)
				.build());
	}
}
