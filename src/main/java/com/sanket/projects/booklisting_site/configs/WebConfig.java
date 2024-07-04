package com.sanket.projects.booklisting_site.configs;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{

	private final CurrentUserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver;
	
	// DI
	public WebConfig(CurrentUserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver) {
		super();
		this.currentUserHandlerMethodArgumentResolver = currentUserHandlerMethodArgumentResolver;
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(currentUserHandlerMethodArgumentResolver);
	}
	
}
