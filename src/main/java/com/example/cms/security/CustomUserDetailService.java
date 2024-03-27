package com.example.cms.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.userrepository.UserRepository;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private UserRepository ur;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
System.out.println(ur.findByEmail(username));
		return ur.findByEmail(username)
				.map((user)->new CustomUserDetails(user))
				.orElseThrow(()->new UsernameNotFoundException("user email is not found in database"));
	}
}
