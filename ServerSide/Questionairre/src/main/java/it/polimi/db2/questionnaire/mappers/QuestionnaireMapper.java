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
@AllArgsConstructor
@NoArgsConstructor
@Mapper(componentModel = "spring", uses = {QuestionMapper.class, ProductMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class QuestionnaireMapper {

	protected QuestionMapper questionMapper;
	protected ProductMapper productMapper;
	
	@Mapping(target="product", expression="java(productMapper.toProductInQuestionnaireResponse(questionnaire.getProduct()))")
	@Mapping(target="questions", expression="java(questionMapper.toQuestionResponsesList(product.getQuestions().stream()))")
	public abstract QuestionnaireOfTheDayResponse toQuestionnaireOfTheDayResponse(Questionnaire questionnaire);
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="date", expression="java(questionnaireRequest.getDate())")
	@Mapping(target="questions", ignore=true)
	@Mapping(target="responses", ignore=true)
	public abstract Questionnaire toQuestionnaire(AddQuestionnaireRequest questionnaireRequest, Product product, User user);
	
	public abstract QuestionnaireResponse toQuestionnaireResponse(Questionnaire questionnaire, List<User> cancelled);

	@Autowired
	public void setQuestionMapper(QuestionMapper questionMapper) {
		this.questionMapper = questionMapper;
	};
}
