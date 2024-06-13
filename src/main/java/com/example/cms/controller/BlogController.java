package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.model.ContributionPanel;
import com.example.cms.request.dto.BlogRequest;
import com.example.cms.response.dto.BlogResponse;
import com.example.cms.response.dto.UserResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
@RestController
@AllArgsConstructor
public class BlogController {
	private BlogService blogService;
	
	@PostMapping("/users/{userId}/blogs")
	public ResponseEntity<ResponseStructure<BlogResponse>> registerBlog(@RequestBody @Valid BlogRequest blogRequest ,@PathVariable int userId){
		return blogService.blogRegister(blogRequest,userId);																																				
	}
	@GetMapping("/titles/{title}/blogs")
	public ResponseEntity<Boolean> checkTitleExists(@PathVariable String title){
		return blogService.checkTitleExists(title);
	}
	@GetMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(@PathVariable int blogId){
		return blogService.findBlogById(blogId);
		
	}
	@PutMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(@RequestBody @Valid BlogRequest blogRequest,@PathVariable int  blogId){
		return blogService.updateBlogData(blogRequest, blogId);
	}
	@PutMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanel>>addContributor(@PathVariable int  userId,@PathVariable int  panelId){
		return blogService.addContributer(userId, panelId);
	}
	@DeleteMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<UserResponse>> removeUserFromContributionPanel(@PathVariable int  userId,@PathVariable int  panelId){
		return blogService. removeUserFromContributionPanel(userId,panelId);
	}
	
	
	
}
