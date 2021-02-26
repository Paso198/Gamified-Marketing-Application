package it.polimi.db2.questionnaire.mappers;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import it.polimi.db2.questionnaire.dto.requests.AddQuestionnaireRequest;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireOfTheDayResponse;
import it.polimi.db2.questionnaire.dto.responses.QuestionnaireResponse;
import it.polimi.db2.questionnaire.model.Product;
import it.polimi.db2.questionnaire.model.Questionnaire;
import it.polimi.db2.questionnaire.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Mapper(componentModel = "spring", uses = {QuestionMapper.class, ProductMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class QuestionnaireMapper {

	
	@Mapping(target="product", expression="java(productMapper.toProductInQuestionnaireResponse(questionnaire.getProduct()))")
	@Mapping(target="questions", expression="java(questionMapper.toQuestionResponsesList(questionnaire.getQuestions().stream()))")
	public abstract QuestionnaireOfTheDayResponse toQuestionnaireOfTheDayResponse(Questionnaire questionnaire);
	
	@Mapping(target = "title", ignore = true)
	@Mapping(target = "user", source="user")
	@Mapping(target="id", ignore=true)
	@Mapping(target="date", expression="java(questionnaireRequest.getDate())")
	@Mapping(target="questions", ignore=true)
	@Mapping(target="responses", ignore=true)
	@Mapping(target="product", source="product")
	public abstract Questionnaire toQuestionnaire(AddQuestionnaireRequest questionnaireRequest, Product product, User user);
	
	@Mapping(target="questions", expression="java(questionMapper.toQuestionResponsesCollectionModel(questionnaire.getQuestions().stream()))")
	@Mapping(target="id", expression="java(questionnaire.getId())")
	@Mapping(target="date", expression="java(questionnaire.getDate())")
	@Mapping(target="title", expression="java(questionnaire.getTitle())")
	@Mapping(target="product", expression="java(productMapper.toProductResponse(questionnaire.getProduct()))")
	@Mapping(target="responses", ignore=true/*TODO:fix*/)
	public abstract QuestionnaireResponse toQuestionnaireResponse(Questionnaire questionnaire, List<User> cancelled);


}
