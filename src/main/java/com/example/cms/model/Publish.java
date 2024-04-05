package com.example.cms.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Publish {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int publishId;
	
	private String seoTitle;
	private String seoDescription;
	private String[] seoTopics;
	@OneToOne
	private BlogPost blogPost;
	@CreatedDate
	private LocalDateTime createdAt;
	@OneToOne
	private Schedule schedule;
	
}
