package com.example.sample2.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author
 * 경로를 지정해서 던지게 할 예외 클래스
 */
@Getter
public class CustomPathException extends RuntimeException {

	private HttpStatus status;
	private String path;
	
	public CustomPathException(String message, HttpStatus status, String path) {
		super(message);
		this.status = status;
		this.path = path;
	}
	
}
