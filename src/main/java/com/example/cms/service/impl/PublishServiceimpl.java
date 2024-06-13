package com.example.cms.service.impl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.TimeDateNotValidException;
import com.example.cms.model.BlogPost;
import com.example.cms.model.Publish;
import com.example.cms.model.Schedule;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.PublishRepository;
import com.example.cms.repository.ScheduleRepository;
import com.example.cms.request.dto.PublishRequest;
import com.example.cms.request.dto.ScheduleRequest;
import com.example.cms.response.dto.BlogPostResponse;
import com.example.cms.response.dto.PublishResponse;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class PublishServiceimpl  implements PublishService{
	private PublishRepository publishRepository;
	private BlogPostRepository blogPostRepository;
	private ResponseStructure<PublishResponse> responseStructure;
	private ResponseStructure<BlogPostResponse> response;
	private ScheduleRepository scheduleRepository;

	/*
	 * @Override public ResponseEntity<ResponseStructure<PublishResponse>>
	 * publishBlogPost(PublishRequest publishRequest, int postId) { String
	 * ownerEmail=SecurityContextHolder.getContext().getAuthentication().getName();
	 * return blogPostRepository.findById(postId).map(blogPost->{
	 * 
	 * if(!blogPost.getBlog().getUser().getEmail().equals(ownerEmail)||(!blogPost.
	 * getCreatedBy().equals(ownerEmail))) throw new
	 * IllegalArgumentException("failed to publish the blog post"); Publish
	 * publish=null; if(blogPost.getPublish()!=null) {
	 * publish=mapToPublishRequest(publishRequest,blogPost.getPublish()); } else {
	 * publish=mapToPublishRequest(publishRequest, new Publish()); }
	 * if(publishRequest.getSchedule()!=null) {
	 * if(blogPost.getPostType().equals(PostType.SCHEDULE)) {
	 * blogPost.setPostType(blogPost.getPostType()); } else{
	 * publish.setSchedule(scheduleRepository.save(mapToScheduleEntity(
	 * publishRequest.getSchedule(), new Schedule())));
	 * blogPost.setPostType(PostType.PUBLISHED); } } else {
	 * blogPost.setPostType(PostType.PUBLISHED); } publish.setBlogPost(blogPost);
	 * blogPost.setPublish(publish); publishRepository.save(publish);
	 * blogPostRepository.save(blogPost); return
	 * ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.CREATED.value())
	 * .setMessage("the blog post draft created successfully")
	 * .setData(mapToBlogPostResponse(publish)));
	 * 
	 * }).orElseThrow(()->new
	 * BlogPostNotFoundByIdException("blogPost not find by Id")); }
	 */
	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(PublishRequest publishRequest,int postId){

		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(blogPost->{
			System.out.println(blogPost.getBlog().getUser().getEmail());
			System.out.println(blogPost. getCreatedBy());
			if(!blogPost.getBlog().getUser().getEmail().equals(email)||(!blogPost.
					getCreatedBy().equals(email)))
				throw new IllegalArgumentException("failed to publish the blog post");
			Publish publish=null;
			if(blogPost.getPublish()!=null) {
				publish=mapToPublishRequest(publishRequest,blogPost.getPublish());
			}
			else {
				publish=mapToPublishRequest(publishRequest,new  Publish());
			}
			if(publishRequest.getSchedule()!=null) {
				if(!publishRequest.getSchedule().getDateTime().isAfter(LocalDateTime.now())) {
					throw new TimeDateNotValidException("date and time is not valided");
				}
				if(publish.getSchedule()==null) {
					publish.setSchedule(scheduleRepository.save(mapToScheduleEntity(publishRequest.getSchedule(), new Schedule())));
					blogPost.setPostType(PostType.SCHEDULE);
				}
				else{
					publish.setSchedule(scheduleRepository.save(mapToScheduleEntity(publishRequest.getSchedule(), publish.getSchedule())));
					blogPost.setPostType(PostType.SCHEDULE);
				}
			}
			else {
				blogPost.setPostType(PostType.PUBLISHED);
			}
			publish.setBlogPost(blogPost);
			blogPost.setPublish(publish);
			publishRepository.save(publish);
			blogPostRepository.save(blogPost);
			return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.CREATED.value())
					.setMessage("the blog post draft created successfully")
					.setData(mapToBlogPostResponse(publish)));

		}).orElseThrow(()-> new BlogPostNotFoundByIdException("blog post is not found by Id"));

	}


	Schedule mapToScheduleEntity(ScheduleRequest scheduleRequest,Schedule s) {
		s.setDateTime(scheduleRequest.getDateTime());

		return s;
	}


	PublishResponse mapToBlogPostResponse(Publish publish) {
		return  PublishResponse.builder()
				.publishId(publish.getPublishId())
				.seoTitle(publish.getSeoTitle())
				.seoDescription(publish.getSeoDescription())
				.seoTopics(publish.getSeoTopics())
				.createdAt(publish.getCreatedAt())
				.build();

	}
	public Publish mapToPublishRequest(PublishRequest publishRequest,Publish p) {

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
			if(blogPost.getPostType()!=PostType.DRAFT) {
				blogPost.setPostType(PostType.DRAFT);

			}
			blogPostRepository.save(blogPost);
			return ResponseEntity.ok(response.setStatuscode(HttpStatus.CREATED.value())
					.setMessage("the blog post published into draft created successfully")
					.setData(mapToBlogPostResponse(blogPost)));

		}).orElseThrow(()->new BlogPostNotFoundByIdException("BlogPost id is not found"));

	}







}
