package com.project.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.payload.ResponseStructure;
import com.project.blog.payload.RoleDto;
import com.project.blog.services.RoleService;

@RestController
@RequestMapping("/api")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/save_Role")
	public ResponseEntity<ResponseStructure<RoleDto>> saveRole(@RequestBody RoleDto roleDto){
		RoleDto role  = this.roleService.createRole(roleDto);
		ResponseStructure<RoleDto> rs = new ResponseStructure();
		rs.setData(role);
		rs.setMessage("Success");
		rs.setStatusCode(HttpStatus.CREATED.value());
		
		return new ResponseEntity<ResponseStructure<RoleDto>>(rs,HttpStatus.OK);
	}
}
