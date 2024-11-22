package edu.du.sb1101.fileUploadBoard.repository;

import edu.du.sb1101.fileUploadBoard.entity.Board;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    Page<Board> findByTitleContainingOrCreatorIdContaining(String title, String creatorId, Pageable pageable);

    List<Board> findByCreatorId(String creatorId);

    @Query("SELECT count(b) FROM Board b WHERE b.creatorId = :creatorId")
    Integer countByCreatorId(@Param("creatorId") String creatorId);
}
