package edu.du.sb1101.registerMember.controller;

import edu.du.sb1101.fileUploadBoard.board.dto.BoardDto;
import edu.du.sb1101.fileUploadBoard.board.service.BoardService;
import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/sample/")
@Log4j2
public class SampleController {

    @Autowired
    private BoardService boardService;

    private final MemberRepository memberRepository;

    public SampleController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/accessDenied")
    public void accessDenied() {}

    @GetMapping("/all")
    public String exAll(Model model, HttpSession session) throws Exception {
        Member member = (Member) session.getAttribute("member"); // 세션에서 Member 객체 가져오기
        if (member == null) {
            return "redirect:/sample/login";
        }
        model.addAttribute("username", member.getUsername());

        List<BoardDto> recentList = boardService.selectRecentBoardList(5);
        model.addAttribute("recentList", recentList);
        return "sample/all";
    }

    @GetMapping("/admin")
    public String exAdmin(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member"); // 세션에서 Member 객체 가져오기
        if (member == null) {
            return "redirect:/sample/login";
        }
        model.addAttribute("username", member.getUsername());
        return "sample/admin";
    }

    @GetMapping("/member")
    public String exMember(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member"); // 세션에서 Member 객체 가져오기
        if (member == null) {
            return "redirect:/sample/login";
        }
        model.addAttribute("id", member.getId());
        model.addAttribute("email", member.getEmail());
        model.addAttribute("username", member.getUsername());
        model.addAttribute("address", member.getAddress()); // 주소 추가
        model.addAttribute("role", member.getRole());
        model.addAttribute("regdate", member.getRegdate());
        return "sample/member";
    }
    @PostMapping("/infoUpdate")
    public String infoUpdate(@RequestParam String email,
                             @RequestParam String username,
                             @RequestParam String regdate,
                             @RequestParam String address,
                             HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        // 입력받은 정보를 Member 객체에 반영
        member.setEmail(email);
        member.setUsername(username);
        member.setRegdate(LocalDate.parse(regdate).atStartOfDay()); // 가입일 변환
        member.setAddress(address);

        // 데이터베이스에 저장
        memberRepository.save(member);

        return "redirect:/sample/member"; // 업데이트 후 회원 정보 페이지로 리다이렉트
    }


    @GetMapping("/login")
    public String loginForm() {
        return "sample/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, HttpSession session, Model model) {
        Member member = memberRepository.findByEmail(email);

        if (member != null) {
            // Member 객체를 세션에 저장
            session.setAttribute("member", member);
            log.info("Login successful for user: {}", member.getUsername());
            return "redirect:/sample/all";
        } else {
            model.addAttribute("errorMessage", "잘못된 이메일 또는 비밀번호입니다.");
            log.warn("Login failed for email: {}", email);
            return "sample/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/sample/login?logout"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }

}
