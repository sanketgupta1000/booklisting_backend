package com.sanket.projects.booklisting_site.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sanket.projects.booklisting_site.configs.SecurityUser;
import com.sanket.projects.booklisting_site.entities.User;
import com.sanket.projects.booklisting_site.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
	
	// repository
	private final UserRepository userRepository;
	
	//DI
	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		
		System.out.println("loadUserByUsername() called");
		
		User user = userRepository.findByEmail(username);
		
		// if null, means user not found
		if(user==null)
		{
			throw new UsernameNotFoundException("User with email: "+username+" not found.");
		}
		
		return new SecurityUser(user);
	}


}
