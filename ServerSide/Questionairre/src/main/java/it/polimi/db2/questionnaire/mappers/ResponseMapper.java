package it.polimi.db2.questionnaire.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.requests.ResponseRequest;
import it.polimi.db2.questionnaire.dto.responses.ResponseResponse;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.model.Response;
import it.polimi.db2.questionnaire.model.User;

@Mapper(componentModel = "spring", uses = AnswerMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ResponseMapper {

	@Mapping(target="id", ignore=true)
	@Mapping(target="age", source="responseRequest.age")
	@Mapping(target="gender", source="responseRequest.gender")
	@Mapping(target="expertiseLevel", source="responseRequest.expertiseLevel")
	@Mapping(target="answers", source="responseRequest.answers")
	@Mapping(target="points", ignore=true)
	@Mapping(target="user", source="user")
	@Mapping(target="questionnaire", source="questionnaire")
	public Response toResponse(ResponseRequest responseRequest, Questionnaire questionnaire, User user);

	//TODO
	public ResponseResponse toResponseResponse(Response response);
}
