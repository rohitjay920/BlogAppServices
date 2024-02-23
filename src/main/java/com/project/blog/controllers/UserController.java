package com.project.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.entities.User;
import com.project.blog.payload.ResponseStructure;
import com.project.blog.payload.UserDto;
import com.project.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//POST-create user
	
	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<UserDto>> createUser(@Valid @RequestBody UserDto userDto){
		UserDto user = userService.createUser(userDto);
		ResponseStructure rs = new ResponseStructure();
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setData(user);
		
		return new ResponseEntity<ResponseStructure<UserDto>>(rs,HttpStatus.CREATED);
		
	}
	//PUT-update user
	@PutMapping("/update/{user_id}")
	public ResponseEntity<ResponseStructure<UserDto>> updateUser(@Valid @PathVariable int user_id,@RequestBody UserDto userDto){
		UserDto user = userService.updateUser(userDto, user_id);
		ResponseStructure<UserDto> rs =new ResponseStructure();
		rs.setData(user);
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<UserDto>>(rs,HttpStatus.OK);
	}
	
	//DELETE-delete user
	@DeleteMapping("/delete/{user_id}")
	public ResponseEntity<ResponseStructure<String>> deleteById(@PathVariable int user_id){
		userService.deleteUser(user_id);
		//Map map = Map.of("message", "User bearing ID: "+user_id+" has been deleted from the DB");
		String message = "User bearing ID: "+user_id+" has been deleted from the DB";
		ResponseStructure rs = new ResponseStructure();
		rs.setMessage("Success");
		rs.setData(message);
		//rs.setData(map);
		rs.setStatusCode(HttpStatus.NO_CONTENT.value());
		
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
		// return new ResponseEntity<ResponseStructure<Map>>(rs,HttpStatus.OK);
	}
	//GET-get user
	@GetMapping("/get_all")
	public ResponseEntity<ResponseStructure<List<UserDto>>> getAllUsers(){
		List<UserDto> user = userService.getAllUsers();
		ResponseStructure rs = new ResponseStructure();
		rs.setMessage("Success");
		rs.setData(user);
		rs.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<List<UserDto>>>(rs,HttpStatus.OK);
		
		/*This is a shortcut for creating a ResponseEntity Object with the status code OK, but the 
		below Statement will return ResponseEntity<List<UserDto>> hence it should be declared accordingly 
		in the method declaration statement*/
		
		//return ResponseEntity.ok(userService.getAllUsers());
	}
	
	//Get by id
	@GetMapping("/get/{user_id}")
	public ResponseEntity<ResponseStructure<UserDto>> getUserById(@PathVariable int user_id){
		UserDto user = userService.getUserById(user_id);
		ResponseStructure<UserDto> rs = new ResponseStructure();
		rs.setData(user);
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.OK.value());
		
		return new ResponseEntity<ResponseStructure<UserDto>>(rs,HttpStatus.OK);
	}
	
	
}
