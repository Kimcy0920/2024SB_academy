package edu.du.sb1101.comment.controller;

import edu.du.sb1101.comment.entity.Comment;
import edu.du.sb1101.comment.repository.CommentRepository;
import edu.du.sb1101.fileUploadBoard.entity.Board;
import edu.du.sb1101.fileUploadBoard.repository.BoardRepository;
import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.repository.MemberRepository;
import edu.du.sb1101.registerMember.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/addComment")
    public String addComment(@RequestParam("content") String content,
                             @RequestParam("boardIdx") Integer boardIdx,
                             Model model, HttpSession session) throws UnsupportedEncodingException {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }

        Board board = boardRepository.findById(boardIdx).get();
        if (board == null) {
            model.addAttribute("error", "게시글을 찾을 수 없습니다.");
            return "redirect:/board/openBoardDetail.do";
        }

        // 공백 검증
        if (content == null || content.trim().isEmpty()) {
            model.addAttribute("errorMessage", "댓글 내용을 입력해주세요.");
            model.addAttribute("board", board);
            // 댓글 목록을 CommentRepository에서 가져오는 방식 변경
            List<Comment> commentList = commentRepository.findByBoard(board);
            model.addAttribute("commentList", commentList);
            return "redirect:/board/openBoardDetail.do?boardIdx=" + boardIdx + "&errorMessage=" + URLEncoder.encode("댓글 내용을 입력해주세요.", "UTF-8");

        }

        // 줄바꿈을 <br>로 변경
        String formattedContent = content.replace("\n", "<br>");

        Comment newComment = Comment.builder()
                .content(formattedContent)
                .commentDate(LocalDateTime.now())
                .username(member.getUsername())
                .member(member)
                .board(board)
                .build();

        commentRepository.save(newComment);

        // 포인트 적립
        memberService.addPoints(member.getUsername(), 5, "댓글 작성 +5포인트 적립");

        // 업데이트된 Member 객체 가져오기
        Member updatedMember = memberRepository.findByUsername(member.getUsername());
        // 세션에 갱신된 Member 저장
        session.setAttribute("member", updatedMember);

        return "redirect:/board/openBoardDetail.do?boardIdx=" + boardIdx;
    }
}
