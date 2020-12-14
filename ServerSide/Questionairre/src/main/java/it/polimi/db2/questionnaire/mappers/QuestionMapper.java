package it.polimi.db2.questionnaire.mappers;

import org.mapstruct.Mapper;

import it.polimi.db2.questionnaire.dto.responses.QuestionResponse;
import it.polimi.db2.questionnaire.model.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
	public QuestionResponse toQuestionResponse(Question question);
}
