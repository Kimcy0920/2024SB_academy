package edu.du.sb1101.fileUploadBoard.board.controller;

import edu.du.sb1101.comment.entity.Comment;
import edu.du.sb1101.comment.repository.CommentRepository;
import edu.du.sb1101.fileUploadBoard.board.dto.BoardDto;
import edu.du.sb1101.fileUploadBoard.board.dto.BoardFileDto;
import edu.du.sb1101.fileUploadBoard.board.service.BoardService;
import edu.du.sb1101.fileUploadBoard.entity.Board;
import edu.du.sb1101.fileUploadBoard.repository.BoardRepository;
import edu.du.sb1101.registerMember.entity.Member;
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
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Slf4j
public class BoardController {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	BoardRepository boardRepository;

	@Autowired
	private BoardService boardService;

	@RequestMapping("/board/openBoardList.do")
	public String openBoardList(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable,
								HttpSession session) throws Exception {
		log.info("====> openBoardList {}", "테스트");
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return "redirect:/sample/login";
		}
		model.addAttribute("username", member.getUsername());

		// 전체 게시글 목록
		List<BoardDto> list = boardService.selectBoardList();

		// 페이지 정보에 따라 현재 페이지의 시작 인덱스를 계산
		final int start = (int) pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), list.size());
		final Page<BoardDto> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
		model.addAttribute("list", page);

//		// 최근 5개 게시글
//		List<BoardDto> recentList = boardService.selectRecentBoardList(6);
//		log.info("====> recentList size: {}", recentList.size());
//		log.info("====> recentList contents: {}", recentList);
//		for (BoardDto boardDto : recentList) {
//			log.info("BoardIdx: {}, Title: {}, Contents: {}, CreatorId: {}", boardDto.getBoardIdx(), boardDto.getTitle(), boardDto.getContents(), boardDto.getCreatorId());
//		}
//
//		model.addAttribute("recentList", recentList);

		return "board/boardList";
	}
	
	@RequestMapping("board/openBoardWrite.do")
	public String openBoardWrite(HttpSession session, Model model) throws Exception{
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return "redirect:/sample/login";
		}
		model.addAttribute("username", member.getUsername());
		return "board/boardWrite";
	}

	@RequestMapping("board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest, HttpSession session, Model model) throws Exception{
		Member member = (Member) session.getAttribute("member");
		board.setCreatorId(member.getUsername()); // 먼저 creatorId를 설정합니다.
		model.addAttribute("username", member.getUsername());

		// 게시글을 데이터베이스에 저장합니다.
		boardService.insertBoard(board, multipartHttpServletRequest);

		return "redirect:/board/openBoardList.do";
	}

	@RequestMapping("board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx,
										HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("board/boardDetail");

		Member member = (Member) session.getAttribute("member");
		if (member != null) {
			mv.addObject("username", member.getUsername());
		}

		// 게시글 조회
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		if (board == null) {
			mv.setViewName("redirect:/board/openBoardList.do");
			mv.addObject("error", "게시글을 찾을 수 없습니다.");
			return mv;
		}
		mv.addObject("board", board);

		// 댓글 목록 조회
		Board boardEntity = boardRepository.findById(boardIdx).orElse(null);
		List<Comment> commentList = commentRepository.findByBoard(boardEntity);
		mv.addObject("commentList", commentList);

		return mv;
	}
	
	@RequestMapping("board/updateBoard.do")
	public String updateBoard(BoardDto board, HttpSession session, Model model) throws Exception{
		Member member = (Member) session.getAttribute("member");
		board.setUpdaterId(member.getUsername());
		boardService.updateBoard(board);
		return "redirect:/board/openBoardList.do";
	}

	@RequestMapping("board/deleteBoard.do")
	public String deleteBoard(BoardDto board, HttpSession session) throws Exception {
		Member member = (Member) session.getAttribute("member");
		board.setUpdaterId(member.getUsername());  // updaterId 설정
		boardService.deleteBoard(board);  // BoardDto를 전달
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("board/downloadBoardFile.do")
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
