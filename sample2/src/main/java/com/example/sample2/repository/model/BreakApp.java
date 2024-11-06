package com.example.sample2.repository.model;

import com.example.sample2.utils.DateUtil;
import lombok.Data;

import java.sql.Date;

/**
 * @author
 * 휴학 신청 내역
 */
@Data
public class BreakApp {

	private Integer id;
	private Integer studentId;
	private Integer studentGrade;
	private Integer fromYear;
	private Integer fromSemester;
	private Integer toYear;
	private Integer toSemester;
	private String type;
	private Date appDate;
	private String status;
	
	public String appDateFormat() {
		return DateUtil.dateFormat(appDate);
	}
	
}
