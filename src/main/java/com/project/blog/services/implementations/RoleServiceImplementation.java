package com.project.blog.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Role;
import com.project.blog.payload.RoleDto;
import com.project.blog.repositories.RoleRepository;
import com.project.blog.services.RoleService;

@Service
public class RoleServiceImplementation implements RoleService{
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public RoleDto createRole(RoleDto roleDto) {
		Role role = this.modelMapper.map(roleDto, Role.class);
		Role savedRole = roleRepository.save(role);
		return this.modelMapper.map(savedRole, RoleDto.class);
	}
}
