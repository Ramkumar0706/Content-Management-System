package com.example.cms.utility;



import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.cms.enums.PostType;
import com.example.cms.model.BlogPost;
import com.example.cms.repository.BlogPostRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class ScheduleJobs {
	private BlogPostRepository blogPostRepository;
	@Scheduled(fixedDelay = 1000l)
	public void publishSchedulerBlogPost() {
		System.out.println("hello");
		List<BlogPost> postType = blogPostRepository.findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime.now(),PostType.SCHEDULE);

		postType.stream().map(post->{
			post.setPostType(PostType.PUBLISHED);
			return post;
		}).collect(Collectors.toList());
		blogPostRepository.saveAllAndFlush(postType);
	}

}
