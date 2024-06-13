 package com.example.cms.service.impl;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.exception.BlogNotFoundException;
import com.example.cms.exception.BlogTitleAlreadyExistException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PanelNotFoundException;
import com.example.cms.exception.TitleEmptyException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.Blog;
import com.example.cms.model.ContributionPanel;
import com.example.cms.model.User;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributerRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.request.dto.BlogRequest;
import com.example.cms.response.dto.BlogResponse;
import com.example.cms.response.dto.ContributerPanelResponse;
import com.example.cms.response.dto.UserResponse;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService{
	private BlogRepository blogRepository;
	private UserRepository userRepository;
	private ContributerRepository contributerRepository;
	private ResponseStructure<BlogResponse> response;
	private ResponseStructure<UserResponse> userResponse;
	private ResponseStructure<ContributionPanel> contributerResponse;

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> blogRegister(BlogRequest blogRequest,int userId) {	
		return userRepository.findById(userId).map(user->{
			if(blogRepository.existsByTitle(blogRequest.getTitle())) 
				throw new BlogTitleAlreadyExistException("Title is already Exists");

			if(blogRequest.getTopics().length<1) 
				throw new TitleEmptyException("topic is empty");
			ContributionPanel panel=new ContributionPanel();
			ContributionPanel panel2 = contributerRepository.save(panel);
			Blog blogs=mapToBlog(blogRequest);
			blogs.setUser(user);
			blogs.setContributerpanel(panel2);
			Blog bg=blogRepository.save(blogs);
			user.getBlogs().add(bg);
			userRepository.save(user);

			return  ResponseEntity.ok(response.setStatuscode(HttpStatus.CREATED.value())
					.setMessage("user Register successfully")
					.setData(matToResponse(blogs)));
		}).orElseThrow(()->new UserNotFoundByIdException("user Not Found Byb Id"));

	}

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanel>>  addContributer(int userId,int panelId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(owner->{
			System.out.println("ererrre");
			return contributerRepository.findById(panelId).map(panel->{
				if(!blogRepository.existsByUserAndContributerpanel(owner,panel))
					throw new IllegalAccessRequestException("Failed to add contributor");
				return userRepository.findById(userId).map(contributor->{
					System.out.println("ererrre");
					panel.getContributers().add(contributor);
					ContributionPanel save = contributerRepository.save(panel);
					System.out.println("ererrre");
					return ResponseEntity.ok(contributerResponse.setStatuscode(HttpStatus.OK.value())
							.setMessage("contributor added successfully")
							.setData(save));
				}).orElseThrow(()-> new UserNotFoundByIdException("Failed to add contributor"));
			}).orElseThrow(()->new PanelNotFoundException("Failed to fetch panel"));
		}).get();
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
				.build();
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

	public ContributerPanelResponse mapToContributerPanelResponse(List<User> users,User owner) {
		return ContributerPanelResponse.builder().users(users).owner(owner).build();
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> removeUserFromContributionPanel(int userId,int panelId){
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map((owner)->{
			return contributerRepository.findById(panelId).map((panel)->{
				if(!blogRepository.existsByUserAndContributerpanel(owner,panel))
					throw new IllegalAccessRequestException("Failed to add contributor");
				return userRepository.findById(userId).map((user)->{
					panel.getContributers().remove(user);
					contributerRepository.save(panel);
					return ResponseEntity.ok(userResponse.setStatuscode(HttpStatus.CREATED.value())
							.setMessage("user Register successfully")
							.setData(matToResponse(user)));
				}).orElseThrow(()-> new UserNotFoundByIdException("Failed to add contributor"));
			}).orElseThrow(()->new PanelNotFoundException("Failed to fetch panel"));
		}).get();
	}
	private UserResponse matToResponse(User saveUser) {
		return UserResponse.builder()
				.userId(saveUser.getUserId())
				.username(saveUser.getUsername())
				.userEmail(saveUser.getEmail())
				.createdAt(saveUser.getCreatedAt())
				.modifiedAt(saveUser.getLastModifiedAt())
				.build();

	}
	private Blog mapToBlog(BlogRequest blogRequest) {
		Blog blog = new Blog();
		blog.setTitle(blogRequest.getTitle());
		blog.setTopics(blogRequest.getTopics());
		blog.setAbout(blogRequest.getAbout());
		return blog;
	}




}
