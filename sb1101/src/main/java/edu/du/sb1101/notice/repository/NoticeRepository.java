package edu.du.sb1101.notice.repository;

import edu.du.sb1101.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    Page<Notice> findAll(Pageable pageable); // 공지사항 최근등록순
    
    List<Notice> findTop5ByOrderByRegdateDesc(); // 공지사항 최근등록순 5개 메인페이지용
}
