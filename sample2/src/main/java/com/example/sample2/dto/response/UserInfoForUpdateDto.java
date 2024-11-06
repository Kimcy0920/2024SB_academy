package com.example.sample2.dto.response;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserInfoForUpdateDto {

	@NotBlank
	private String address;
	@Size(min = 11, max = 13)
	private String tel;
	@Email
	private String email;
	
}
