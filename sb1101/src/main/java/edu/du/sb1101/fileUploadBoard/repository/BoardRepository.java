package edu.du.sb1101.fileUploadBoard.repository;

import edu.du.sb1101.fileUploadBoard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    public List<Board> findAllByOrderByBoardIdxDesc();
}
