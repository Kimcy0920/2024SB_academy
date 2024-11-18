package edu.du.sb1101.fileUploadBoard.board.service;

import edu.du.sb1101.fileUploadBoard.board.dto.BoardDto;
import edu.du.sb1101.fileUploadBoard.board.dto.BoardFileDto;
import edu.du.sb1101.fileUploadBoard.board.mapper.BoardMapper;
import edu.du.sb1101.fileUploadBoard.common.FileUtils;
import edu.du.sb1101.fileUploadBoard.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.List;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardMapper.insertBoard(board);
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false){
			boardMapper.insertBoardFileList(list);
		}
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception{
		log.info("==============>selectBoardDetail {} 들어왔다",boardIdx);
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		
		boardMapper.updateHitCount(boardIdx);
		
		return board;
	}
	
	@Override
	public void updateBoard(BoardDto board) throws Exception {
		boardMapper.updateBoard(board);
	}

	@Override
	public void deleteBoard(BoardDto board) throws Exception {
		boardMapper.deleteBoard(board);
	}
	
	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}

	@Override
	public List<BoardDto> selectRecentBoardList(int limit) throws Exception {
		return boardMapper.selectRecentBoardList(limit);
	}

}	

