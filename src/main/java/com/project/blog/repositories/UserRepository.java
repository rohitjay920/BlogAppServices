package com.project.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{

}
