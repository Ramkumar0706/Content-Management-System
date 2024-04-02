package com.example.cms.userservice.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.cms.dtoRequest.BlogPostRequest;
import com.example.cms.dtoResponse.BlogPostResponse;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogNotFoundException;
import com.example.cms.exception.BlogPostAlreadyExistByTitleException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.usermodel.BlogPost;
import com.example.cms.userrepository.BlogPostRepository;
import com.example.cms.userrepository.BlogRepository;
import com.example.cms.userrepository.UserRepository;
import com.example.cms.userservice.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public class BlogPostImpl implements BlogPostService{
	private BlogRepository blogRepository;
	private UserRepository userRepository;
	private BlogPostRepository blogPostRepository;
	private ResponseStructure<BlogPostResponse> blogPostResponse;


	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createDraft(int blogId,BlogPostRequest blogPostRequset) {
		if(!isValidUser(blogId)) {
			throw new IllegalAccessRequestException("Failed to create draft");
		}
		if(!blogRepository.existsByTitle(blogPostRequset.getTitle())) {
			throw new BlogPostAlreadyExistByTitleException(" the title is already exists");
		}
		return blogRepository.findById(blogId).map(blog->{
			BlogPost blogPost = mapToBlogPost(blogPostRequset);
			blogPost.setBlog(blog);
			blogPost.setPostType(PostType.DRAFT);
			blogPostRepository.save(blogPost);
			blog.getBlogPost().add(blogPost);
			blogRepository.save(blog);
			return ResponseEntity.ok(blogPostResponse.setStatuscode(HttpStatus.CREATED.value())
					.setMessage("the blog post draft created successfully")
					.setData(mapToBlogPostResponse(blogPost)));
		}).orElseThrow(()->new BlogNotFoundException("blog is not found"));
	}
	
	
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateDraft(int postId,BlogPostRequest blogPostRequest){
		return blogPostRepository.findById(postId).map(blogPost->{
			if(!isValidUser(blogPost.getBlog().getBlogId())) {
				throw new IllegalAccessRequestException("Failed to update the Blog Post Draft");
			}
			BlogPost newBlogPost = mapToBlogPost(blogPostRequest);
			newBlogPost.setPostId(blogPost.getPostId());
			newBlogPost.setPostType(blogPost.getPostType());
			newBlogPost.setCreatedAt(blogPost.getCreatedAt());
			newBlogPost.setCreatedBy(blogPost.getCreatedBy());
			blogPostRepository.save(newBlogPost);
			return ResponseEntity.ok(blogPostResponse.setStatuscode(HttpStatus.OK.value()).setMessage("the blog post draft is updated successfullty")
					.setData(mapToBlogPostResponse(newBlogPost)));
					
			
		}).orElseThrow(()->new BlogPostNotFoundByIdException("the blog post is not found by Id"));
	}

	public BlogPost mapToBlogPost(BlogPostRequest blogPostRequest) {
		BlogPost blogPost=new BlogPost();
		blogPost.setTitile(blogPostRequest.getTitle());
		blogPost.setSutTitle(blogPostRequest.getSubTitile());
		blogPost.setSummary(blogPostRequest.getSummary());
		return blogPost;
	}

	public BlogPostResponse mapToBlogPostResponse(BlogPost blogPost) {
		return BlogPostResponse.builder()
				.title(blogPost.getTitile())
				.subTitle(blogPost.getSutTitle())
				.summary(blogPost.getSummary())
				.createdAt(blogPost.getCreatedAt())
				.lastModifiedAt(blogPost.getLastModifiedAt())
				.createdBy(blogPost.getCreatedBy())
				.lastModifiedBy(blogPost.getLastModifiedBy())
				.build();
	}

	public boolean isValidUser(int blogId) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(user->{
			return blogRepository.findById(blogId).map(blog->{
				if(blog.getUser().getEmail().equals(email)||blogPostRepository.existsByUserAndContributerpanel(blog.getContributerpanel().getPanelId(), user)) 
					return true;

				else {
					return false;
				}
			}).orElseThrow(()-> new BlogNotFoundException("Failed to validate user"));
		}).orElseThrow(()-> new UserNotFoundByIdException("Failes to validate User"));
	}


}
