package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.model.ContributionPanel;
import com.example.cms.request.dto.BlogRequest;
import com.example.cms.response.dto.BlogResponse;
import com.example.cms.response.dto.UserResponse;
import com.example.cms.utility.ResponseStructure;



public interface BlogService {

	public ResponseEntity<ResponseStructure<BlogResponse>> blogRegister( BlogRequest blogRequest,int userId);

	public ResponseEntity<Boolean> checkTitleExists(String title);

	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId);
	
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(BlogRequest blogRequest,int blogId);
	
	public ResponseEntity<ResponseStructure<ContributionPanel>> addContributer(int userId,int panelId);

	public ResponseEntity<ResponseStructure<UserResponse>> removeUserFromContributionPanel(int userId, int panelId);
}
