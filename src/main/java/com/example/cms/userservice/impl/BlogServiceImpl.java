package com.example.cms.userservice.impl;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.dtoRequest.BlogRequest;
import com.example.cms.dtoResponse.BlogResponse;
import com.example.cms.exception.BlogNotFoundException;
import com.example.cms.exception.BlogTitleAlreadyExistException;
import com.example.cms.exception.TitleEmptyException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.usermodel.Blog;
import com.example.cms.usermodel.User;
import com.example.cms.userrepository.BlogRepository;
import com.example.cms.userrepository.UserRepository;
import com.example.cms.userservice.BlogService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService{
	private BlogRepository blogRepository;
	private UserRepository userRepository;
	private ResponseStructure<BlogResponse> response;
	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> blogRegister(BlogRequest blogRequest,int userId) {	
		return userRepository.findById(userId).map(user->{
			if(blogRepository.existsByTitle(blogRequest.getTitle())) 
				throw new BlogTitleAlreadyExistException("Title is already Exists");

			if(blogRequest.getTopics().length<1) 
				throw new TitleEmptyException("topic is empty");

			Blog blogs=mapToBlog(blogRequest);
			blogs.getUser().add(user);
			blogRepository.save(blogs);
			return  ResponseEntity.ok(response.setStatuscode(HttpStatus.CREATED.value())
					.setMessage("user Register successfully")
					.setData(matToResponse(blogs)));
		}).orElseThrow(()->new UserNotFoundByIdException("user Not Found Byb Id"));

	}

	@Override
	public ResponseEntity<Boolean> checkTitleExists(String title) {
		return ResponseEntity.ok(blogRepository.existsByTitle(title));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId) {

		return blogRepository.findById(blogId).map(user->ResponseEntity.ok(response.setStatuscode(HttpStatus.OK.value())
				.setMessage("Blog founded successfully")
				.setData(matToResponse(user))))
				.orElseThrow(()->new BlogNotFoundException("Blog is NotFound By Id"));
	}

	private BlogResponse matToResponse(Blog blogs) {
		return BlogResponse.builder()
				.blogId(blogs.getBlogId())
				.title(blogs.getTitle())
				.topics(blogs.getTopics())
				.about(blogs.getAbout())
				.user(blogs.getUser()).build();
	}
	private Blog mapToBlog(BlogRequest blogRequest) {
		Blog blog = new Blog();
		blog.setTitle(blogRequest.getTitle());
		blog.setTopics(blogRequest.getTopics());
		blog.setAbout(blogRequest.getAbout());
		return blog;
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData( BlogRequest blogRequest,int blogId) {



		return blogRepository.findById(blogId).map(blog->{
			if(blogRepository.existsByTitle(blogRequest.getTitle()))
				throw new BlogTitleAlreadyExistException("The title already present in the DataBase");

			if(blogRequest.getTopics().length<1) 
				throw new TitleEmptyException("topic is empty");

			Blog blogs=mapToBlog(blogRequest);
			blogs.setBlogId(blog.getBlogId());
			blogs.setUser(blog.getUser());
			blogRepository.save(blogs);
			return  ResponseEntity.ok(response.setStatuscode(HttpStatus.CREATED.value())
					.setMessage("user Register successfully")
					.setData(matToResponse(blogs)));
		}).orElseThrow(()-> new BlogNotFoundException("Blog is NotFound By Id"));
	}




}
