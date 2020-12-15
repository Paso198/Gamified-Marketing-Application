package it.polimi.db2.questionnaire.dto.requests;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRequest {
	@NotBlank
	private Long questionnaireId;
	
	private List<AnswerRequest> answers;
	private Integer age;
	private String gender;
	private String expertiseLevel;
	
}
