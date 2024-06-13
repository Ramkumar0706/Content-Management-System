package com.example.cms.utility;




import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class ResponseStructure<T> {
	private int statuscode;
	private String message;
	private T data;
	private List<T> lists;
	
	public List<T> getLists() {
		return lists;
	}
	public ResponseStructure<T> setLists(List<T> lists) {
		this.lists = lists;
		return this;
	}
	public int getStatuscode() {
		return statuscode;
	}
	public ResponseStructure<T> setStatuscode(int statuscode) {
		this.statuscode = statuscode;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public ResponseStructure<T> setMessage(String message) {
		this.message = message;
		return this;
	}
	public T getData() {
		return data;
	}
	public ResponseStructure<T> setData(T data) {
		this.data = data;
		return this;
	}


}
