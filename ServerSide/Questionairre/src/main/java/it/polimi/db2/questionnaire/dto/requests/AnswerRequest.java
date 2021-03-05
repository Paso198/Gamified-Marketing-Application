package it.polimi.db2.questionnaire.dto.requests;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {
	@NotBlank
	private Long questionId;
	
	@NotBlank
	private String text;
}
