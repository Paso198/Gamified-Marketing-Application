package it.polimi.db2.questionnaire.mappers;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.requests.AnswerRequest;
import it.polimi.db2.questionnaire.dto.responses.AnswerResponse;
import it.polimi.db2.questionnaire.model.Answer;

@Mapper(componentModel = "spring", uses = QuestionMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AnswerMapper {
	
	@Mapping(target="id", source="id")
	@Mapping(target="text", source="text")
	@Mapping(target="question", source="question")
	public AnswerResponse toAnswerResponse(Answer answer);
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="text", source="text")
	@Mapping(target="question", ignore=true)
	@Mapping(target="response", ignore=true)
	public Answer toAnswer(AnswerRequest answerRequest);
	
	public List<AnswerResponse> toAnswersResponse(List<Answer> answers);
	
	public List<Answer> toAnswers(List<AnswerRequest> answerRequest);
}
