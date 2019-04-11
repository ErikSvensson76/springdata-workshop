package se.lexicon.spring_data_workshop.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(value = "se.lexicon.spring_data_workshop")
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex){
		Map<String, Object> errors = new HashMap<>();
		errors.put("timestamp", LocalDateTime.now());
		errors.put("message", ex.getMessage());
		errors.put("code",HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex){
		Map<String, Object> errors = new HashMap<>();
		errors.put("timestamp", LocalDateTime.now());
		errors.put("message", ex.getMessage());
		errors.put("code", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	

}
