package com.sanket.projects.booklisting_site.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// to represent the link of a book

@Entity
@Table(name="book_links")
public class BookLink
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	// the name the author wants to give to the link
	@Column(name="link_name")
	private String linkName;
	
	// the actual link
	@Column(name="link")
	private String link;
	
	// the book to which this link belongs
	@ManyToOne
	@JoinColumn(name="book_id")
	@JsonIgnoreProperties("links")
	private Book book;
	
	public BookLink() {

	}

	public BookLink(Integer id, String linkName, String link, Book book) {
		super();
		this.id = id;
		this.linkName = linkName;
		this.link = link;
		this.book = book;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "BookLink [id=" + id + ", linkName=" + linkName + ", link=" + link + ", book=" + book + "]";
	}
	
	
}
