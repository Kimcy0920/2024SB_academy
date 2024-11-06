package com.example.sample2.dto;

import com.example.sample2.utils.Define;
import lombok.Data;

@Data
public class CurrentSemesterSubjectSearchFormDto {

	private String type;
	private Integer deptId;
	private String name;
	
	private Integer subYear = Define.CURRENT_YEAR;
	private Integer semester = Define.CURRENT_SEMESTER;
	
	private Integer page;
	
}
