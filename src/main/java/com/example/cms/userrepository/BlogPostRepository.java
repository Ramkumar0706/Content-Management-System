package com.example.cms.userrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.usermodel.BlogPost;
import com.example.cms.usermodel.User;

public interface BlogPostRepository extends JpaRepository<BlogPost,Integer> {

	boolean existsByUserAndContributerpanel(int panelId, User user);

}
