package com.example.cms.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.enums.PostType;
import com.example.cms.model.BlogPost;
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost,Integer> {

	Optional<BlogPost> findByPostIdAndPostType(int postId, PostType published);

	List<BlogPost> findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime now, PostType schedule);

	 }
