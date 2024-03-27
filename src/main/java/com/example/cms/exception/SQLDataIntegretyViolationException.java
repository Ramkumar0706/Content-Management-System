package com.example.cms.exception;



public class SQLDataIntegretyViolationException extends RuntimeException {

	private String message;
	

	public SQLDataIntegretyViolationException(String message) {
		super();
		this.message = message;
	}


	public String getMessage() {
		return message;
	}

	
	

}
