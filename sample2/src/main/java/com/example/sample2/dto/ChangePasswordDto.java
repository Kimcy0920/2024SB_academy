package com.example.sample2.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ChangePasswordDto {

	@Size(min = 6, max = 20, message = "패스워드는 6~20자 사이여야합니다.")
	private String beforePassword;
	@Size(min = 6, max = 20, message = "패스워드는 6~20자 사이여야합니다.")
	private String afterPassword;
	@Size(min = 6, max = 20, message = "패스워드는 6~20자 사이여야합니다.")
	private String passwordCheck;
	private Integer id;
	
}
