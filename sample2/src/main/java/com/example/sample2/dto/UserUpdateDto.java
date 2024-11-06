package com.example.sample2.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UserUpdateDto {
	
	private Integer userId;
	@NotEmpty
	private String address;
	@NotBlank
	private String tel;
	@Email
	private String email;
	
}
