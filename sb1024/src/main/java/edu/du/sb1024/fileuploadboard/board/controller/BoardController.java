package edu.du.sb1024.fileuploadboard.board.controller;

import edu.du.sb1024.fileuploadboard.board.dto.BoardDto;
import edu.du.sb1024.fileuploadboard.board.dto.BoardFileDto;
import edu.du.sb1024.fileuploadboard.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@RequestMapping("/board/openBoardList.do") // 페이징 코드 추가, 10-29
	public String openBoardList(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) throws Exception {
		log.info("====> openBoardList {}", "테스트");

		List<BoardDto> list = boardService.selectBoardList();

		// 현재 페이지의 시작 인덱스를 계산
		// 예를 들어, 1페이지라면 start는 0, 2페이지라면 start는 10이 됨
		final int start = (int) pageable.getOffset();

		// 현재 페이지의 끝 인덱스를 계산
		// 목록의 끝을 넘어가지 않도록 list.size()와 비교하여 제한
		final int end = Math.min(start + pageable.getPageSize(), list.size());

		// 주어진 범위(start ~ end)에 해당하는 서브리스트로 새로운 Page 객체 생성
		// pageable 객체를 통해 현재 페이지 정보와 전체 목록 크기를 제공
		final Page<BoardDto> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

		log.info("start: {}. end: {}", start, end);
		log.info("총 페이지 수: {}", page.getTotalPages());
		log.info("전체 개수: {}", page.getTotalElements());
		log.info("현재 페이지 번호: {}", page.getNumber());
		log.info("페이지 당 데이터 개수: {}", page.getSize());
		log.info("다음 페이지 존재 여부: {}", page.hasNext());
		log.info("이전 페이지 존재 여부: {}", page.hasPrevious());
		log.info("시작 페이지 확인: {}", page.isFirst());

		model.addAttribute("list", page);
		return "/board/boardList";
	}

	
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception{
		return "/board/boardWrite";
	}
	
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		boardService.insertBoard(board, multipartHttpServletRequest);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		
		return mv;
	}
	
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception{
		boardService.updateBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(BoardDto board) throws Exception{
		boardService.deleteBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/downloadBoardFile.do")
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
		String currentPath = Paths.get("").toAbsolutePath().toString();
		System.out.println("---------------------"+currentPath);
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File("./src/main/resources/static"+boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}
