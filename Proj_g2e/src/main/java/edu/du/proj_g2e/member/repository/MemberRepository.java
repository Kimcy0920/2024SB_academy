package edu.du.proj_g2e.member.repository;

import edu.du.proj_g2e.member.entity_dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email); // 비밀번호 변경

}