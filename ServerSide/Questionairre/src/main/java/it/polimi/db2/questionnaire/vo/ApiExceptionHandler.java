package it.polimi.db2.questionnaire.vo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.polimi.db2.questionnaire.exceptions.BadImageException;
import it.polimi.db2.questionnaire.exceptions.DuplicateUniqueValueException;
import it.polimi.db2.questionnaire.exceptions.ProductNotFoundException;
import it.polimi.db2.questionnaire.exceptions.QuestionNotFoundException;
import it.polimi.db2.questionnaire.exceptions.QuestionnaireNotAvailableException;
import it.polimi.db2.questionnaire.exceptions.QuestionnaireNotFoundException;
import it.polimi.db2.questionnaire.exceptions.UnauthorizedOperationException;
import it.polimi.db2.questionnaire.exceptions.UnloggedUserException;
import it.polimi.db2.questionnaire.exceptions.UserBlockedException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateUniqueValueException.class)
	@ResponseStatus(code = HttpStatus.OK)
	@ResponseBody
	public Map<String, String> handleDuplicateError(DuplicateUniqueValueException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put(ex.getField(), ex.getMessage());
		return errors;
	}

	@ExceptionHandler(BadImageException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> handleImageError(BadImageException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put(ex.getField(), ex.getMessage());
		return errors;
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ResponseBody
	public Map<String, String> handleProductNotFound(ProductNotFoundException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put(ex.getName(), ex.getMessage());
		return errors;
	}
	
	@ExceptionHandler(QuestionnaireNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ResponseBody
	public Map<String, String> handleQuestionnaireNotFound(QuestionnaireNotFoundException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put(ex.getName(), ex.getMessage());
		return errors;
	}
	
	@ExceptionHandler(QuestionNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ResponseBody
	public Map<String, String> handleQuestionNotFound(QuestionNotFoundException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put(ex.getName(), ex.getMessage());
		return errors;
	}
	
	@ExceptionHandler(QuestionnaireNotAvailableException.class)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@ResponseBody
	public Map<String, String> handleQuestionnaireNotAvailable(QuestionnaireNotAvailableException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put(ex.getName(), ex.getMessage());
		return errors;
	}
	
	@ExceptionHandler(UnloggedUserException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public Map<String, String> handleUnloggedUserException(UnloggedUserException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("user", ex.getMessage());
		return errors;
	}
	
	@ExceptionHandler(UserBlockedException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public Map<String, String> UserBlockedException(UserBlockedException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("user", ex.getMessage());
		return errors;
	}
	
	@ExceptionHandler(UnauthorizedOperationException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public Map<String, String> handleUnauthorizedOperationException(UnauthorizedOperationException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("questionnaire", ex.getMessage());
		return errors;
	}
}
