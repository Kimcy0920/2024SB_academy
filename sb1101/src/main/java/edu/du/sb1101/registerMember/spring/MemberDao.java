package edu.du.sb1101.registerMember.spring;

import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberDao {

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

	public List<Member> selectAll() {
		return memberRepository.findAll();
	}

}
