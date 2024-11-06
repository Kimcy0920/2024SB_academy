package com.example.sample2.repository.interfaces;

import com.example.sample2.dto.DepartmentFormDto;
import com.example.sample2.repository.model.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 *
 *  학과 repository
 */

@Mapper
public interface DepartmentRepository {
	public int insert(DepartmentFormDto departmentFormDto);
	
	public Department selectById(Integer id);
	public List<Department> selectByDepartmentDto();
	public int deleteById(Integer id);
	public int updateByDepartmentDto(DepartmentFormDto departmentFormDto);
	
}
