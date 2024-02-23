 package com.project.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.blog.config.AppConstants;
import com.project.blog.payload.PostDto;
import com.project.blog.payload.PostResponse;
import com.project.blog.payload.ResponseStructure;
import com.project.blog.services.FileService;
import com.project.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/save")
	public ResponseEntity<ResponseStructure<PostDto>> createPost(@RequestBody PostDto postDto,@PathVariable
		int userId,@PathVariable int categoryId){
				
		PostDto post = this.postService.createPost(postDto, userId, categoryId);
		ResponseStructure<PostDto> rs = new ResponseStructure();
		rs.setData(post);
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.CREATED.value());
		
		return new ResponseEntity<ResponseStructure<PostDto>>(rs,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/category/{id}/get")
	public ResponseEntity<ResponseStructure<List<PostDto>>> getPostByCategory(@PathVariable int id){
		List<PostDto> posts = this.postService.getPostByCategory(id);
		ResponseStructure<List<PostDto>> rs = new ResponseStructure();
		rs.setData(posts);
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<List<PostDto>>>(rs,HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}/get")
	public ResponseEntity<ResponseStructure<List<PostDto>>> getPostByUser(@PathVariable int id){
		List<PostDto> post = this.postService.getPostByUser(id);
		ResponseStructure<List<PostDto>> rs = new ResponseStructure();
		rs.setData(post);
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<List<PostDto>>>(rs,HttpStatus.OK);
	}
	
	//to hide the page number and size from user in url for client we use post method
	@GetMapping("/post/getAll")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) int pageNumber,
		@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) int pageSize,
		@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
		@RequestParam(value="sortDirection",defaultValue=AppConstants.SORT_DIRECTION,required=false) String sortDirection){
		PostResponse postResponse = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDirection);
		
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/post/{postId}/get")
	public ResponseEntity<ResponseStructure<PostDto>> getPostById(@PathVariable int postId){
		PostDto post = this.postService.getPostById(postId);
		ResponseStructure<PostDto> rs = new ResponseStructure();
		rs.setData(post);
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<PostDto>>(rs,HttpStatus.OK);
	}
	
	@PutMapping("/post/{id}/update")
	public ResponseEntity<ResponseStructure<PostDto>> updatePostById(@RequestBody PostDto postDto,@PathVariable int id){
		PostDto post = this.postService.updatePost(postDto, id);
		ResponseStructure<PostDto> rs = new ResponseStructure();
		rs.setData(post);
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<PostDto>>(rs,HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{id}/delete")
	public ResponseEntity<ResponseStructure<String>> deletePost(@PathVariable int id){
		this.postService.deletePost(id);
		ResponseStructure<String> rs = new ResponseStructure();
		rs.setData("Post with ID: "+id+" has been deleted");
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}
	
	//check video number 26 to learn something new in the end 
	@GetMapping("/post/search/{keywords}")
	public ResponseEntity<ResponseStructure<List<PostDto>>> searchPostByTitle(@PathVariable String keywords){
		List<PostDto> list = this.postService.searchPost(keywords);
		ResponseStructure<List<PostDto>> rs = new ResponseStructure<List<PostDto>>();
		rs.setData(list);
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<List<PostDto>>>(rs,HttpStatus.OK);
	}
	
	//upload image to file 
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile file,@PathVariable int postId) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, file);
		postDto.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	//method to serve files
	//image name must contain extension as well and run the url in browser
	@GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
		InputStream in = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(in, response.getOutputStream());
	}
}
