package com.example.cms.usercontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.dtoRequest.BlogPostRequest;
import com.example.cms.dtoResponse.BlogPostResponse;
import com.example.cms.userservice.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

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
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(@PathVariable int postId,@RequestBody BlogPostRequest  blogPostRequest){
		return blogPostService.updateDraft(postId,blogPostRequest);
	}
	
	@DeleteMapping("/blog-posts/{postId}")
	public  ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(@PathVariable int postId){
		return blogPostService.deleteBlogPost(postId);
	}

}