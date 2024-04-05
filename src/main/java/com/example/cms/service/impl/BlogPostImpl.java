package com.example.cms.service.impl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogNotFoundException;
import com.example.cms.exception.BlogPostAlreadyExistByTitleException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.Blog;
import com.example.cms.model.BlogPost;
import com.example.cms.model.Publish;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributerRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.request.dto.BlogPostRequest;
import com.example.cms.response.dto.BlogPostResponse;
import com.example.cms.response.dto.PublishResponse;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class BlogPostImpl implements BlogPostService{
	private BlogRepository blogRepository;
	private UserRepository userRepository;
	private BlogPostRepository blogPostRepository;
	private ResponseStructure<BlogPostResponse> blogPostResponse;
	private ContributerRepository contributerRepository;


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

	@Override
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
			newBlogPost.setBlog(blogPost.getBlog());
			blogPostRepository.save(newBlogPost);
			return ResponseEntity.ok(blogPostResponse.setStatuscode(HttpStatus.OK.value()).setMessage("the blog post draft is updated successfullty")
					.setData(mapToBlogPostResponse(newBlogPost)));
		}).orElseThrow(()->new BlogPostNotFoundByIdException("the blog post is not found by Id"));
	}
	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId){
		String ownerEmail=SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(ownerEmail).map(owner->{
			return blogPostRepository.findById(postId).map(blogPost->{
				if(!blogPost.getBlog().getUser().getEmail().equals(ownerEmail)||(!blogPost.getCreatedBy().equals(ownerEmail)))
					throw new IllegalAccessRequestException(" you not create the the blog post .so you don't have autherity to delete the blog post");
				blogPostRepository.deleteById(postId);
				int blogId = blogPost.getBlog().getBlogId();
				Blog blog = blogRepository.findById(blogId).orElseThrow(()-> new BlogPostNotFoundByIdException("blog not found"));
				blog.getBlogPost().remove(blogPost);
				blogRepository.save(blog);
				return ResponseEntity.ok(blogPostResponse.setStatuscode(HttpStatus.OK.value())
						.setMessage("the blog post draft is deleted successfullty")
						.setData(mapToBlogPostResponse(blogPost)));
			}).orElseThrow(()->new BlogPostNotFoundByIdException("blog post not find by id "));
		}).get();
	}


	public BlogPost mapToBlogPost(BlogPostRequest blogPostRequest) {
		BlogPost blogPost=new BlogPost();
		blogPost.setTitile(blogPostRequest.getTitle());
		blogPost.setSutTitle(blogPostRequest.getSubTitile());
		blogPost.setSummary(blogPostRequest.getSummary());
		blogPost.setCreatedAt(blogPostRequest.getCreatedAt());
		blogPost.setCreatedBy(blogPostRequest.getCreatedBy());
		blogPost.setLastModifiedAt(blogPostRequest.getLastModifiedAt());
		blogPost.setLastModifiedBy(blogPostRequest.getLastModifiedBy());
		return blogPost;
	}

	public BlogPostResponse mapToBlogPostResponse(BlogPost blogPost) {
		return BlogPostResponse.builder()
				.postId(blogPost.getPostId())
				.title(blogPost.getTitile())
				.subTitle(blogPost.getSutTitle())
				.summary(blogPost.getSummary())
				.createdAt(blogPost.getCreatedAt())
				.lastModifiedAt(blogPost.getLastModifiedAt())
				.createdBy(blogPost.getCreatedBy())
				.lastModifiedBy(blogPost.getLastModifiedBy())
				.blogPost(blogPost.getPostType())
				.build();
	}
	
	public BlogPostResponse mapToBlogPostAndPublishResponse(BlogPost blogPost) {
		return BlogPostResponse.builder()
				.postId(blogPost.getPostId())
				.title(blogPost.getTitile())
				.subTitle(blogPost.getSutTitle())
				.summary(blogPost.getSummary())
				.createdAt(blogPost.getCreatedAt())
				.lastModifiedAt(blogPost.getLastModifiedAt())
				.createdBy(blogPost.getCreatedBy())
				.lastModifiedBy(blogPost.getLastModifiedBy())
				.blogPost(blogPost.getPostType())
				.publishResponse(blogPost.getPublish() != null ? 
                        mapToBlogPostResponse(blogPost.getPublish()) : 
                        null)
				.build();
	}
	public PublishResponse mapToBlogPostResponse(Publish publish) {
		return  PublishResponse.builder()
		 .publishId(publish.getPublishId())
		 .seoTitle(publish.getSeoTitle())
		 .seoDescription(publish.getSeoDescription())
		 .seoTopics(publish.getSeoTopics())
		 .createdAt(publish.getCreatedAt())
		 .build();
		 
	}

	public boolean isValidUser(int blogId) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(user->{
			return blogRepository.findById(blogId).map(blog->{
				if(blog.getUser().getEmail().equals(email)||contributerRepository.existsByPanelIdAndContributers(blog.getContributerpanel().getPanelId(), user)) 
					return true;
				else {
					return false;
				}
			}).orElseThrow(()-> new BlogNotFoundException("Failed to validate blog "));
		}).orElseThrow(()-> new UserNotFoundByIdException("Failes to validate by id User"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostById(int postId) {
		return blogPostRepository.findById(postId).map(blogPost->{
			return ResponseEntity.ok(blogPostResponse.setStatuscode(HttpStatus.OK.value())
					.setMessage("the blog post draft is deleted successfullty")
					.setData(mapToBlogPostAndPublishResponse(blogPost)));
			
		}).orElseThrow(()-> new BlogNotFoundException("blog post not found"));
	}
	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> findByIdAndPosttype(int postId){
		return blogPostRepository.findByPostIdAndPostType(postId,PostType.PUBLISHED).map(blogPost->ResponseEntity.ok(blogPostResponse
				.setStatuscode(HttpStatus.FOUND.value())
				.setData(mapToBlogPostAndPublishResponse(blogPost))
				.setMessage("the blog post is published list"))).orElseThrow(()->new IllegalAccessRequestException("failed to fectch the blogPost"));
	}
	
	


}
