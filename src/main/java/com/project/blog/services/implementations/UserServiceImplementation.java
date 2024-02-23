package com.project.blog.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.User;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payload.UserDto;
import com.project.blog.repositories.UserRepository;
import com.project.blog.services.UserService;

@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepository.save(user);
		return this.userToDto(savedUser); 
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("Id: "+userId+" "+"not present in DB"));
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
	
		User updatedUser = this.userRepository.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("Id: "+userId+" "+"not present in DB"));
		return this.userToDto(user);
	}
	

	
	@Override
	public List<UserDto> getAllUsers() {
		List<User> list = this.userRepository.findAll();
		
		List<UserDto> userDtoList = list.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtoList;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user =this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("Id: "+userId+" "+"not found in DB"));
		this.userRepository.delete(user);
	}
	
	private User dtoToUser(UserDto userDto) {
		
//		User user = new User();
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	private UserDto userToDto(User user) {
//		UserDto userDto = new UserDto();
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
