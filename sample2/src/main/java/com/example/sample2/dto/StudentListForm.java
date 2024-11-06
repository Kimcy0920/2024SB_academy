package com.example.sample2.dto;

import lombok.Data;

/**
 * 학생 리스트 보기 폼
 * @author
 *
 */
@Data
public class StudentListForm {

	private Integer deptId;
	private Integer studentId;
	private Integer page;
	
	
}
