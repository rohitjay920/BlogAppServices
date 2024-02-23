package com.project.blog.services;

import java.util.List;

import com.project.blog.payload.PostDto;
import com.project.blog.payload.PostResponse;

public interface PostService {
	
	public PostDto createPost(PostDto postDto,int userId,int categoryID);
	
	public PostDto updatePost(PostDto postDto,int postId);
	
	public void deletePost(int postId);
	
	public PostResponse getAllPosts(int pageNumber,int pageSize,String sortBy,String sortDirection);
	
	public PostDto getPostById(int postId);
	
	public List<PostDto> getPostByCategory(int categoryId);
	
	public List<PostDto> getPostByUser(int userId);
	
	public List<PostDto> searchPost(String keyword);
	
}
