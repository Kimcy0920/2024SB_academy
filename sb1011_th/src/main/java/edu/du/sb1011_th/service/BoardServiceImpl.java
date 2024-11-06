package edu.du.sb1011_th.service;

import edu.du.sb1011_th.entity.Board;
import edu.du.sb1011_th.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BoardServiceImpl implements edu.du.sb1011_th.service.BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public List<Board> selectBoardList() throws Exception {
        return boardRepository.selectBoardList();
    }

    @Override
    public void insertBoard(Board board) throws Exception {
//		boardRepository.insertBoard(board);
    }

    @Override
    public Board selectBoardDetail(int boardIdx) throws Exception{
		Board board = boardRepository.selectBoardDetail(boardIdx);
//		boardRepository.updateHitCount(boardIdx);
		return board;
    }

    @Override
    public void updateBoard(Board board) throws Exception {
//		boardRepository.updateBoard(board);
    }

    @Override
    public void deleteBoard(int boardIdx) throws Exception {
//		boardRepository.deleteBoard(boardIdx);
    }

    @Override
    public void updateHitCount(int boardIdx) throws Exception {
        boardRepository.updateHitCount(boardIdx);
    }
}
