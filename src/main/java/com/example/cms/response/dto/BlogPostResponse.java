package com.example.cms.response.dto;

import java.time.LocalDateTime;

import com.example.cms.enums.PostType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class BlogPostResponse {
	private int postId;
	private String title;
	private String subTitle;
	private PostType blogPost;
	private String summary;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	private String createdBy;
	private String lastModifiedBy;
	
	private PublishResponse publishResponse;


}
