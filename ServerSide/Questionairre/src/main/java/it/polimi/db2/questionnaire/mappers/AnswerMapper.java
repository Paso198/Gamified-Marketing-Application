package it.polimi.db2.questionnaire.mappers;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.requests.AnswerRequest;
import it.polimi.db2.questionnaire.dto.responses.AnswerResponse;
import it.polimi.db2.questionnaire.model.Answer;
import it.polimi.db2.questionnaire.model.Question;

@Mapper(componentModel = "spring", uses = QuestionMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AnswerMapper {
	
	@Mapping(target="id", source="id")
	@Mapping(target="text", source="text")
	@Mapping(target="question", source="question")
	public  AnswerResponse toAnswerResponse(Answer answer);
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="text", source="answerRequest.text")
	@Mapping(target="response", ignore=true)
	@Mapping(target="question", source="question")
	public abstract  Answer toAnswer(AnswerRequest answerRequest, Question question);
	
	
	public  List<AnswerResponse> toAnswersResponse(List<Answer> answers);
	
	default List<Answer> toAnswers(List<AnswerRequest> answersRequests, List<Question> questions){
		List<Answer> answers = new ArrayList<Answer>();
		answersRequests.forEach((a)->answers.add(Answer.builder()
				.text(a.getText())
				.question(questions.stream()
						.filter((q)->q.getId().equals(a.getQuestionId()))
						.findFirst()
						.get())
				.build()));
		return answers;
	};
}
