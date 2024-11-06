package com.example.sample2.repository.interfaces;

import com.example.sample2.dto.ChangePasswordDto;
import com.example.sample2.dto.response.PrincipalDto;
import com.example.sample2.repository.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
	
	// 로그인용
	public PrincipalDto selectById(Integer userId);
	
	// 패스워드 변경
	public int updatePassword(ChangePasswordDto changePasswordDto);
	
	// id 이용해서 user_tb에 insert
	public int insertToUser(User user);

}
