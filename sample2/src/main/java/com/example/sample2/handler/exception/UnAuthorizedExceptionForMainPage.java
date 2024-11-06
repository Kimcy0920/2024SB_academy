package com.example.sample2.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnAuthorizedExceptionForMainPage extends RuntimeException {

	private HttpStatus status;
	private String path;

	public UnAuthorizedExceptionForMainPage(HttpStatus status, String path) {
		
		this.status = status;
		this.path = path;
		
	}
	
}
