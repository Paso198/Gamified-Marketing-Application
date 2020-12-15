package it.polimi.db2.questionnaire.dto.requests;

import javax.validation.constraints.NotBlank;

public class AnswerRequest {
	@NotBlank
	private Long questionId;
	
	@NotBlank
	private String text;
}
