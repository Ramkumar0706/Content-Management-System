package com.example.cms.userservice;

import org.springframework.http.ResponseEntity;

import com.example.cms.dtoRequest.PublishRequest;
import com.example.cms.dtoResponse.BlogPostResponse;
import com.example.cms.dtoResponse.PublishResponse;
import com.example.cms.utility.ResponseStructure;

public interface PublishService {

	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,int postId);
	
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unPublishBlogPost(int postId);
	
}
