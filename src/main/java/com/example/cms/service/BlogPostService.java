package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.request.dto.BlogPostRequest;
import com.example.cms.response.dto.BlogPostResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogPostService {
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(int blogId,BlogPostRequest blogPostRequset);
	
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId,BlogPostRequest blogPostRequest);
	
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId);
	
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostById(int postId);
	
	public ResponseEntity<ResponseStructure<BlogPostResponse>>findByIdAndPosttype(int postId);
	
}
