package com.project.blog.services;

import java.util.List;


import com.project.blog.payload.UserDto;

public interface UserService {
	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto,Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userID);
}
