package com.example.cms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.model.Blog;
import com.example.cms.model.ContributionPanel;
import com.example.cms.model.User;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer>  {
	
boolean existsByTitle(String titile);
boolean existsByUserAndContributerpanel(User owner, ContributionPanel panel);
}
