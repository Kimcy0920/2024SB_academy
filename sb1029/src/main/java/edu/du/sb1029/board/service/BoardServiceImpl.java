package edu.du.sb1029.board.service;

import edu.du.sb1029.board.entity.Board;
import edu.du.sb1029.board.repository.BoardRepository;
import groovy.transform.Undefined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardRepository boardRepository;

	@Override
	public List<Board> selectBoardList() throws Exception {
		return boardRepository.selectBoardList();
	}

	@Override
	public void insertBoard(Board board) throws Exception {
		board.setDeletedYn("N");
		boardRepository.save(board);
	}

	@Override
	public Board selectBoardDetail(int boardIdx) throws Exception{
		Board board = boardRepository.findById(boardIdx).orElseThrow(() -> new Undefined.EXCEPTION());
		// 조회수 증가 로직 유지
		board.setHitCnt(board.getHitCnt() + 1);
		boardRepository.save(board);
		return board;
	}

	@Override
	public void updateBoard(Board board) throws Exception {
		board.setDeletedYn("N");
		boardRepository.save(board);
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		Board board = boardRepository.findById(boardIdx).orElseThrow(() -> new Undefined.EXCEPTION());
		// 실제 삭제 대신 deletedYn을 'Y'로 업데이트
		board.setDeletedYn("Y");
		boardRepository.save(board);
	}

}	

