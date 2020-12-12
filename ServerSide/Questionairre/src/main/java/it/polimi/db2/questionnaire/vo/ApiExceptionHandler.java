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
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> handleProductNotFound(ProductNotFoundException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put(ex.getName(), ex.getMessage());
		return errors;
	}
}
