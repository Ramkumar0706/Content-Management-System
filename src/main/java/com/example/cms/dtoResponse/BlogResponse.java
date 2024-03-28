package com.example.cms.dtoResponse;

import java.util.List;

import com.example.cms.usermodel.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Builder
@Getter
@Setter
@AllArgsConstructor
public class BlogResponse {
	private int blogId;
	private String title;
	private String[] topics;
	private String about;
	private List<User> user;

}
