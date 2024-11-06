package com.example.sample2.repository.interfaces;

import com.example.sample2.dto.BreakAppFormDto;
import com.example.sample2.repository.model.BreakApp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 *
 */
@Mapper
public interface BreakAppRepository {

	// 휴학 신청하기
	public int insert(BreakAppFormDto breakAppFormDto);
	
	// 학생의 휴학 신청 조회하기
	public List<BreakApp> selectByStudentId(Integer studentId);
	
	// 처리되지 않은 휴학 신청 조회하기 (교직원용)
	public List<BreakApp> selectByStatus(String status);
	
	// 특정 휴학 신청서 조회하기
	public BreakApp selectById(Integer id);
	
	// 휴학 신청 취소하기 (학생용)
	public int deleteById(Integer id);
	
	// 휴학 신청 처리하기 (교직원용)
	public int updateById(@Param("id") Integer id, @Param("status") String status);
	
}
