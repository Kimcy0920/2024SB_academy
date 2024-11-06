package edu.du.sb1024.fileuploadboard.board.service;

import edu.du.sb1024.fileuploadboard.board.dto.BoardDto;
import edu.du.sb1024.fileuploadboard.board.dto.BoardFileDto;
import edu.du.sb1024.fileuploadboard.board.mapper.BoardMapper;
import edu.du.sb1024.fileuploadboard.common.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{
	
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
		// 현재 로그인한 사용자의 username을 가져오기
		String currentUsername = getCurrentUsername();
		// board 객체에 creatorId 설정
		board.setCreatorId(currentUsername);
		boardMapper.insertBoard(board);
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false){
			boardMapper.insertBoardFileList(list);
		}
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception{
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		
		boardMapper.updateHitCount(boardIdx);
		
		return board;
	}
	
	@Override
	public void updateBoard(BoardDto board) throws Exception {
		// 현재 로그인한 사용자의 username을 가져오기
		String currentUsername = getCurrentUsername();
		// board 객체에 creatorId 설정
		board.setCreatorId(currentUsername);
		boardMapper.updateBoard(board);
	}

	@Override
	public void deleteBoard(BoardDto board) throws Exception {
		// 현재 로그인한 사용자의 username을 가져오기
		String currentUsername = getCurrentUsername();
		// board 객체에 creatorId 설정
		board.setCreatorId(currentUsername);
		boardMapper.deleteBoard(board);
	}
	
	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}

	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getName());
		return authentication != null ? authentication.getName() : null;

	}
}	

