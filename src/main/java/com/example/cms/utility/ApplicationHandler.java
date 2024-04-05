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
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "user is not found by Id");
				
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleBlogNotFoundException(BlogNotFoundException ex){
		return errorResponse(HttpStatus.NOT_FOUND,ex.getMessage(),"blog is not found by Id");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleTitleEmptyException(TitleEmptyException ex){
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"blog title is empty ");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleBlogTitleExistException(BlogTitleAlreadyExistException ex){
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"blog title is already in database");	
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleBlogPostAlreadyExistByTitleException(BlogPostAlreadyExistByTitleException ex){
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"blog post titile alresdy found ");	
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleBlogPostNotFoundByIdException(BlogPostNotFoundByIdException ex){
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"blog post not found by Id ");	
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleIllegalAccessRequestException(IllegalAccessRequestException ex){
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"illegal access request");	
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleInvalidPostStatusException(InvalidPostStatusException ex){
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"post is already in DRAFT state");	
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handlePanelNotFoundException(PanelNotFoundException ex){
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"panel is not found by thie user or blog Id");	
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleTimeInvalidException(TimeInvalidException ex){
		return errorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),"Invalid Time");	
	}
	
	
}