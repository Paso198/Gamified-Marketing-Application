package it.polimi.db2.questionnaire.mappers;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.requests.AnswerRequest;
import it.polimi.db2.questionnaire.dto.responses.AnswerResponse;
import it.polimi.db2.questionnaire.exceptions.InvalidResponseException;
import it.polimi.db2.questionnaire.model.Answer;
import it.polimi.db2.questionnaire.model.Question;

@Mapper(componentModel = "spring", uses = QuestionMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class AnswerMapper {
	
	@Mapping(target="id", source="id")
	@Mapping(target="text", source="text")
	@Mapping(target="question", source="question")
	public abstract AnswerResponse toAnswerResponse(Answer answer);
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="text", source="answerRequest.text")
	@Mapping(target="question", expression="java(addQuestion(answerRequest.getQuestionId(), questions))")
	@Mapping(target="response", ignore=true)
	public abstract  Answer toAnswer(AnswerRequest answerRequest, List<Question> questions);
	
	protected Question addQuestion(Long questionId, List<Question> questions) {
		Question toAdd = new Question();
		Boolean added = false;
		for(Question question:questions){
			if(question.getId().equals(questionId)) { 
				toAdd = question;
				added = true;
				break;
			}
		}
		if(added) return toAdd;
		else throw new InvalidResponseException("Response contains answers to questions that are not present in this questionnaire");
	}
	
	public abstract List<AnswerResponse> toAnswersResponse(List<Answer> answers);
	
	public abstract ArrayList<Answer> toAnswers(List<AnswerRequest> answersRequests, List<Question> questions);
}
