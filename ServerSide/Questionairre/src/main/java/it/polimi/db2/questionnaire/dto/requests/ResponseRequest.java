package it.polimi.db2.questionnaire.dto.requests;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRequest {
	@NotNull
	private Long questionnaireId;
	
	@NotNull
	@NotEmpty
	private List<AnswerRequest> answers;
	
	private Integer age;
	private String gender;
	private String expertiseLevel;
	
}
