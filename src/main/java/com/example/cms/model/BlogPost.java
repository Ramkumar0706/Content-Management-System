package com.example.cms.model;

import java.time.LocalDateTime;


import com.example.cms.enums.PostType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@EntityListeners(AuditingEntityListener.class)
public class BlogPost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	
	private String titile;
	private String sutTitle;
	private String summary;
	
	@Enumerated(EnumType.STRING)
	private PostType postType;
	
	//@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	//@LastModifiedDate
	private LocalDateTime lastModifiedAt;
	
	//@CreatedBy
	private String createdBy;
	
	//@LastModifiedBy
	private String lastModifiedBy;
	
	@ManyToOne
	private Blog blog;
	
	@OneToOne
	private Publish publish;
}
