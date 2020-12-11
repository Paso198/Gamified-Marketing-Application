package it.polimi.db2.questionnaire.services;

import org.springframework.stereotype.Service;

import it.polimi.db2.questionnaire.repositories.QuestionnaireRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionnaireService {
	
	private final QuestionnaireRepository questionnaireRepository;
	
	public void addQuestionnaire() {
		
	}
}
