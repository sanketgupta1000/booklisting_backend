package com.sanket.projects.booklisting_site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.sanket.projects.booklisting_site.configs.RsaKeyProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class BooklistingSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooklistingSiteApplication.class, args);
	}

}
