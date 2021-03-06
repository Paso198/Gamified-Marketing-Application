package it.polimi.db2.questionnaire.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Exception thrown where there is not logged user but one is required
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnloggedUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

}
