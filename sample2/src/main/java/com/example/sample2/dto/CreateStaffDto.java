package com.example.sample2.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;

/**
 * staff_tb insert용
 * @author
 *
 */
@Data
public class CreateStaffDto {

	@NotEmpty
	@Size(min = 2, max= 30)
	private String name;
	private Date birthDate;
	private String gender;
	@NotEmpty
	private String address;
	@Size(min = 11, max = 13)
	private String tel;
	@Email
	private String email;
	
}
