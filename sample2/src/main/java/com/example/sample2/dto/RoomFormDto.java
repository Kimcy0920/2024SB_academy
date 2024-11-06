package com.example.sample2.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * 
 * @author
 *
 */
@Data
public class RoomFormDto {
	@NotNull
	@Size(min = 4, max = 4)
	private String id;
	@NotNull
	private String collegeId;
}
