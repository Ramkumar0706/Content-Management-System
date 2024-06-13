package com.example.cms.request.dto;



import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlogPostRequest {
	private String title;
	private String subTitile;
	private String summary;
	private String createdBy;
	private String lastModifiedBy;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	

}
