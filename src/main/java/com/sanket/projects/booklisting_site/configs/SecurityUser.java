package com.sanket.projects.booklisting_site.configs;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.sanket.projects.booklisting_site.entities.User;

// my custom user details to wrap my custom user entity
public class SecurityUser implements UserDetails
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5129904364599836089L;
	private final User user;

	public SecurityUser(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		// return an empty list of authorities, since no need in this app
		return new ArrayList<SimpleGrantedAuthority>();
		
	}

	@Override
	public String getPassword()
	{
		
		return user.getPassword();
		
	}

	@Override
	public String getUsername()
	{
		
		return user.getEmail();
		
	}

	public User getUser() {
		return user;
	}
	
	

}
