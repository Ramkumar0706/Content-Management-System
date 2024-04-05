package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.request.dto.PublishRequest;
import com.example.cms.response.dto.BlogPostResponse;
import com.example.cms.response.dto.PublishResponse;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PublishController {
	private PublishService publishService;

	@PostMapping("/blog-posts/{postId}/publishes")
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(@PathVariable int postId,@RequestBody @Valid PublishRequest  publishRequest){
		return publishService.publishBlogPost(publishRequest, postId);
	}


	@PutMapping("/blogPosts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unPublishBlogPost(@PathVariable int postId){
		return publishService.unPublishBlogPost(postId);

	}
}
