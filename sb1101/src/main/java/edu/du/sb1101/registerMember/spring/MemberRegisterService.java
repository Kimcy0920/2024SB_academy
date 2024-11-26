package edu.du.sb1101.registerMember.spring;

import edu.du.sb1101.registerMember.entity.Member;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberRegisterService {

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}

		// 비밀번호 암호화
		String encodePassword = passwordEncoder.encode(req.getPassword());

		// 새로운 멤버 객체 생성 시 암호화된 비밀번호만 저장
		Member newMember = Member.builder()
				.email(req.getEmail())
				.password(encodePassword)  // 암호화된 비밀번호만 저장
				.regdate(LocalDateTime.now())
				.username(req.getName())
				.address(req.getAddress())
				.role("USER")
				.point(0)
				.build();

		memberDao.insert(newMember);
		System.out.println("====>" + newMember);
		return newMember.getId();
	}
}
