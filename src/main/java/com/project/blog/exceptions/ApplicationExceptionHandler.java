package com.project.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.project.blog.payload.ResponseStructure;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value=ResourceNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> catchResourceNotFoundException(ResourceNotFoundException exception){
		ResponseStructure<String> rs = new ResponseStructure();
		rs.setMessage("Not found");
		rs.setStatusCode(HttpStatus.NOT_FOUND.value());
		rs.setData(exception.getMessage());
		
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.NOT_FOUND);
 	}
	
	 /*ResponseEntityExceptionHandler
	
	 ResponseEntityExceptionHandler is a convenient base class for controller advice classes. It provides exception handlers for internal 
	 Spring exceptions ie., all predefined exceptions hence when our class with @ControllerAdvice is extending ResponseEntityExceptionHandler
	 we don't need to handle those exceptions. If we try to forcefuly handle the predefined exception the while the class with @ControllerAdvice 
	 is extending ResponseEntityExceptionHandler then the spring server wont start by throwing "Ambiguous @ExceptionHandler method mapped" as 
	 there is a method in the ResponseEntityExceptionHandler class annotated with @ExceptionHandler to handle the predefined exception and since 
	 we are also creating another method by annotating @Exceptionhandler and mentioning to forcefully handle the same exception the spring server 
	 is confused which class object has to be created to call the method resulting in BeanCreationException
	
	 If we really want to handle the predefined exception with our own method then the class with @ControllerAdvice must not extend
	 ResponseEntityExceptionHandler class to avoid the ambiguity.
	 
	 We only extend ResponseEntityExceptionHandler to handle the predefined exception, if we dont extend it then the handling of custom exception
	 would work fine without any threat to the application
	 */
	 
//	@ExceptionHandler(value=MethodArgumentNotValidException.class)
//	public ResponseEntity<ResponseStructure<String>> catchMethodArgumentNotValidException(MethodArgumentNotValidException exception){
//		ResponseStructure<String> rs = new ResponseStructure();
//		rs.setData(exception.getMessage());
//		rs.setMessage("Voilating constraints");
//		rs.setStatusCode(HttpStatus.NOT_FOUND.value());
//		
//		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.NOT_FOUND);
//	}
	
	/*
	 *  if we are manually handling the predefined exception then the method must be written in this way to get the fields in which the error was
	produced and the message given by the error object for each field 
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach(error -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });
	        return new ResponseEntity<Map<String,String>>(errors, HttpStatus.BAD_REQUEST);
	    }
	    
	 */
	
	
}
