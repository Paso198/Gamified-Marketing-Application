package it.polimi.db2.questionnaire.mappers;

import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.springframework.hateoas.CollectionModel;

import it.polimi.db2.questionnaire.dto.responses.ProductResponse;
import it.polimi.db2.questionnaire.dto.responses.QuestionResponse;
import it.polimi.db2.questionnaire.model.Product;
import it.polimi.db2.questionnaire.model.Question;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
	
	public QuestionResponse toQuestionResponse(Question question);
	
	public List<QuestionResponse> toQuestionResponsesList(Stream <Question> questions);
	
	default CollectionModel <QuestionResponse> toQuestionResponsesCollectionModel(Stream<Question> questions){
		return CollectionModel.of(toQuestionResponsesList(questions));
	}
}
