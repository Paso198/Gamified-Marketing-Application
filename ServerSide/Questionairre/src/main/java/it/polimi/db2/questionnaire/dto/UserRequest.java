package it.polimi.db2.questionnaire.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	private final int MIN_PSW_LENGTH = 5;
	private final int MIN_USERNAME_LENGTH = 3;
	private final int MAX_PSW_LENGTH = 18;
	private final int MAX_USERNAME_LENGTH = 18;
	
	@NotBlank(message = "Username can't be blank")
//	@Min(value = MIN_USERNAME_LENGTH, message="Minimun username length is "+MIN_USERNAME_LENGTH)
//	@Max(value = MAX_USERNAME_LENGTH, message="Maximum username length is "+MAX_USERNAME_LENGTH)
	@Size(min=MIN_USERNAME_LENGTH, max= MAX_USERNAME_LENGTH, message="lenght must be between "+MIN_USERNAME_LENGTH+" and "+MAX_USERNAME_LENGTH+" characters")
	@Pattern(regexp="([\\w\\d](\\s)?)*[\\w\\d]", message="Username must contain only letters and numbers")
	private String username;
	
	@Email(message="Invalid Mail format")
	@NotBlank(message = "Mail can't be blank")
	private String email;
//	
//	@Min(value = MIN_PSW_LENGTH, message="Minimun password length is "+MIN_PSW_LENGTH)
//	@Max(value = MIN_PSW_LENGTH, message="Maximum password length is "+MAX_PSW_LENGTH)
	@Size(min=MIN_PSW_LENGTH, max= MAX_PSW_LENGTH, message="lenght must be between "+MIN_PSW_LENGTH+" and "+MAX_PSW_LENGTH+" characters")
	@Pattern(regexp="[a-zA-Z0-9!_\\.\\-]*", message = "Password must contain only letters, numbers or some special characters (! _ . - )")
	private String password;
}
