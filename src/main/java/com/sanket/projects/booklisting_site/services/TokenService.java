package com.sanket.projects.booklisting_site.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.sanket.projects.booklisting_site.entities.User;
import com.sanket.projects.booklisting_site.repositories.UserRepository;

@Service
public class TokenService
{

	private final JwtEncoder jwtEncoder;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	// DI
	public TokenService(JwtEncoder jwtEncoder, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.jwtEncoder = jwtEncoder;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

	// to create account
	public String createAccount(User user)
	{
		// first will check if user already exists
		if (userRepository.findByEmail(user.getEmail()) != null) {
			return "User already exists.";
		}
		
		// encode password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// set id to 0, so that it is auto generated
		user.setId(0);
		
		// save user
		userRepository.save(user);
		
		return "User created.";
		
	}
	
}
