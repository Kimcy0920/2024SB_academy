package edu.du.sb1101.fileUploadBoard.board.controller;

import edu.du.sb1101.comment.entity.Comment;
import edu.du.sb1101.comment.repository.CommentRepository;
import edu.du.sb1101.fileUploadBoard.board.dto.BoardDto;
import edu.du.sb1101.fileUploadBoard.board.dto.BoardFileDto;
import edu.du.sb1101.fileUploadBoard.board.service.BoardService;
import edu.du.sb1101.fileUploadBoard.entity.Board;
import edu.du.sb1101.fileUploadBoard.repository.BoardRepository;
import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.entity.Title;
import edu.du.sb1101.registerMember.repository.MemberRepository;
import edu.du.sb1101.registerMember.repository.TitleRepository;
import edu.du.sb1101.registerMember.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class BoardController {

	@Autowired
	TitleRepository titleRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberService memberService;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	private BoardService boardService;

	@RequestMapping("/board/openBoardList.do")
	public String openBoardList(@RequestParam(value = "keyword", required = false) String keyword,
								Model model,
								@PageableDefault(page = 0, size = 10) Pageable pageable,
								HttpSession session) throws Exception {
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return "redirect:/sample/login";
		}
		model.addAttribute("username", member.getUsername());
		model.addAttribute("role", member.getRole());

		// Member 객체에 연결된 Title 가져오기 (가장 활성화된 칭호)
		Title title = titleRepository.findActiveTitleByMemberId(member.getId());
		// Model에 title과 username 전달
		model.addAttribute("title", title);

		// 키워드 여부에 따라 데이터 조회
		Page<Board> pageResult;
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("boardIdx"))); // boardIdx 기준 내림차순 정렬

		if (keyword != null && !keyword.trim().isEmpty()) {
			pageResult = boardRepository.findByTitleContainingOrCreatorIdContaining(keyword, keyword, sortedPageable); // 수정된 부분
		} else {
			pageResult = boardRepository.findAll(sortedPageable); // 수정된 부분
		}

		// 게시글 목록에 칭호 정보 추가
		for (Board board : pageResult) {
			// creatorId가 현재 로그인된 사용자와 일치하면 칭호를 설정
			if (board.getCreatorId().equals(member.getUsername()) && title != null) {
				board.setCreatorId("[" + title.getName() + "] " + board.getCreatorId());
			}
		}

		// 검색어, 페이징 결과를 모델에 추가
		model.addAttribute("list", pageResult);
		model.addAttribute("keyword", keyword);

		return "board/boardList";
	}
	
	@RequestMapping("board/openBoardWrite.do")
	public String openBoardWrite(HttpSession session, Model model) throws Exception{
		Member member = (Member) session.getAttribute("member");
		model.addAttribute("role", member.getRole());
		if (member == null) {
			return "redirect:/sample/login";
		}
		model.addAttribute("username", member.getUsername());

		// Member 객체에 연결된 Title 가져오기 (가장 활성화된 칭호)
		Title title = titleRepository.findActiveTitleByMemberId(member.getId());
		model.addAttribute("title", title);
		if (title != null) {
			model.addAttribute("titleName", title.getName());
		} else {
			model.addAttribute("titleName", ""); // 칭호가 없을 경우 기본값
		}

		return "board/boardWrite";
	}

	@RequestMapping("board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest, HttpSession session, Model model) throws Exception{
		Member member = (Member) session.getAttribute("member");
		board.setCreatorId(member.getUsername()); // 먼저 creatorId를 설정합니다.
		model.addAttribute("username", member.getUsername());

		// 공백 검증
		if (board.getContents().trim().isEmpty() || board.getTitle().trim().isEmpty()) {
			model.addAttribute("errorMessage", "제목과 내용을 모두 입력해주세요.");
			model.addAttribute("board", board); // 작성한 게시글 데이터 다시 넘기기
			return "redirect:/board/openBoardWrite.do?errorMessage=" +
					URLEncoder.encode("제목과 내용을 입력해주세요.", "UTF-8");
		}

		// 게시글을 데이터베이스에 저장합니다.
		boardService.insertBoard(board, multipartHttpServletRequest);

		// 포인트 적립
		memberService.addPoints(member.getUsername(), 15, "게시글 작성 +15포인트 적립");
		log.info("15포인트가 적립되었습니다.");
		// 업데이트된 Member 객체 가져오기
		Member updatedMember = memberRepository.findByUsername(member.getUsername());
		// 세션에 갱신된 Member 저장
		session.setAttribute("member", updatedMember);

		return "redirect:/board/openBoardList.do";
	}

	@RequestMapping("board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx,
										HttpSession session, Model model) throws Exception {
		ModelAndView mv = new ModelAndView("board/boardDetail");

		Member member = (Member) session.getAttribute("member");
		if (member != null) {
			mv.addObject("username", member.getUsername());
			mv.addObject("role", member.getRole());

			// Member 객체에 연결된 Title 가져오기 (가장 활성화된 칭호)
			Title title = titleRepository.findActiveTitleByMemberId(member.getId());
			model.addAttribute("title", title);
			if (title != null) {
				model.addAttribute("titleName", title.getName());
			} else {
				model.addAttribute("titleName", ""); // 칭호가 없을 경우 기본값
			}
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
		// 댓글 작성자 칭호 매핑
		Map<String, String> userTitles = new HashMap<>();
		for (Comment comment : commentList) {
			Member mem = memberRepository.findByUsername(comment.getUsername());
			if (mem != null) {
				Title title = titleRepository.findActiveTitleByMemberId(mem.getId());
				userTitles.put(comment.getUsername(), title != null ? title.getName() : "");
			}
		}
		mv.addObject("commentList", commentList);
		model.addAttribute("userTitles", userTitles); // 사용자 칭호 매핑 전달

		return mv;
	}
	
	@RequestMapping("board/updateBoard.do")
	public String updateBoard(@RequestParam("boardIdx") Integer boardIdx, BoardDto board, HttpSession session, Model model) throws Exception{
		Member member = (Member) session.getAttribute("member");
		board.setUpdaterId(member.getUsername());

		// 공백 검증
		if (board.getContents().trim().isEmpty() || board.getTitle().trim().isEmpty()) {
			model.addAttribute("errorMessage", "제목과 내용을 모두 입력해주세요.");
			model.addAttribute("board", board); // 작성한 게시글 데이터 다시 넘기기
			return "redirect:/board/openBoardDetail.do?boardIdx=" + boardIdx +
					"&errorMessage=" + URLEncoder.encode("제목과 내용을 입력해주세요.", "UTF-8");
		}

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
