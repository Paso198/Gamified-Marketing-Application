package it.polimi.db2.questionnaire.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.requests.ResponseRequest;
import it.polimi.db2.questionnaire.dto.responses.ResponseResponse;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.model.Response;
import it.polimi.db2.questionnaire.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ResponseMapper {

	protected AnswerMapper answerMapper;

	@Mapping(target="id", ignore=true)
	@Mapping(target="age", source="responseRequest.age")
	@Mapping(target="gender", source="responseRequest.gender")
	@Mapping(target="expertiseLevel", source="responseRequest.expertiseLevel")
	@Mapping(target="answers", expression="java(answerMapper.toAnswers(responseRequest.answers))")
	@Mapping(target="points", ignore=true)
	@Mapping(target="user", source="user")
	@Mapping(target="questionnaire", source="questionnaire")
	public abstract Response toResponse(ResponseRequest responseRequest, Questionnaire questionnaire, User user);

	//TODO
	public abstract ResponseResponse toResponseResponse(Response response);
}
