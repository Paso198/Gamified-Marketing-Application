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
public class ResponseResponse extends RepresentationModel <ResponseResponse>{
	private Long id;
	private UserResponse submitter;
	private List<AnswerResponse> answers;
	private Integer age;
	private String gender;
	private String expertiseLevel;
}
