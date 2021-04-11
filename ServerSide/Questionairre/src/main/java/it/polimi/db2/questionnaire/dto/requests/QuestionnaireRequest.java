package it.polimi.db2.questionnaire.dto.requests;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	@Future
	@NotNull
	private LocalDate date;;
	
	@NotNull
	private Long productId;
	
	@NotBlank
	private String title;
	
	@NotNull
	@NotEmpty
	private List<Long> questionsIds;
	
	
}
