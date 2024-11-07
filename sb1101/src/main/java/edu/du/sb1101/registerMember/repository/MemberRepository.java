package edu.du.sb1101.registerMember.repository;

import edu.du.sb1101.registerMember.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmailAndPassword(String email, String password); // 로그인
    Member findByUsername(String username); // 아이디 찾기
    Member findByEmailAndUsername(String email, String username); // 비밀번호 찾기
    Member findByEmail(String email); // 비밀번호 변경
}
