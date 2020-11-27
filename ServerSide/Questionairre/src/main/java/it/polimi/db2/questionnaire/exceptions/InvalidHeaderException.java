package it.polimi.db2.questionnaire.exceptions;

public class InvalidHeaderException extends Exception {

	/**
	 * There is no valid header for jwt auth
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidHeaderException(String message) {
		super(message);
	}
}
