package edu.du.kcy1101.board.service;

import edu.du.kcy1101.board.entity.Board;

import java.util.List;

public interface BoardService {

	List<Board> selectBoardList() throws Exception;

	void insertBoard(Board board) throws Exception;

	Board selectBoardDetail(int boardIdx) throws Exception;

	void updateBoard(Board board) throws Exception;

	void deleteBoard(int boardIdx) throws Exception;

}
