package com.example.cms.dtoRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PublishRequest {
	@NotBlank
	@NotNull
	@NotEmpty
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
}
