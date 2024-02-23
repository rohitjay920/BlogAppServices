package com.project.blog.services.implementations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.blog.services.FileService;

@Service
public class FileServiceImplementation implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//name of file
		String name = file.getOriginalFilename();
		
		//creating random id for file
		String randomID = UUID.randomUUID().toString();
		
		String updatedFileName = randomID.concat(name.substring(name.lastIndexOf(".")));
		
		//get full path
		String filePath = path+File.separator+updatedFileName;
		
		//create folder if not present
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		//copying image into folder
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return updatedFileName;
	}
	//fileName must contain extension orelse we get FileNotFoundException
	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path+File.separator+fileName;
		InputStream is = new FileInputStream(fullPath);
		//DB logic to return InputStream
		return is;
	}
	
}
