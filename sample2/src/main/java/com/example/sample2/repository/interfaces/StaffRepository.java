package com.example.sample2.repository.interfaces;

import com.example.sample2.dto.CreateStaffDto;
import com.example.sample2.dto.FindIdFormDto;
import com.example.sample2.dto.FindPasswordFormDto;
import com.example.sample2.dto.UserUpdateDto;
import com.example.sample2.dto.response.UserInfoForUpdateDto;
import com.example.sample2.repository.model.Staff;
import org.apache.ibatis.annotations.Mapper;

/**
 * Staff DAO
 * 
 * @author
 */
@Mapper
public interface StaffRepository {

	// staff_tb에 insert
	public int insertToStaff(CreateStaffDto createStaffDto);

	// staff_tb에서 자동 생성된 id 받아오기
	public Integer selectIdByCreateStaffDto(CreateStaffDto createStaffDto);

	// 업데이트용 정보 읽기
	public UserInfoForUpdateDto selectByUserId(Integer userId);

	// 유저정보 업데이트
	public int updateStaff(UserUpdateDto userUpdateDto);
	
	// id로 staff 모델 불러오기
	public Staff selectStaffById(Integer Id);
	
	// id 찾기
	public Integer selectIdByNameAndEmail(FindIdFormDto findIdFormDto);
	
	// password 발급용 model 확인
	public Integer selectStaffByIdAndNameAndEmail(FindPasswordFormDto findPasswordFormDto);

}
