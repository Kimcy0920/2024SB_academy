package com.example.sample2.repository.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Student {
	
	private Integer id;
	private String name;
	private Date birthDate;
	private String gender;
	private String address;
	private String tel;
	private String email;
	private Integer deptId;
	private Integer grade;
	private Integer semester;
	private Date entranceDate;
	private Date graduationDate;
}
