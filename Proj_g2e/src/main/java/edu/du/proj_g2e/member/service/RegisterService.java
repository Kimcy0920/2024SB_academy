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

		System.out.println("agreeTerms: " + req.isAgreeTerms());
		System.out.println("agreePrivacy: " + req.isAgreePrivacy());

		// 비밀번호 암호화
		String encodePassword = passwordEncoder.encode(req.getPassword());

		// 새로운 멤버 객체 생성 시 암호화된 비밀번호만 저장
		Member newMember = Member.builder()
				.email(req.getEmail())
				.password(encodePassword)  // 암호화된 비밀번호만 저장
				.name(req.getName())
				.role(req.getRole())  // 전달된 role 값으로 설정
				.agreeTerms(req.isAgreeTerms())
				.agreePrivacy(req.isAgreePrivacy())
				.build();

		memberService.insert(newMember);
		return newMember.getId();
	}
}