package edu.du.sb1101.registerMember.repository;

import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
    List<Title> findByMemberIdOrderById(Long memberId);
    List<Title> findByMemberIdAndActiveTrue(Long memberId);

    @Query("SELECT t FROM Title t WHERE t.member.id = :memberId AND t.active = true")
    Title findActiveTitleByMemberId(@Param("memberId") Long memberId);

    boolean existsByMemberAndName(Member member, String titleName);
}
