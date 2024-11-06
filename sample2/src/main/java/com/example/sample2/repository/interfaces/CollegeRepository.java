package com.example.sample2.repository.interfaces;

import com.example.sample2.dto.CollegeFormDto;
import com.example.sample2.repository.model.College;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 *
 *  단과대 repository
 */

@Mapper
public interface CollegeRepository {
	public int insert(CollegeFormDto CollegeFormDto);

	public List<College> selectCollegeDto();

	public int selectCollegeDtoByName(String name);
	public College selectCollegeDtoById(Integer id);
	public int deleteById(Integer id);
}
