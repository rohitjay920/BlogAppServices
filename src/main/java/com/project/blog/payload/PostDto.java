package com.project.blog.payload;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	//we only need title and content to set the data but to return it back in ResponseEntity we want all 
	//fields hence we will have all fields of Post in PostDto
	private int postId;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	private CategoryDto category;
	private UserDto user;
	private List<CommentDto> comment;
	/*
	we cannot have Category and User entity directly here as there will be recurssion in postman and causes 
	exception this is beacuse posts have user and user have multiple post, similarly posts have category and 
	category has multiple posts. To avoid recurssion we will have CategoryDto and UserDto instead of their 
	respective entities. These Dto's does not have posts in them , they just act as data tranfer object.
	*/
	
}
