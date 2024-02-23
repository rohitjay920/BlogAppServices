package com.project.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {
	
	private int categoryId;
	@NotEmpty
	private String categoryTitle;
	@NotEmpty
	@Size(min=10,message="min size of category description is 10")
	private String categoryDescription ;
}
