package com.project.blog.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Category;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payload.CategoryDto;
import com.project.blog.repositories.CategoryRepository;
import com.project.blog.services.CategoryService;

@Service
public class CategoryServiceImplementation implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.categoryDtoToCategory(categoryDto);
		Category savedCategory = this.categoryRepository.save(category);
		return this.categoryToCategoryDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category with ID: "+categoryId+" not found in DB"));
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		
		Category updatedCategory = this.categoryRepository.save(category);
		return this.categoryToCategoryDto(updatedCategory);
	}

	@Override
	public CategoryDto getCategoryByID(int categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category with ID: "+categoryId+" not found in DB"));
		return this.categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> list = this.categoryRepository.findAll();
		
		List<CategoryDto> dtoList = list.stream().map(category->this.categoryToCategoryDto(category)).collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category with ID: "+categoryId+" not found in DB"));
		this.categoryRepository.delete(category);
	}
	
	private Category categoryDtoToCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	private CategoryDto categoryToCategoryDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
