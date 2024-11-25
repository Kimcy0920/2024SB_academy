package edu.du.sb1101.point;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointLogRepository extends JpaRepository<PointLog, Integer> {

    List<PointLog> findByMemberIdOrderByCreatedAtDesc(Long memberId);
}
