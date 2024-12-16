package edu.du.proj_g2e.member.repository;

import edu.du.proj_g2e.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email); // 비밀번호 변경


    Member findByEmailAndPassword(String email, String password); // 로그인
    Member findByName(String name); // 아이디 찾기
    Member findByEmailAndName(String email, String name); // 비밀번호 찾기
    List<Member> findAllByOrderByIdAsc();

}