package com.project.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.payload.CommentDto;
import com.project.blog.payload.ResponseStructure;
import com.project.blog.services.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	
	@PostMapping("/create/{postId}")
	public ResponseEntity<ResponseStructure<CommentDto>> createComment(@RequestBody CommentDto commentDto,@PathVariable int postId ){
		CommentDto comment = this.commentService.createComment(commentDto, postId);
		ResponseStructure<CommentDto> rs = new ResponseStructure<>();
		rs.setData(comment);
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.CREATED.value());
		
		return new ResponseEntity<ResponseStructure<CommentDto>>(rs,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<ResponseStructure<String>> deleteComment(@PathVariable int commentId){
		this.commentService.deleteComment(commentId);
		ResponseStructure<String> rs= new ResponseStructure<>();
		rs.setData("Comment deleted");
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}
}
