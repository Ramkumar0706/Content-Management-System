package com.example.cms.dtoRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PublishRequest {
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
}
