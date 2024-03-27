package com.example.cms.userservice;

import org.springframework.http.ResponseEntity;

import com.example.cms.dto.UserRequest;
import com.example.cms.dto.UserResponse;
import com.example.cms.utility.ResponseStructure;

public interface UserService {
	public ResponseEntity<ResponseStructure<UserResponse>> userRegistration(UserRequest user);
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById( int userId);

	

}
