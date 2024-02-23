package com.project.blog.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private int id;
	@NotNull
	@Size(min = 4,message="Name must contain min 4 characters")
	private String name;
	@NotEmpty
	private String about;
	//message only displays if the validation is failed
	//@Pattern(regexp="") can be used to set pattern for a field
	@Email(message="Email is not valid")
	private String email;
	@NotNull
	private String password;
}
