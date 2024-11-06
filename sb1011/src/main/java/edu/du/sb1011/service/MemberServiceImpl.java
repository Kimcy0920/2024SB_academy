package edu.du.sb1011.service;

import edu.du.sb1011.dto.MemberDto;
import edu.du.sb1011.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public MemberDto selectMember(MemberDto member) {
        MemberDto mem = memberMapper.selectMember(member);
        return mem;
    }

    @Override
    public void insertMember(MemberDto member) {
        memberMapper.insertMember(member);
    }
}
