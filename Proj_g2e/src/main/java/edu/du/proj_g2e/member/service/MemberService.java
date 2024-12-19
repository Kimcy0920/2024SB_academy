package edu.du.proj_g2e.member.service;

import edu.du.proj_g2e.member.entity.Member;
import edu.du.proj_g2e.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member selectByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public void insert(Member member) {
        memberRepository.save(member);
    }

    public void update(Member member) {
        memberRepository.save(member);
    }

}