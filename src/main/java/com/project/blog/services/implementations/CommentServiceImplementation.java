package com.project.blog.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Comment;
import com.project.blog.entities.Post;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payload.CommentDto;
import com.project.blog.repositories.CommentRepository;
import com.project.blog.repositories.PostRepository;
import com.project.blog.services.CommentService;

@Service
public class CommentServiceImplementation implements CommentService{
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepository postRepository;
	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post with ID: "+postId+" not found in DB"));
		Comment comment = this.modelMapper.map(commentDto,Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepository.save(comment);
		return this.modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment with ID: "+commentId+" not found in DB"));
		this.commentRepository.delete(comment);
	}

	

}
