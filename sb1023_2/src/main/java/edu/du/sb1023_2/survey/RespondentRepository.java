package edu.du.sb1023_2.survey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RespondentRepository extends JpaRepository<AnsweredData, Long> {
}
