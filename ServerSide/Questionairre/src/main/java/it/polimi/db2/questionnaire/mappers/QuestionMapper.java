package it.polimi.db2.questionnaire.mappers;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.requests.QuestionRequest;
import it.polimi.db2.questionnaire.dto.responses.QuestionResponse;
import it.polimi.db2.questionnaire.model.Question;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface QuestionMapper {

	@Mapping(target="id", source="id")
	@Mapping(target="text", source="text")
	public  QuestionResponse toQuestionResponse(Question question);
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="questionnaires", ignore=true)
	@Mapping(target="answers", ignore=true)
	@Mapping(target="text", source="text")
	public Question toQuestion(QuestionRequest questionRequest);

	public List<QuestionResponse> toQuestionsResponse(List<Question> questions);
}
