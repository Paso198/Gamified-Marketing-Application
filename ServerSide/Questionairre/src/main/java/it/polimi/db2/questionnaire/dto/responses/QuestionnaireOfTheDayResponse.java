package it.polimi.db2.questionnaire.dto.responses;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireOfTheDayResponse extends RepresentationModel<QuestionnaireOfTheDayResponse>{
	private Long id;
	private String title;
	private ProductResponse product;
	private List<QuestionResponse> questions;
}
