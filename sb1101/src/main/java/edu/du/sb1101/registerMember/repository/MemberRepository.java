package edu.du.sb1101.registerMember.repository;

import edu.du.sb1101.registerMember.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmailAndPassword(String email, String password); // 로그인
    Member findByUsername(String username); // 아이디 찾기
    Member findByEmailAndUsername(String email, String username); // 비밀번호 찾기
    Member findByEmail(String email); // 비밀번호 변경
    List<Member> findAllByOrderByIdAsc();

    @Modifying
    @Query("UPDATE Member m SET m.point = m.point + :point WHERE m.username = :username")
    int updatePointByUsername(@Param("point") int point, @Param("username") String username);
}
