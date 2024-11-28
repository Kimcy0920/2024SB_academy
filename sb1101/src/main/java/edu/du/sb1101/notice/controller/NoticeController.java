package edu.du.sb1101.notice.controller;

import edu.du.sb1101.notice.entity.Notice;
import edu.du.sb1101.notice.entity.NoticeDto;
import edu.du.sb1101.notice.repository.NoticeRepository;
import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.entity.Title;
import edu.du.sb1101.registerMember.repository.TitleRepository;
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

    private final TitleRepository titleRepository;

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

        // Member 객체에 연결된 Title 가져오기 (가장 활성화된 칭호)
        Title title = titleRepository.findActiveTitleByMemberId(member.getId());
        // Model에 title과 username 전달
        model.addAttribute("title", title);

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
        model.addAttribute("noticeDto", new NoticeDto());

        if (member.getRole().equals("ADMIN")) {
            return "/notice/noticeWrite";
        } else {
            System.out.println("관리자 전용입니다.");
            return "redirect:/notice/noticeList";
        }
    }
    @PostMapping("/noticeWrite")
    public String noticeWrite(@Valid NoticeDto noticeDto, BindingResult bindingResult,
                              Model model, HttpSession session) throws Exception {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }

        // 유효성 검사에서 오류가 발생한 경우, 다시 폼으로 돌아가서 오류를 표시
        if (bindingResult.hasErrors()) {
            model.addAttribute("username", member.getUsername());
            model.addAttribute("role", member.getRole());
            return "/notice/noticeWrite"; // 폼을 다시 반환하여 오류 메시지를 표시합니다.
        }

        Notice notice = Notice.builder()
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .username(member.getUsername())
                .regdate(LocalDateTime.now())
                .build();
        noticeRepository.save(notice);

        return "redirect:/notice/noticeList";
    }

    @PostMapping("/noticeUpdate")
    public String noticeUpdate(@RequestParam("id") Integer id,
                               @RequestParam String title,
                               @RequestParam String content,
                               HttpSession session,
                               Model model) throws Exception {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }

        Notice notice = noticeRepository.findById(id).orElse(null);
        if (notice != null && member.getRole().equals("ADMIN")) {
            if (title.trim().isEmpty() || content.trim().isEmpty()) {
                // 에러 메시지 설정
                model.addAttribute("errorMessage", "제목과 내용을 모두 입력해주세요.");
                model.addAttribute("notice", notice); // 기존 값 유지
                model.addAttribute("username", member.getUsername());
                return "/notice/noticeDetail"; // 상세 페이지로 이동
            }

            notice.setTitle(title);
            notice.setContent(content);
            noticeRepository.save(notice);
            return "redirect:/notice/noticeList";
        } else {
            model.addAttribute("errorMessage", "수정 권한이 없습니다.");
            model.addAttribute("notice", notice);
            model.addAttribute("username", member.getUsername());
            return "/notice/noticeDetail";
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

        // Member 객체에 연결된 Title 가져오기 (가장 활성화된 칭호)
        Title title = titleRepository.findActiveTitleByMemberId(member.getId());
        // Model에 title과 username 전달
        model.addAttribute("title", title);

        Notice notice = noticeRepository.findById(id).orElse(null);
        if (notice == null) {
            return "redirect:/notice/noticeList"; // 데이터가 없으면 리스트로 리다이렉트
        }
        model.addAttribute("notice", notice);

        return "/notice/noticeDetail";
    }
}
