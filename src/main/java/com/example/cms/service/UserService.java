package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.request.dto.UserRequest;
import com.example.cms.response.dto.UserResponse;
import com.example.cms.utility.ResponseStructure;

public interface UserService {
	public ResponseEntity<ResponseStructure<UserResponse>> userRegistration(UserRequest user);
	
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById( int userId);
	
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(int userId);

	

}
