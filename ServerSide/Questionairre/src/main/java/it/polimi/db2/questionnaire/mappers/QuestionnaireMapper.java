package it.polimi.db2.questionnaire.mappers;

import org.springframework.beans.factory.annotation.Autowired;

import it.polimi.db2.questionnaire.dto.responses.QuestionnaireOfTheDayResponse;
import it.polimi.db2.questionnaire.model.Questionnaire;

public abstract class QuestionnaireMapper {
	@Autowired
	QuestionMapper questionMapper;
	
	@Autowired
	QuestionnaireMapper questionMapper;
	
	
	public abstract QuestionnaireOfTheDayResponse toQuestionnaireOfTheDayResponse(Questionnaire questionnaire);
}
