package edu.du.sb1029.board.repository;
import edu.du.sb1029.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query("SELECT new Board(b.boardIdx, b.title, b.contents, b.hitCnt, b.creatorId, b.createdDatetime " +
            ", b.updaterId, b.updatedDatetime, b.deletedYn) FROM Board b WHERE b.deletedYn = 'N' ORDER BY b.boardIdx DESC")
    List<Board> selectBoardList();

}
