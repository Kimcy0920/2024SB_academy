package edu.du.sb1024.fileuploadboard.board.repository;

import edu.du.sb1024.fileuploadboard.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    public List<Board> findAllByOrderByBoardIdxDesc(Pageable pageable);
}
