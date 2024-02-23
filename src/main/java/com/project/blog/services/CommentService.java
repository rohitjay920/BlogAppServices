package com.project.blog.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.entities.Comment;
import com.project.blog.payload.CommentDto;

public interface CommentService{
	public CommentDto createComment(CommentDto comment,int postId);
	public void deleteComment(int commentId);
}
