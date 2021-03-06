package it.polimi.db2.questionnaire.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String message;

}
