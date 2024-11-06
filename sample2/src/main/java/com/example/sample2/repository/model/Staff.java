package com.example.sample2.repository.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Staff {

	private Integer id;
	private String name;
	private Date birthDate;
	private String gender;
	private String address;
	private String tel;
	private String email;
	private Date hireDate;
	
}
