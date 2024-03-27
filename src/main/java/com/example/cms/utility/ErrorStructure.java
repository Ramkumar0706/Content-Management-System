package com.example.cms.utility;

import org.springframework.stereotype.Component;

@Component
public class ErrorStructure<T> {
	private int errorStatuscode;
	private String errorMessage;
	private T rootCause;
	public int getErrorStatuscode() {
		return errorStatuscode;
	}
	public ErrorStructure<T> setErrorStatuscode(int errorStatuscode) {
		this.errorStatuscode = errorStatuscode;
		return this;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public ErrorStructure<T> setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
	public T getRootCause() {
		return rootCause;
	}
	public ErrorStructure<T> setRootCause(T rootCause) {
		this.rootCause = rootCause;
		return this;
	}

}
