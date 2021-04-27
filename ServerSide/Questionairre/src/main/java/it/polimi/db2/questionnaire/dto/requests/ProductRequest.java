package it.polimi.db2.questionnaire.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
	
	private final int MIN_NAME_LENGTH = 2;
	private final int MAX_NAME_LENGTH = 30;
	
	@NotBlank(message = "Name can't be blank")
	@Size(min=MIN_NAME_LENGTH, max= MAX_NAME_LENGTH, message="Name length must be between "+MIN_NAME_LENGTH+" and "+MAX_NAME_LENGTH+" characters")
	@Pattern(regexp="[a-zA-Z0-9][a-zA-Z0-9 ]*[a-zA-Z0-9]", message="Name must contain only letters and numbers separated by spaces")
	private String name;
	
	@NotNull
	private MultipartFile image;
	
}
