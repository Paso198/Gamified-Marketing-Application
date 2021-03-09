package it.polimi.db2.questionnaire.mappers;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.requests.AnswerRequest;
import it.polimi.db2.questionnaire.dto.responses.AnswerResponse;
import it.polimi.db2.questionnaire.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Mapper(componentModel = "spring", uses = QuestionMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class AnswerMapper {

	protected QuestionMapper questionMapper;
	
	@Mapping(target="id", source="id")
	@Mapping(target="text", source="text")
	@Mapping(target="question", expression="java(questionMapper.toQuestionResponse(answer.getQuestion()))")
	public abstract AnswerResponse toAnswerResponse(Answer answer);
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="text", source="answerRequest.text")
	@Mapping(target="question", ignore=true)
	@Mapping(target="response", ignore=true)
	public abstract Answer toAnswer(AnswerRequest answerRequest);
	
	public abstract List<AnswerResponse> toAnswersResponse(List<Answer> answers);
	
	public abstract List<Answer> toAnswers(List<AnswerRequest> answerRequest);
}
