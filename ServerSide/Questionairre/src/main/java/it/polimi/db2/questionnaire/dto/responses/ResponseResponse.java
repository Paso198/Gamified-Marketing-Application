package it.polimi.db2.questionnaire.dto.responses;

import java.util.List;

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
public class ResponseResponse {
	private Long id;
	private UserResponse submitter;
	private List<AnswerResponse> answers;
	private Integer age;
	private String gender;
	private String expertiseLevel;
}
