package edu.du.sb1011.service;

import edu.du.sb1011.dto.MemberDto;

public interface MemberService {

	MemberDto selectMember(MemberDto member);

	void insertMember(MemberDto member);
}
