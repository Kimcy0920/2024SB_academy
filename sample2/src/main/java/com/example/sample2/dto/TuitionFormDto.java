package com.example.sample2.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 
 * @author 박성희
 *
 */
@Data
public class TuitionFormDto {
	@NotEmpty
	@Size(min = 10000000)
	@Size(max = 99999999)
	private String studentId;
	@NotEmpty
	@Size(max = 6)
	private String semester;
	private boolean status;
}
