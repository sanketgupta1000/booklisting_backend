package com.sanket.projects.booklisting_site.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sanket.projects.booklisting_site.annotations.CurrentUser;
import com.sanket.projects.booklisting_site.entities.Book;
import com.sanket.projects.booklisting_site.entities.User;
import com.sanket.projects.booklisting_site.services.BookService;

@RestController
@RequestMapping("/api")
public class BookController
{

	// service
	 private BookService bookService;
	 
	 // DI
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	// create a new book
	@PostMapping("/books")
	public Book createBook(@RequestPart("book") Book book, @CurrentUser User user, @RequestParam("cover_image") MultipartFile coverImage) throws IllegalStateException, IOException
	{
		return bookService.createBook(book, user, coverImage);
	}
	
	// get all books
	@GetMapping("/books")
	public List<Book> getAllBooks()
	{
		return bookService.getAllBooks();
	}
	
	// get book by id
	@GetMapping("/books/{id}")
	public Book getBookById(@PathVariable("id") int id)
	{
		return bookService.getBookById(id);
	}
	
	// update book
	@PutMapping("/books")
	public Book updateBook(@RequestPart("book") Book book, @CurrentUser User user, @RequestParam("cover_image") MultipartFile coverImage) throws IllegalStateException, IOException
	{
		return bookService.updateBook(book, user, coverImage);
	}
	
	// delete book
	@DeleteMapping("/books/{id}")
	public void deleteBook(@PathVariable("id") int id, @CurrentUser User user)
	{
		bookService.deleteBook(id, user);
	}
	
}
