package it.polimi.db2.questionnaire.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import it.polimi.db2.questionnaire.dto.requests.ResponseRequest;
import it.polimi.db2.questionnaire.model.Response;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ResponseMapper {

	protected AnswerMapper answerMapper;

	//TODO mapping
	public abstract Response toResponse(ResponseRequest responseRequest);
}
