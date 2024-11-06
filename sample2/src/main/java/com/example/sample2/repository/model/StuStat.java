package com.example.sample2.repository.model;

import lombok.Data;

import java.sql.Date;

@Data
public class StuStat {

	private Integer id;
	private Integer studentId;
	private String status;
	private Date fromDate;
	private Date toDate;
	
}
