package com.sanket.projects.booklisting_site.controllers;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanket.projects.booklisting_site.services.FileService;

@RestController
@RequestMapping("/api")
public class FileController
{

	// service
	private final FileService fileService;
	
	// DI
	public FileController(FileService fileService)
	{
		this.fileService = fileService;
	}
	
	// get file by path
	@GetMapping("/files")
	public ResponseEntity<Resource> getFileByPath(@RequestParam("path") String path) throws IOException
	{
		return fileService.getFileByPath(path);
	}
	
}
