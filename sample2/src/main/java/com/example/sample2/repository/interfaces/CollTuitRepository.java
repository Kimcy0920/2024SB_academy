package com.example.sample2.repository.interfaces;

import com.example.sample2.dto.CollTuitFormDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 *
 *  단과대별 등록금 repository
 */

@Mapper
public interface CollTuitRepository {
	public int insert(CollTuitFormDto collTuitFormDto);
	public List<CollTuitFormDto> selectByCollTuitDto();
	public int deleteById(Integer collegeId);
	public int updateByCollTuitDto(CollTuitFormDto collTuitFormDto);
}
