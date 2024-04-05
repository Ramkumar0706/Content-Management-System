package com.example.cms.response.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserResponse {
	private int userId;
	private String username;
	private String userEmail;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	
}
