package com.example.cms.exception;

public class ConstraintViolationException extends RuntimeException {
private String message;

public ConstraintViolationException(String message) {
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
