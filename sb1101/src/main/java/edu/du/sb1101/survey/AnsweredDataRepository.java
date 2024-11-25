package edu.du.sb1101.survey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnsweredDataRepository extends JpaRepository<AnsweredData, Integer> {

    AnsweredData findByMemberId(Long memberId);
}
