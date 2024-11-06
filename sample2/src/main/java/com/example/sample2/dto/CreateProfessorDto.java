package com.example.sample2.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.sql.Date;

/**
 * professor_tb insert용
 * @author
 *
 */
@Data
public class CreateProfessorDto {

	@NotEmpty
	@Size(min = 2, max= 30)
	private String name;
	private Date birthDate;
	private String gender;
	@NotEmpty
	private String address;
	@NotBlank
	private String tel;
	@Min(100)
	@Max(999)
	private Integer deptId;
	@Email
	private String email;
	
}
