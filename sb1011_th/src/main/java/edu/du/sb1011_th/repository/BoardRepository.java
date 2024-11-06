package edu.du.sb1011_th.repository;

import edu.du.sb1011_th.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// 기존 sb1010에서 BoardMapper -> Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT new Board(b.boardIdx, b.title, b.contents, b.hitCnt, b.creatorId, b.createdDatetime " +
            ", b.updaterId, b.updatedDatetime, b.deletedYn) FROM Board b WHERE b.deletedYn = 'N' ORDER BY b.boardIdx DESC")
    List<Board> selectBoardList();

//    void insertBoard(Board board);

    @Query("SELECT new Board(b.boardIdx, b.title, b.contents, b.hitCnt, b.creatorId, b.createdDatetime, " +
            "b.updaterId, b.updatedDatetime, b.deletedYn) FROM Board b WHERE b.boardIdx = :boardIdx AND b.deletedYn = 'N'")
    Board selectBoardDetail(int boardIdx);

    @Modifying
    @Query("UPDATE Board b SET b.hitCnt = b.hitCnt + 1 WHERE b.boardIdx = :boardIdx")
    void updateHitCount(int boardIdx);

//    void updateBoard(Board board);

//    void deleteBoard(int boardIdx);
}
