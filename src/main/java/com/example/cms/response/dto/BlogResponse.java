package com.example.cms.response.dto;

import java.util.List;

import com.example.cms.model.User;

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
