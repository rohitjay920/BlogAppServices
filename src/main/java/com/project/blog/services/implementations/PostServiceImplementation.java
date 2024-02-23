package com.project.blog.services.implementations;




import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Category;
import com.project.blog.entities.Post;
import com.project.blog.entities.User;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payload.PostDto;
import com.project.blog.payload.PostResponse;
import com.project.blog.repositories.CategoryRepository;
import com.project.blog.repositories.PostRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.services.PostService;

@Service
public class PostServiceImplementation implements PostService{
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public PostDto createPost(PostDto postDto, int userId, int categoryId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID: "+userId+" not found in DB"));
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category with ID: "+categoryId+" not found in DB"));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setImageName("default.png");
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = this.postRepository.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post with ID: "+postId+" not found in DB"));
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName("postDto.png");
		
		Post updatedPost = this.postRepository.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(int postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post with ID: "+postId+" not found in DB"));
		this.postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPosts(int pageNumber,int pageSize,String sortBy,String sortDirection) {
		/*
		Pageable must be imported from org.springframework.data.domain.Pageable
		Page must be imported from org.springframework.data.domain.Page
		PageRequest must be imported from org.springframework.data.domain.PageRequest
		*/
		//Sort.by(sortBy).descending(); to sort post in descending order
		
		//sorting using ternary operator
		Sort sort = (sortDirection.equalsIgnoreCase("descending"))?Sort.by(sortBy).descending():Sort.by(sortBy);
		
		//sorting using conditional statements
		
//		Sort sort = null;
//		if(sortDirection.equalsIgnoreCase("descending")) {
//			sort = Sort.by(sortBy).descending();
//		}
//		else {
//			sort = Sort.by(sortBy);
//		}
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepository.findAll(p);
		List<Post> allPost = pagePost.getContent();
		List<PostDto> postDto = allPost.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse pr = new PostResponse();
		pr.setContent(postDto);
		pr.setPageNumber(pagePost.getNumber());
		pr.setPageSize(pagePost.getSize());
		pr.setTotalElements(pagePost.getTotalElements());
		pr.setTotalPages(pagePost.getTotalPages());
		pr.setLastpage(pagePost.isLast());
		return pr;
	}

	@Override
	public PostDto getPostById(int postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post with ID: "+postId+" not found in DB"));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto; 	
	}

	@Override
	public List<PostDto> getPostByCategory(int categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category with ID: "+categoryId+" not found in DB"));
		List<Post> posts = this.postRepository.findByCategory(category);
		
		List<PostDto> postDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> getPostByUser(int userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID: "+userId+" not found in Db"));
		List<Post> posts = this.postRepository.findByUser(user);
		List<PostDto> postDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> list = this.postRepository.findByTitleContaining(keyword);
		List<PostDto> recoveredList = list.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return recoveredList;
	}
	
	
	
}
