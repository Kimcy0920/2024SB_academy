package edu.du.sb1101.notice.repository;

import edu.du.sb1101.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    List<Notice> findTop5ByOrderByRegdateDesc();
}
