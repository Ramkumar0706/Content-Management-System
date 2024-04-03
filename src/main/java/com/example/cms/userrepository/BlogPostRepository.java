package com.example.cms.userrepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.cms.dtoResponse.BlogPostResponse;
import com.example.cms.enums.PostType;
import com.example.cms.usermodel.BlogPost;
import com.example.cms.usermodel.User;
import com.example.cms.utility.ResponseStructure;
@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost,Integer> {

	Optional<BlogPost> findByPostIdAndPostType(int postId, PostType published);


}
