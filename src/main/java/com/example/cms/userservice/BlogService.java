package com.example.cms.userservice;

import org.springframework.http.ResponseEntity;

import com.example.cms.dtoRequest.BlogRequest;
import com.example.cms.dtoResponse.BlogResponse;
import com.example.cms.dtoResponse.UserResponse;
import com.example.cms.usermodel.ContributionPanel;
import com.example.cms.utility.ResponseStructure;



public interface BlogService {

	public ResponseEntity<ResponseStructure<BlogResponse>> blogRegister( BlogRequest blogRequest,int userId);

	public ResponseEntity<Boolean> checkTitleExists(String title);

	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId);
	
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(BlogRequest blogRequest,int blogId);
	
	public ResponseEntity<ResponseStructure<ContributionPanel>> addContributer(int userId,int panelId);

	public ResponseEntity<ResponseStructure<UserResponse>> removeUserFromContributionPanel(int userId, int panelId);
}
