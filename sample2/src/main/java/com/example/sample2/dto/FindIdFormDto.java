package com.example.sample2.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * id 찾기 폼
 * @author
 *
 */
@Data
public class FindIdFormDto {

	@NotBlank
	private String name;
	@Email
	private String email;
	private String userRole;
	
}
