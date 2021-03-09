package it.polimi.db2.questionnaire.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.responses.QuestionResponse;
import it.polimi.db2.questionnaire.model.Question;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class QuestionMapper {

	@Mapping(target="id", source="id")
	@Mapping(target="text", source="text")
	public abstract QuestionResponse toQuestionResponse(Question question);
}
