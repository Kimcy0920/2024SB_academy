package edu.du.kcy1101.board.controller;

import edu.du.kcy1101.board.entity.Board;
import edu.du.kcy1101.board.service.BoardService;
import edu.du.kcy1101.board.validator.BoardValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String index() {
        return "redirect:/board/openBoardList.do";
    }

    @RequestMapping("/board/openBoardList.do")
    public String openBoardList(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) throws Exception {
        log.info("====> openBoardList {}", "테스트");

        List<Board> list = boardService.selectBoardList();
        // 페이지 정보에 따라 현재 페이지의 시작 인덱스를 계산
        final int start = (int) pageable.getOffset();
        // 현재 페이지의 끝 인덱스를 계산하되, 목록 크기를 초과하지 않도록 함
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        // 현재 페이지의 아이템 서브리스트를 포함하는 Page 객체 생성
        log.info("start: {}, end: {}", start, end);
        final Page<Board> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
        // 페이지 객체를 모델에 추가하여 뷰에서 접근 가능하도록 함
        log.info("총페이지 수: {}", page.getTotalPages());
        log.info("전체 개수: {}", page.getTotalElements());
        log.info("현재 페이지 번호: {}", page.getNumber());
        log.info("페이지당 데이터 개수: {}", page.getSize());
        log.info("다음 페이지 존재 여부: {}", page.hasNext());
        log.info("이전 페이지 존재 여부: {}", page.hasPrevious());
        log.info("시작페이지(0) 입니까: {}", page.isFirst());
        model.addAttribute("list", page);

        return "/board/boardList";
    }

    @RequestMapping("/board/openBoardWrite.do")
    public String openBoardWrite() throws Exception {
        return "/board/boardWrite";
    }

    @RequestMapping("/board/insertBoard.do")
    public String insertBoard(@ModelAttribute("board") Board board) throws Exception {
        boardService.insertBoard(board);
        return "redirect:/board/openBoardList.do";
    }

    @GetMapping("/board/openBoardDetail.do")
    public String openBoardDetail(@RequestParam int boardIdx, Model model) throws Exception {
        Board board = boardService.selectBoardDetail(boardIdx);
        model.addAttribute("board", board);
        return "/board/boardDetail";
    }

    @RequestMapping("/board/updateBoard.do")
    public String updateBoard(Board board) throws Exception {
        boardService.updateBoard(board);
        return "redirect:/board/openBoardList.do";
    }

    @RequestMapping("/board/deleteBoard.do")
    public String deleteBoard(int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
        return "redirect:/board/openBoardList.do";
    }

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new BoardValidator());
	}
}
