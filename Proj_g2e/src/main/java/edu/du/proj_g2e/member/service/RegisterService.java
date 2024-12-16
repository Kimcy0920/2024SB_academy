package edu.du.proj_g2e.member.service;

import edu.du.proj_g2e.member.entity.Member;
import edu.du.proj_g2e.member.exception.DuplicateMemberException;
import edu.du.proj_g2e.member.entity.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterService {

	private final PasswordEncoder passwordEncoder;

	private final MemberService memberService;

	public Long register(RegisterRequest req) {
		Member member = memberService.selectByEmail(req.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}

		// 비밀번호 암호화
		String encodePassword = passwordEncoder.encode(req.getPassword());

		// 새로운 멤버 객체 생성 시 암호화된 비밀번호만 저장
		Member newMember = Member.builder()
				.email(req.getEmail())
				.password(encodePassword)  // 암호화된 비밀번호만 저장
				.name(req.getName())
				.role("USER")
				.build();

		memberService.insert(newMember);
		System.out.println("====>" + newMember);
		return newMember.getId();
	}
}
