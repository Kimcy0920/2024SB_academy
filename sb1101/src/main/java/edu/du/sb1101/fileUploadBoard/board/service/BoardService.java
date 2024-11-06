package edu.du.sb1101.fileUploadBoard.board.service;

import edu.du.sb1101.fileUploadBoard.board.dto.BoardDto;
import edu.du.sb1101.fileUploadBoard.board.dto.BoardFileDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface BoardService {

	List<BoardDto> selectBoardList() throws Exception;

	void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

	BoardDto selectBoardDetail(int boardIdx) throws Exception;

	void updateBoard(BoardDto board) throws Exception;

	void deleteBoard(BoardDto board) throws Exception;

	BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;

	List<BoardDto> selectRecentBoardList(int limit) throws Exception;

}
