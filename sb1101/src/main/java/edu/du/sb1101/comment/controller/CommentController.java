package edu.du.sb1101.comment.controller;

import edu.du.sb1101.comment.entity.Comment;
import edu.du.sb1101.comment.repository.CommentRepository;
import edu.du.sb1101.fileUploadBoard.entity.Board;
import edu.du.sb1101.fileUploadBoard.repository.BoardRepository;
import edu.du.sb1101.registerMember.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @PostMapping("/addComment")
    public String addComment(@RequestParam("content") String content,
                             @RequestParam("boardIdx") Integer boardIdx,
                             Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }

        Board board = boardRepository.findById(boardIdx).get();
        if (board == null) {
            System.out.println("게시글이 존재하지 않습니다.");
            model.addAttribute("error", "게시글을 찾을 수 없습니다.");
            return "redirect:/board/openBoardDetail.do";
        }

        Comment newComment = Comment.builder()
                .content(content)
                .commentDate(LocalDateTime.now())
                .username(member.getUsername())
                .member(member)
                .board(board)
                .build();
        commentRepository.save(newComment);

        List<Comment> commentList = commentRepository.findByBoard(board);
        model.addAttribute("commentList", commentList);

        return "redirect:/board/openBoardDetail.do?boardIdx=" + boardIdx;
    }
}
