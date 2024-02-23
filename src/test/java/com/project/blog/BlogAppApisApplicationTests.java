package com.project.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.blog.repositories.UserRepository;

@SpringBootTest
class BlogAppApisApplicationTests {
	
	@Autowired
	private UserRepository userRepository;
	@Test
	void contextLoads() {
	}
	
	@Test
	void test() {
		System.out.println(userRepository.getClass().getName());
		System.out.println(userRepository.getClass().getPackageName());
	}

}
