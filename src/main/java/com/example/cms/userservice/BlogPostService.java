package com.example.cms.userservice;

import org.springframework.http.ResponseEntity;

import com.example.cms.dtoRequest.BlogPostRequest;
import com.example.cms.dtoResponse.BlogPostResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogPostService {
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(int blogId,BlogPostRequest blogPostRequset);
	
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId,BlogPostRequest blogPostRequest);
	
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId);
}
