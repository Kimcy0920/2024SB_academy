package edu.du.sb1101.notice.controller;

import edu.du.sb1101.notice.entity.Notice;
import edu.du.sb1101.notice.repository.NoticeRepository;
import edu.du.sb1101.registerMember.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/notice")
@RequiredArgsConstructor
@Controller
public class NoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/noticeList")
    public String notice(@RequestParam(value = "keyword", required = false) String keyword, // 검색 키워드
                         Model model,
                         @PageableDefault(page = 0, size = 10, sort = "regdate", direction = Sort.Direction.DESC) Pageable pageable,
                         HttpSession session) throws Exception {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }
        model.addAttribute("username", member.getUsername());
        model.addAttribute("role", member.getRole());

        // 키워드 여부에 따라 데이터 조회
        Page<Notice> list;
        if (keyword != null && !keyword.trim().isEmpty()) {
            list = noticeRepository.findByTitleContainingOrUsernameContaining(keyword, keyword, pageable);
        } else {
            list = noticeRepository.findAll(pageable);
        }

        model.addAttribute("noticeList", list);
        model.addAttribute("keyword", keyword);

        return "/notice/noticeList";
    }

    @GetMapping("/noticeWrite")
    public String noticeWriteForm(HttpSession session, Model model) throws Exception{
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }
        model.addAttribute("username", member.getUsername());
        model.addAttribute("role", member.getRole());
        if (member.getRole().equals("ADMIN")) {
            return "/notice/noticeWrite";
        } else {
            System.out.println("관리자 전용입니다.");
            return "redirect:/notice/noticeList";
        }
    }
    @PostMapping("/noticeWrite")
    public String noticeWrite(@Valid @RequestParam String title,
                              @Valid @RequestParam String content,
                              Model model,
                              HttpSession session) throws Exception {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }
        model.addAttribute("username", member.getUsername());
        model.addAttribute("role", member.getRole());

        Notice notice = Notice.builder()
                .title(title).content(content).username(member.getUsername()).regdate(LocalDateTime.now()).build();
        noticeRepository.save(notice);

        return "redirect:/notice/noticeList";
    }


    @PostMapping("/noticeUpdate")
    public String noticeUpdate(@RequestParam("id") Integer id, @RequestParam String title, @RequestParam String content, HttpSession session) throws Exception {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }

        Notice notice = noticeRepository.findById(id).orElse(null);
        if (notice != null && member.getRole().equals("ADMIN")) {
            // 업데이트할 필드 설정
            notice.setTitle(title);
            notice.setContent(content);
            noticeRepository.save(notice);
            return "redirect:/notice/noticeList"; // 수정 후 리스트로 리다이렉트
        } else {
            return "redirect:/notice/noticeDetail?id=" + id; // 수정 실패 시 상세페이지로 리다이렉트
        }
    }
    @PostMapping("/noticeDelete")
    public String noticeDelete(@RequestParam("id") Integer id) throws Exception {
        Notice notice = noticeRepository.findById(id).orElse(null);
        if (notice != null) {
            noticeRepository.deleteById(id);
            return "redirect:/notice/noticeList";
        }
        return "/notice/noticeList";
    }

    @GetMapping("/noticeDetail")
    public String noticeDetail(@RequestParam("id") Integer id, Model model, HttpSession session) throws Exception {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }
        model.addAttribute("username", member.getUsername());

        Notice notice = noticeRepository.findById(id).orElse(null);
        if (notice == null) {
            return "redirect:/notice/noticeList"; // 데이터가 없으면 리스트로 리다이렉트
        }
        model.addAttribute("notice", notice);

        return "/notice/noticeDetail";
    }
}
