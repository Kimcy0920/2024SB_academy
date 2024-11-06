package com.example.sample2.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author
 *
 */
@Data
public class DepartmentFormDto {
	private Integer id;
	@NotNull
	@NotBlank
	@NotEmpty
	private String name;
	@NotNull
	private Integer collegeId;
	private String collegeName;
}
