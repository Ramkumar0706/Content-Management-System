package com.example.cms.response.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PublishResponse {
	private int publishId;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	private LocalDateTime createdAt;
	

}
