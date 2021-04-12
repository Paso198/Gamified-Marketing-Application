package it.polimi.db2.questionnaire.exceptions;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Exception thrown when an user that is blocked interacts with application
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlockedUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
}