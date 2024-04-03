package com.example.cms.userservice.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.dtoRequest.PublishRequest;
import com.example.cms.dtoResponse.BlogPostResponse;
import com.example.cms.dtoResponse.PublishResponse;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.InvalidPostStateException;
import com.example.cms.exception.InvalidPostStatusException;
import com.example.cms.usermodel.BlogPost;
import com.example.cms.usermodel.Publish;
import com.example.cms.userrepository.BlogPostRepository;
import com.example.cms.userrepository.PublishRepository;
import com.example.cms.userservice.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class PublishServiceimpl  implements PublishService{
	private PublishRepository publishRepository;
	private BlogPostRepository blogPostRepository;
	private ResponseStructure<PublishResponse> responseStructure;
	private ResponseStructure<BlogPostResponse> response;
	
	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,
			int postId) {
		String ownerEmail=SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(blogPost->{
			
			if(!blogPost.getBlog().getUser().getEmail().equals(ownerEmail)||(!blogPost.getCreatedBy().equals(ownerEmail))) {
				throw new IllegalArgumentException("failed to publish the blog post");
			}
			if(blogPost.getPostType()!=PostType.DRAFT&&blogPost.getPostType()!=null) {
				throw new InvalidPostStateException("Post is not in DRAFT state"); 
			}
			Publish publish = mapToPublishRequest(publishRequest);
			publish.setBlogPost(blogPost);
			blogPost.setPostType(PostType.PUBLISHED);
			publishRepository.save(publish);
			blogPostRepository.save(blogPost);
			return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.CREATED.value())
					.setMessage("the blog post draft created successfully")
					.setData(mapToBlogPostResponse(publish)));
			
		}).orElseThrow(()->new BlogPostNotFoundByIdException("blogPost not find by Id"));
	}
	
	
	
	 PublishResponse mapToBlogPostResponse(Publish publish) {
		 PublishResponse.builder()
		 .publishId(publish.getPublishId())
		 .seoTitle(publish.getSeoTitle())
		 .seoDescription(publish.getSeoDescription())
		 .seoTopics(publish.getSeoTopics())
		 .createdAt(publish.getCreatedAt())
		 .build();
		 return null;
	}
	public Publish mapToPublishRequest(PublishRequest publishRequest) {
		Publish p=new Publish();
		p.setSeoTitle(publishRequest.getSeoTitle());
		p.setSeoDescription(publishRequest.getSeoDescription());
		p.setSeoTopics(publishRequest.getSeoTopics());
		return p;
		
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



	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unPublishBlogPost(int postId) {
		
		return blogPostRepository.findById(postId).map(blogPost->{
			if(blogPost.getPostType()!=PostType.PUBLISHED) {
				 throw new InvalidPostStatusException("Post is not in PUBLISHED state.");
			}
			  blogPost.setPostType(PostType.DRAFT);
			  blogPostRepository.save(blogPost);
			  return ResponseEntity.ok(response.setStatuscode(HttpStatus.CREATED.value())
						.setMessage("the blog post published into draft created successfully")
						.setData(mapToBlogPostResponse(blogPost)));
			
		}).orElseThrow(()->new BlogPostNotFoundByIdException("BlogPost id is not found"));
		
	}



	

}
