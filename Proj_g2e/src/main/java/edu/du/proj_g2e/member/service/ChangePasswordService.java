package edu.du.proj_g2e.member.service;

import edu.du.proj_g2e.member.entity.Member;
import edu.du.proj_g2e.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

	private final MemberService memberService;

	@Transactional
	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberService.selectByEmail(email);
		if (member == null)
			throw new MemberNotFoundException();

		member.changePassword(oldPwd, newPwd);

		memberService.update(member);
	}

}
