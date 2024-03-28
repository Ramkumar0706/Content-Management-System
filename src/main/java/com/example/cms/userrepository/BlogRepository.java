package com.example.cms.userrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.usermodel.Blog;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer>  {
	
boolean existsByTitle(String titile);
}
