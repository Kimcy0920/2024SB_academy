package edu.du.sb1010.spring2;

import edu.du.sb1010.config.ManualBean;
import edu.du.sb1010.spring.DuplicateMemberException;
import edu.du.sb1010.spring.Member;
import edu.du.sb1010.spring.MemberDao;
import edu.du.sb1010.spring.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// MemberRegisterService를 복사해옴 AppCtx에서 컴포넌트 스캔 대상에 spring2를 추가
// 코드 실행 시 2개의 서비스가 존재해 에러발생. ManualBean을 사용하면 스캔대상에서 제외됨. 에러X

//@Component
@ManualBean //스캔대상에서 제외됨, 실행 시 에러
@Service // 컴포넌트 스캔 대상에 들어감
public class MemberRegisterService {
	@Autowired
	private MemberDao memberDao;

	public MemberRegisterService() {
	}
	
	public MemberRegisterService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}
		Member newMember = new Member(
				req.getEmail(), req.getPassword(), req.getName(), 
				LocalDateTime.now());
		memberDao.insert(newMember);
		return newMember.getId();
	}
}
