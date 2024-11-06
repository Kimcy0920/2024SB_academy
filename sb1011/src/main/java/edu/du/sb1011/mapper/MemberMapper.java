package edu.du.sb1011.mapper;

import edu.du.sb1011.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insertMember(MemberDto member); // 회원가입

    MemberDto selectMember(MemberDto member);
}
