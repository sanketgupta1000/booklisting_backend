package com.sanket.projects.booklisting_site.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="books")
public class Book
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="title")
	private String title;
	
	@ManyToOne
	@JoinColumn(name="author_id")
	private User author;
	
	@Column(name="cover_image_path")
	private String coverImagePath;
	
	// the links of the book
	@OneToMany(mappedBy="book", cascade=CascadeType.ALL, orphanRemoval=true)
	@JsonIgnoreProperties("book")
	private List<BookLink> links;
	
	public Book() {

	}
	
	public Book(Integer id, String title, User author, String coverImagePath, List<BookLink> links) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.coverImagePath = coverImagePath;
		this.links = links;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public String getCoverImagePath() {
		return coverImagePath;
	}
	
	public void setCoverImagePath(String coverImagePath) {
		this.coverImagePath = coverImagePath;
	}
	
	public List<BookLink> getLinks() {
		return links;
	}
	
	public void setLinks(List<BookLink> links) {
		this.links = links;
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", coverImagePath=" + coverImagePath
				+ ", links=" + links + "]";
	}
}
