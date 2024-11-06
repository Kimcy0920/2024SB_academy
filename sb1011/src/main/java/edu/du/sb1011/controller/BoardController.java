package edu.du.sb1011.controller;

import edu.du.sb1011.dto.BoardDto;
import edu.du.sb1011.dto.MemberDto;
import edu.du.sb1011.service.BoardService;
import edu.du.sb1011.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "redirect:/board/homePage.do";
    }

    @GetMapping("/board/homePage.do")
    public String homePage() {
        return "board/homePage";
    }

    @GetMapping("/board/openBoardList.do")
    public String openBoardList(Model model) throws Exception {
        List<BoardDto> list = boardService.selectBoardList();
        model.addAttribute("list", list);
        return "board/boardList";
    }

    @GetMapping("/board/openBoardWrite.do")
    public String openBoardWrite() throws Exception {
        return "board/boardWrite";
    }

    @PostMapping("/board/insertBoard.do")
    public String insertBoard(BoardDto board) throws Exception {
        boardService.insertBoard(board);
        return "redirect:/board/openBoardList.do";
    }

    @GetMapping("/board/openBoardDetail.do")
    public String openBoardDetail(Model model, int boardIdx) throws Exception{

        BoardDto board = boardService.selectBoardDetail(boardIdx);
        model.addAttribute("board", board);
        return "board/boardDetail";
    }

    @PostMapping("/board/updateBoard.do")
    public String updateBoard(BoardDto board) throws Exception {
        boardService.updateBoard(board);
        return "redirect:/board/openBoardList.do";
    }

    @GetMapping("/board/deleteBoard.do")
    public String deleteBoard(int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
        return "redirect:/board/openBoardList.do";
    }

    // --------------------------- 댓글 ------------------------------
//    @PostMapping("/board/insertReply.do")
//    public String insertReply(@RequestParam("parentBoardIdx") int parentBoardIdx, @RequestParam("replyContents") String replyContents) throws Exception {
//        // 답글 저장 로직 추가 (예: service 호출)
//        // ...
//        return "redirect:/board/openBoardDetail.do?boardIdx=" + parentBoardIdx;
//    }

    // ----------------------- Member Controller -----------------------

    @GetMapping("/board/openInsertMember.do")
    public String insertMem() throws Exception {
        return "board/memberRegister";
    } // 회원가입 html
    @PostMapping("/board/insertMember.do")
    public String insertMember(MemberDto member) throws Exception {
        memberService.insertMember(member);
        return "redirect:/board/homePage.do";
    } // 회원가입 처리 DB

    @GetMapping("/board/openLoginMember.do")
    public String openLoginMember() throws Exception {
        return "board/memberLogin";
    } // 로그인 html
    @PostMapping("/board/loginMember.do")
    public String loginMember(MemberDto member) throws Exception {
        MemberDto loginUser = memberService.selectMember(member);
        if (loginUser != null) {
//            session.setAttribute("loginUser", loginUser);
            System.out.println("로그인 성공!");
            return "redirect:/board/homePage.do";
        } else {
            System.out.println("로그인 실패!");
            return "redirect:/board/openLoginMember.do";
        }
    } // 로그인 확인 DB
}
