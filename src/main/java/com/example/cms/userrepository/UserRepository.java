package com.example.cms.userrepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.usermodel.User;
@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{

	boolean existsByEmail(String userEmail);
	 Optional<User> findByEmail(String email);

}
