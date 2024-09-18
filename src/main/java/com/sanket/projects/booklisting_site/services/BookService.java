package com.sanket.projects.booklisting_site.services;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sanket.projects.booklisting_site.entities.Book;
import com.sanket.projects.booklisting_site.entities.User;
import com.sanket.projects.booklisting_site.repositories.BookDAO;

import jakarta.transaction.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookService
{

	// DAO
	private BookDAO bookDAO;
	private FileService fileService;
	 
	 // DI
	 public BookService(BookDAO bookDAO, FileService fileService) {
		 this.bookDAO = bookDAO;
		 this.fileService = fileService;
	 }
	 
	 // method to create a book
	 @Transactional
	 public Book createBook(Book book, User user, MultipartFile coverImage) throws IllegalStateException, IOException
	 {
		 // set id to 0
		 book.setId(0);
		 
		 // set the author
		 book.setAuthor(user);
		 
		 // set id of each link to 0, and set book
		 book.getLinks().forEach(link ->{
			 link.setId(0);
			 link.setBook(book);
		 });
		 
		 // save image and get path
		 String path = fileService.saveFile(coverImage);
		 
		 // set the path
		 book.setCoverImagePath(path);
		 
		 // save the book
		 return bookDAO.save(book);
	 }

	 // method to get all books
	 @Transactional
	public List<Book> getAllBooks()
	{
		return bookDAO.findAll();
	}

	// method to get book by id
	public Book getBookById(int id)
	{
	    Book book = bookDAO.findById(id);
		if (book == null)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
		}
		return book;
	}

	// method to update a book
	@Transactional
	public Book updateBook(Book book, User user, MultipartFile coverImage) throws IllegalStateException, IOException
	{
//		System.out.println("Image not provided: "+coverImage.isEmpty());
		// get old book
		Book oldBook = bookDAO.findById(book.getId());
		
		// check if it is user's book
		if (!oldBook.getAuthor().getEmail().equals(user.getEmail())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot update book not created by user");
		}
		
		// set new title
		oldBook.setTitle(book.getTitle());
		
		// checking if image provided
		if(!coverImage.isEmpty())
		{
			// image provided, so need to delete old and add new
			
			// delete old image
			fileService.deleteFile(oldBook.getCoverImagePath());
			
			// save new image
			String newImagePath = fileService.saveFile(coverImage);
			
			// set new image path
			oldBook.setCoverImagePath(newImagePath);
		}
		
		// update links
		oldBook.getLinks().removeIf(link->true);
		oldBook.getLinks().addAll(book.getLinks());
		
		// set id and book of each link
		oldBook.getLinks().forEach(link -> {
			link.setId(0);
			link.setBook(oldBook);
		});
		
		// save the book
		return bookDAO.save(oldBook);
	}

	// method to delete a book
	@Transactional
	public void deleteBook(int id, User user)
	{
		
		// get the book
		Book book = bookDAO.findById((Integer)id);
		
		// check if it is user's book
		if (!book.getAuthor().getEmail().equals(user.getEmail()))
		{
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete book not created by user");
		}
		
		// get the image path
		String imagePath = book.getCoverImagePath();
		
		// delete the image
		fileService.deleteFile(imagePath);
		
		// delete
		bookDAO.delete(book);
		
	}
	
}
