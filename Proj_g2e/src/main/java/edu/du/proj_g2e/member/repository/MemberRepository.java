package edu.du.proj_g2e.member.repository;

import edu.du.proj_g2e.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email); // 비밀번호 변경

}