package com.sanket.projects.booklisting_site.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sanket.projects.booklisting_site.entities.Book;

import jakarta.persistence.EntityManager;

@Repository
public class BookDAO
{

	// entity manager
	private final EntityManager entityManager;
	
	// DI
	public BookDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	// save a book
	public Book save(Book book) {
		return entityManager.merge(book);
	}

	// get all books
	public List<Book> findAll()
	{
		return entityManager.createQuery("FROM Book", Book.class).getResultList();
	}
	
	// get book by id
	public Book findById(int id)
	{
		return entityManager.find(Book.class, (Integer)id);
	}

	// delete a book
	public void delete(Book book)
	{
	
		entityManager.remove(book);
		
	}
	
}
