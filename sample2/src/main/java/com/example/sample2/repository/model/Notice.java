package com.example.sample2.repository.model;

import lombok.Data;

import java.security.Timestamp;

@Data
public class Notice {

	private Integer id;
	private String category;
	private String title;
	private String content;
	private Integer views;
	private Timestamp createdTime;
	
	private String uuidFilename;
	private String originFilename;
	
	public String setUpImage() {
		return "/images/uploads/" + uuidFilename;
	} 
}
