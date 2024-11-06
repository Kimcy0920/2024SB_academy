package edu.du.sb1010.spring;

import java.time.LocalDateTime;

import edu.du.sb1010.config.ManualBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
//@ManualBean 스캔대상에서 제외됨, 실행 시 에러
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
