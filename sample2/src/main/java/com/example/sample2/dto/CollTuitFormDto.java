package com.example.sample2.dto;

import com.example.sample2.utils.NumberUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
/**
 * 
 * @author
 *
 */
@Data
public class CollTuitFormDto {
	@NotBlank
	private Integer collegeId;
	private String 	name;
	@NotEmpty
	private Integer amount;

	public String amountFormat() {
		return NumberUtil.numberFormat(amount);
	}
	
}
