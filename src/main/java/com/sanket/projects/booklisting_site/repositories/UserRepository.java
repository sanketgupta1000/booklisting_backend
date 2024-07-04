package com.sanket.projects.booklisting_site.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanket.projects.booklisting_site.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>
{

	User findByEmail(String email);
	
}
