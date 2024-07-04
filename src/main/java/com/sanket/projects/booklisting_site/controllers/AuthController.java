package com.sanket.projects.booklisting_site.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.projects.booklisting_site.entities.User;
import com.sanket.projects.booklisting_site.services.TokenService;

@RestController
public class AuthController
{

	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	private final TokenService tokenService;

	// authentication manager
	private final AuthenticationManager authenticationManager;
	
	public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
		super();
		this.tokenService = tokenService;
		this.authenticationManager = authenticationManager;
	}
	
	// for getting the jwt token
	@PostMapping("/token")
	public String token(@RequestParam("username") String username, @RequestParam("password") String password)
	{
		
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		return tokenService.generateToken(auth);
	}
	
	// for creating account
	@PostMapping("/create-account")
	public String createAccount(@RequestBody User user)
	{
		return tokenService.createAccount(user);
	}
	
}
