package com.project.blog.services;

import java.util.List;

import com.project.blog.payload.CategoryDto;

public interface CategoryService {
	
	//create
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	public CategoryDto updateCategory(CategoryDto categoryDto,int categoryId);
	
	//get
	public CategoryDto getCategoryByID(int categoryId);
	
	//get all
	public List<CategoryDto> getAllCategory();
	
	//delete
	public void deleteCategory(int categoryId);
}
