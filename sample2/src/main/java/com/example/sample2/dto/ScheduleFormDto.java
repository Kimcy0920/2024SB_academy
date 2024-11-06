package com.example.sample2.dto;

import lombok.Data;

@Data
public class ScheduleFormDto {

	private Integer id;
	private Integer staffId;
	private String startDay;
	private String endDay;
	private String information;
}
