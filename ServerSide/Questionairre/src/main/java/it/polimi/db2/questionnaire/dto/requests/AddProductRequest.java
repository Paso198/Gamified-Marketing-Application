package it.polimi.db2.questionnaire.dto.requests;

import javax.validation.constraints.NotBlank;
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
public class AddProductRequest {
	
	private final int MIN_NAME_LENGTH = 1;
	private final int MAX_NAME_LENGTH = 30;
	
	@NotBlank(message = "Name can't be blank")
	@Size(min=MIN_NAME_LENGTH, max= MAX_NAME_LENGTH, message="Name lenght must be between "+MIN_NAME_LENGTH+" and "+MAX_NAME_LENGTH+" characters")
	@Pattern(regexp="([\\w\\d](\\s)?)*[\\w\\d]", message="Name must contain only letters and numbers")
	private String name;
	
	private MultipartFile image;
	
}
