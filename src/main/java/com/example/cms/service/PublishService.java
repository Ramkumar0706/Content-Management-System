package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.request.dto.PublishRequest;
import com.example.cms.response.dto.BlogPostResponse;
import com.example.cms.response.dto.PublishResponse;
import com.example.cms.utility.ResponseStructure;

public interface PublishService {

	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,int postId);
	
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unPublishBlogPost(int postId);
	
}
