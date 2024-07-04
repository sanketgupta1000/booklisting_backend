package com.sanket.projects.booklisting_site.configs;

import java.security.Principal;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.sanket.projects.booklisting_site.annotations.CurrentUser;
import com.sanket.projects.booklisting_site.entities.User;

@Component
public class CurrentUserHandlerMethodArgumentResolver
		implements org.springframework.web.method.support.HandlerMethodArgumentResolver {

	// user details service
	private final UserDetailsService customUserDetailsService;
	
	// DI
	public CurrentUserHandlerMethodArgumentResolver(UserDetailsService customUserDetailsService)
	{
		super();
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter)
	{
		// parameter should have CurrentUser annotation
		// as well as should be of type User
		return parameter.getParameterAnnotation(CurrentUser.class) != null
				&& parameter.getParameterType().equals(User.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception
	{
		// getting principal
		Principal principal = webRequest.getUserPrincipal();
		
		// return the User
		return ((SecurityUser)customUserDetailsService.loadUserByUsername(principal.getName())).getUser();
	}

}
