package com.project.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.payload.CategoryDto;
import com.project.blog.payload.ResponseStructure;
import com.project.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//create
	
	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto category = this.categoryService.createCategory(categoryDto);
		ResponseStructure<CategoryDto> rs = new ResponseStructure();
		 rs.setData(category);
		 rs.setStatusCode(HttpStatus.CREATED.value());
		 rs.setMessage("Success");
		 
		 return new ResponseEntity<ResponseStructure<CategoryDto>>(rs,HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseStructure<CategoryDto>> updateCategory(@Valid @PathVariable int id,@RequestBody CategoryDto categoryDto){
		CategoryDto category = this.categoryService.updateCategory(categoryDto, id);
		ResponseStructure<CategoryDto> rs = new ResponseStructure();
		 rs.setData(category);
		 rs.setStatusCode(HttpStatus.OK.value());
		 rs.setMessage("Success");
		 
		 return new ResponseEntity<ResponseStructure<CategoryDto>>(rs,HttpStatus.OK);
	}
	//get
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseStructure<CategoryDto>> getCategory(@PathVariable int id){
		CategoryDto category = this.categoryService.getCategoryByID(id);
		ResponseStructure<CategoryDto> rs = new ResponseStructure();
		 rs.setData(category);
		 rs.setStatusCode(HttpStatus.OK.value());
		 rs.setMessage("Success");
		 
		 return new ResponseEntity<ResponseStructure<CategoryDto>>(rs,HttpStatus.OK);
	}
	
	//get all
	@GetMapping("/getAll")
	public ResponseEntity<ResponseStructure<List<CategoryDto>>> getAllCategories(){
		List<CategoryDto> list = this.categoryService.getAllCategory();
		ResponseStructure<List<CategoryDto>> rs = new ResponseStructure();
		 rs.setData(list);
		 rs.setStatusCode(HttpStatus.OK.value());
		 rs.setMessage("Success");
		 
		 return new ResponseEntity<ResponseStructure<List<CategoryDto>>>(rs,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteCategory(@PathVariable int id){
		this.categoryService.deleteCategory(id);
		ResponseStructure<String> rs = new ResponseStructure();
		 rs.setData("Category has been deleted");
		 rs.setStatusCode(HttpStatus.OK.value());
		 rs.setMessage("Success");
		 
		 return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}
	
}
