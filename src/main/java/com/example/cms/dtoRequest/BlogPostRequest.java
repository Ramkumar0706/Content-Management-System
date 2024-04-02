package com.example.cms.dtoRequest;



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
	
	

}
