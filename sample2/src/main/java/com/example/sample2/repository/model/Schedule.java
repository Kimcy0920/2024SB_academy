package com.example.sample2.repository.model;

import lombok.Data;

@Data
public class Schedule {

	private Integer id;
	private Integer staffId;
	private String startDay;
	private String endDay;
	private String information;
	private Integer years;
	private Integer months;
}
