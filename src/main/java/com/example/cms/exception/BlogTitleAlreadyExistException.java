package com.example.cms.exception;

public class BlogTitleAlreadyExistException extends RuntimeException {
	private String message;

	
	public BlogTitleAlreadyExistException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
