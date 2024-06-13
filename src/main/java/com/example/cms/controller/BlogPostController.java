package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.request.dto.BlogPostRequest;
import com.example.cms.response.dto.BlogPostResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@AllArgsConstructor
public class BlogPostController {
	private BlogPostService blogPostService;
	
	@PostMapping("/blogs/{blogId}/blog-posts")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(@PathVariable int blogId,@RequestBody BlogPostRequest  blogPostRequest)
	{
		return blogPostService.createDraft(blogId,blogPostRequest);
	}
	
	@PutMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(@RequestBody BlogPostRequest  blogPostRequest,@PathVariable int postId){
		return blogPostService.updateDraft(postId,blogPostRequest);
	}
	
	@DeleteMapping("/blog-posts/{postId}")
	public  ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(@PathVariable int postId){
		return blogPostService.deleteBlogPost(postId);
	}

	@GetMapping("/blog-posts/{postId}")
	public  ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostById(@PathVariable int postId){
		return blogPostService.findBlogPostById(postId);
	}
	@GetMapping("/blog-posts/{postId}/publishes")
	public  ResponseEntity<ResponseStructure<BlogPostResponse>> findByIdAndPosttype(@PathVariable int postId){
		return blogPostService.findByIdAndPosttype(postId);
	}
	
	
}
