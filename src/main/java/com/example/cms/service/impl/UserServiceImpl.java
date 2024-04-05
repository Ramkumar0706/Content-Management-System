package com.example.cms.service.impl;



import com.example.cms.exception.*;
import com.example.cms.model.User;
import com.example.cms.repository.UserRepository;
import com.example.cms.request.dto.UserRequest;
import com.example.cms.response.dto.UserResponse;
import com.example.cms.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepostiory;
	private ResponseStructure<UserResponse> response;
	private PasswordEncoder pass;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> userRegistration(UserRequest user) {
		if(userRepostiory.existsByEmail(user.getEmail()))
			throw new UserAlreadyExistByEmailException("Failed to register user");
		User saveUser=userRepostiory.save(mapToUser(user));
		//	System.out.println(saveUser);
		return ResponseEntity.ok(response.setStatuscode(HttpStatus.CREATED.value())
				.setMessage("user Register successfully")
				.setData(matToResponse(saveUser)));
	}

	private UserResponse matToResponse(User saveUser) {
		return UserResponse.builder()
				.userId(saveUser.getUserId())
				.username(saveUser.getUsername())
				.userEmail(saveUser.getEmail())
				.createdAt(saveUser.getCreatedAt())
				.modifiedAt(saveUser.getLastModifiedAt())
				.build();

	}

	public User mapToUser(UserRequest userRequest) {

		//System.out.println(userRequest);
		//return User.builder().username(userRequest.getUserName()).email(userRequest.getUserEmail()).password(pass.encode(userRequest.getUserPassword())).build();
		User u= new User();
		u.setUsername(userRequest.getUsername());
		u.setPassword(pass.encode(userRequest.getPassword()));
		u.setEmail(userRequest.getEmail());
		return u;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId) {

		return userRepostiory.findById(userId)
				.map(user->ResponseEntity.ok(response
						.setStatuscode(HttpStatus.OK.value())
						.setMessage("User is Found By Id")
						.setData(matToResponse(user))))
				.orElseThrow(()->new UserNotFoundByIdException("User is not Found By Id"));
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUserById(int userId) {
		User user = userRepostiory.findById(userId).orElseThrow(()->new UserNotFoundByIdException("user Not found By Id"));
		user.setDeleted(true);
		userRepostiory.save(user);
		return ResponseEntity.ok(response
				.setStatuscode(HttpStatus.OK.value())
				.setMessage("User is Found By Id")
				.setData(matToResponse(user)));
	}
}































