package com.ontop.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ontop.exception.entity.ErrorMessage;

@RestControllerAdvice
public class ControllerAdvisor {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex){
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode("INVALID_BODY");
		Map<String, String> errors = new HashMap<>();
		errors.put("code", "INVALID_BODY");
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        errors.put("message", error.getDefaultMessage());
	        errorMessage.setMessage(error.getDefaultMessage());
	    });
	    //return errors;
	    return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({GeneralException.class,IllegalStateException.class})
	public ResponseEntity<ErrorMessage> handleGeneralException(GeneralException ex, IllegalStateException ex2){
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode("GENERIC_ERROR");
		errorMessage.setMessage("something bad happened");
	    return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException ex){
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode("INVALID_USER");
		errorMessage.setMessage("user not found");
	    return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(NegativeBalanceNotAllowedException.class)
	public ResponseEntity<ErrorMessage> handleNegativeAmountNotAllowedException(NegativeBalanceNotAllowedException ex){
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode("GENERIC_ERROR");
		errorMessage.setMessage("Negative Balance Not Allowed");
	    return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
