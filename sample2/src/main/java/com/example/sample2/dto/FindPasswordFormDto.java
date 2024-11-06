package com.example.sample2.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 비밀번호 찾기 폼
 * @author
 *
 */
@Data
public class FindPasswordFormDto {

	@NotBlank
	private String name;
	@Min(100000)
	private Integer id;
	@Email
	private String email;
	private String userRole;
	
}
