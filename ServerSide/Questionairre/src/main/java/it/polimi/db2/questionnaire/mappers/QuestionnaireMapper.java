package it.polimi.db2.questionnaire.mappers;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.requests.QuestionnaireRequest;
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
@Mapper(componentModel = "spring", uses = ProductMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class QuestionnaireMapper {

	protected ProductMapper productMapper;
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="date", source="questionnaireRequest.date")
	@Mapping(target="title", source="questionnaireRequest.title")
	@Mapping(target="product", source="product")
	@Mapping(target="questions", ignore=true)
	@Mapping(target="responses", ignore=true)
	@Mapping(target="logs", ignore=true)
	@Mapping(target="creator", source="creator")
	public abstract Questionnaire toQuestionnaire(QuestionnaireRequest questionnaireRequest, Product product, User creator);

	@Mapping(target="id", source="id")
	@Mapping(target="title", source="title")
	@Mapping(target="product", expression="java(productMapper.toProductResponse(questionnaire.getProduct()))")
	//@Mapping(target="questions", expression="java(...)") TODO
	@Mapping(target="questions", ignore=true)
	public abstract QuestionnaireOfTheDayResponse toQuestionnaireOfTheDayResponse(Questionnaire questionnaire);
	
	@Mapping(target="id", source="id")
	@Mapping(target="title", source="title")
	@Mapping(target="date", source="date")
	@Mapping(target="product", expression="java(productMapper.toProductResponse(questionnaire.getProduct()))")
	public abstract QuestionnaireResponse toQuestionnaireResponse(Questionnaire questionnaire);

	public abstract List<QuestionnaireResponse> toQuestionnairesResponse(List<Questionnaire> questionnaires);

}
