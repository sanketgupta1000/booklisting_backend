package com.sanket.projects.booklisting_site.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService
{

	// root folder where files are stored
	@Value("${file.root}")
	private String rootFolder;
	
	// save file
	public String saveFile(MultipartFile file) throws IllegalStateException, IOException {
		// generate a random name for the file
		String fileName = UUID.randomUUID().toString();

		// get the extension of the file
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

		// create a path
		String path = rootFolder + "/" + fileName + extension;

		// save the file
		file.transferTo(new File(path));
		

		// return the path
		return path;
	}

	// delete file
	public void deleteFile(String filePath)
	{
		
		new File(filePath).delete();
		
	}

	// get file by path
	public ResponseEntity<Resource> getFileByPath(String path) throws IOException
	{
		try {
            Path filePath = Paths.get(path);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().contentType(MediaType.valueOf("image/png")).body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
	}
}
