package edu.du.sb1101.registerMember.spring;

import edu.du.sb1101.registerMember.entity.Member;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberRegisterService {

	@Autowired
	private MemberDao memberDao;

	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}

		Member newMember = Member.builder()
				.email(req.getEmail())
				.password(req.getPassword())
				.regdate(LocalDateTime.now())
				.password(req.getPassword())
				.username(req.getName())
				.role("USER")
				.build();
		memberDao.insert(newMember);
		System.out.println("====>" + newMember);
		return newMember.getId();
	}

//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
}
