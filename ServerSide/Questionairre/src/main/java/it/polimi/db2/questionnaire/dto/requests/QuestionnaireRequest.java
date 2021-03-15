package it.polimi.db2.questionnaire.dto.requests;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionnaireRequest {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotBlank
	@FutureOrPresent
	private LocalDate date;;
	
	@NotBlank
	private Long productId;
	
	@NotBlank
	private String title;
	
	
}
