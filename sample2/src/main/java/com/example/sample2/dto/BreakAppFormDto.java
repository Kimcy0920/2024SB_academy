package com.example.sample2.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author
 *
 */

@Data
public class BreakAppFormDto {
	
	private Integer studentId;
	private Integer studentGrade;
	private Integer fromYear;
	private Integer fromSemester;
	@NotNull
	private Integer toYear;
	@NotNull
	private Integer toSemester;
	@NotNull
	private String type;
	
}
