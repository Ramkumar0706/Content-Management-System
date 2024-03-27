package com.example.cms.utility;

import com.example.cms.exception.*;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@AllArgsConstructor
@RestControllerAdvice
public class ApplicationHandler {

	private ErrorStructure<String> errorStructure;

	
	public ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus code,String message,String rootCause){
		return ResponseEntity.ok(errorStructure.setErrorStatuscode(code.value()).setErrorMessage(message).setRootCause(rootCause));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handeleUserAlreadyExistByEmail(UserAlreadyExistByEmailException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "user is already exist in email");
				
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handelerUserNotFoundtById(UserNotFoundByIdException ex)
	{
		return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "user is not found by Id");
				
	}
	
	
}