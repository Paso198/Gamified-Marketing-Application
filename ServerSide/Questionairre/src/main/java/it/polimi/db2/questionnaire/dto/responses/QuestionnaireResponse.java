package it.polimi.db2.questionnaire.dto.responses;

import java.time.LocalDate;
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
public class QuestionnaireResponse {
	private Long id;
	private LocalDate date;
	private String title;
	private ProductResponse product;
	private List<QuestionResponse> questions;
	private List<ResponseResponse> responses;
	private List<UserResponse> usersWhoCancelled;
	
}
