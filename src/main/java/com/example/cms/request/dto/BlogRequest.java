package com.example.cms.request.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequest {

	@NotBlank(message = "Title is required")
	@Pattern(regexp = "^[a-zA-Z ]+$")
	private String title;
	private String[] topics;
	private String about;
}
