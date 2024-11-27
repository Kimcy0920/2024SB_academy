package edu.du.sb1101.registerMember.controller;

import edu.du.sb1101.fileUploadBoard.board.dto.BoardDto;
import edu.du.sb1101.fileUploadBoard.board.service.BoardService;
import edu.du.sb1101.fileUploadBoard.entity.Board;
import edu.du.sb1101.fileUploadBoard.repository.BoardRepository;
import edu.du.sb1101.notice.entity.Notice;
import edu.du.sb1101.notice.repository.NoticeRepository;
import edu.du.sb1101.point.PointLog;
import edu.du.sb1101.point.PointLogRepository;
import edu.du.sb1101.registerMember.entity.LoginDto;
import edu.du.sb1101.registerMember.entity.MemDto2;
import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.entity.MemDto;
import edu.du.sb1101.registerMember.repository.MemberRepository;
import edu.du.sb1101.registerMember.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sample/")
@Log4j2
public class SampleController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PointLogRepository pointLogRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    private final MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private NoticeRepository noticeRepository;

    public SampleController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 중복된 회원 정보를 모델에 추가하는 메서드
    private void addMemberToModel(Member member, Model model) {
        model.addAttribute("id", member.getId());
        model.addAttribute("email", member.getEmail());
        model.addAttribute("username", member.getUsername());
        model.addAttribute("address", member.getAddress());
        model.addAttribute("role", member.getRole());
        model.addAttribute("regdate", member.getRegdate());
        model.addAttribute("password", member.getPassword());
        model.addAttribute("point", member.getPoint());
    }

    @GetMapping("/accessDenied")
    public void accessDenied() {}

    @GetMapping("/all")
    public String exAll(Model model, HttpSession session) throws Exception {
        // 세션에서 Member 객체 가져오기
        Member member = (Member) session.getAttribute("member");

        // 로그인한 경우에만 username을 모델에 추가
        if (member != null) {
            model.addAttribute("username", member.getUsername());
            model.addAttribute("role", member.getRole());

            // 최근 게시글 목록 가져오기
            List<BoardDto> recentList = boardService.selectRecentBoardList(5);
            model.addAttribute("recentList", recentList);

            // 공지사항 목록 가져오기
            List<Notice> noticeList = noticeRepository.findTop5ByOrderByRegdateDesc();
            model.addAttribute("noticeList", noticeList);

            return "sample/all";
        }
        return "sample/all";
    }

    @GetMapping("/points")
    public String viewPointLogs(HttpSession session, Model model) throws Exception {
        Member member = (Member) session.getAttribute("member");

        if (member == null) {
            return "redirect:/sample/login";
        }
        List<PointLog> pointLogs = pointLogRepository.findByMemberIdOrderByCreatedAtDesc(member.getId());
        model.addAttribute("pointLogs", pointLogs);

        return "/sample/pointLogs";
    }

    @GetMapping("/member")
    public String exMember(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }

        addMemberToModel(member, model);  // 중복된 코드 호출
        model.addAttribute("memDto", new MemDto()); // MemDto 객체를 전달
        return "sample/member";
    }
    @PostMapping("/infoUpdate")
    public String infoUpdate(@Valid MemDto memDto, BindingResult bindingResult, HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("member");

        if (bindingResult.hasErrors()) {
            // 유효성 검증 실패 시, 오류 메시지를 모델에 추가하고 수정 페이지로 리다이렉트
            addMemberToModel(member, model);  // 중복된 코드 호출
            return "sample/member";
        }

        // 입력받은 정보를 Member 객체에 반영
        member.setEmail(memDto.getEmail());
        member.setUsername(memDto.getUsername());
        member.setAddress(memDto.getAddress());

        // 데이터베이스에 저장
        memberRepository.save(member);

        return "redirect:/sample/member"; // 업데이트 후 회원 정보 페이지로 리다이렉트
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "sample/login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginDto loginDto, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            return "sample/login";  // 유효성 검사 실패 시 로그인 폼으로 돌아감
        }

        Member member = memberRepository.findByEmail(loginDto.getEmail());
        if (member != null && passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            session.setAttribute("member", member);
            log.info("Login successful for user & password : {}", member.getUsername());

            LocalDate today = LocalDate.now();
            if (member.getLastPointDate() == null || !member.getLastPointDate().isEqual(today)) {
                member.setLastPointDate(today);
                memberRepository.save(member);
                memberService.addPoints(member.getUsername(), 10, "로그인 +10포인트 적립");
                session.setAttribute("member", member);
            } else {
                System.out.println("이미 로그인 포인트가 적립되었습니다.");
            }
            return "redirect:/sample/all";
        } else {
            System.out.println("잘못된 이메일 또는 비밀번호입니다.");
            model.addAttribute("errorMessage", "잘못된 이메일 또는 비밀번호입니다.");
            log.warn("Login failed for email: {}", loginDto.getEmail());
            return "sample/login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/sample/login?logout"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }

    @GetMapping("/findPwd")
    public String findPwdForm(Model model) {
        model.addAttribute("memDto", new MemDto2());
        return "sample/findPwd";
    }
    @PostMapping("/findPwd")
    public String findPwd(@Valid MemDto2 memDto,
                          BindingResult bindingResult,
                          HttpSession session,
                          Model model) {
        // 유효성 검사 에러 처리
        if (bindingResult.hasErrors()) {
            model.addAttribute("memDto", memDto);  // 유효성 검사 실패 시 memDto 객체를 다시 모델에 추가
            return "sample/findPwd";
        }

        // 아이디와 성명 데이터베이스 조회
        Member member = memberRepository.findByEmailAndUsername(memDto.getEmail(), memDto.getUsername());
        if (member == null) {
            // 아이디와 성명이 모두 입력되었는데 조회 실패 시 에러 메시지 설정
            model.addAttribute("errorMessage", "아이디 또는 성명이 잘못되었습니다.");
            model.addAttribute("memDto", memDto);
            return "sample/findPwd";
        }

        // 조회 성공 시 세션에 이메일 저장 후 이동
        session.setAttribute("email", memDto.getEmail());
        return "redirect:/sample/changePwd";
    }


    @GetMapping("/changePwd")
    public String changePwdForm(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        System.out.println("Session email: " + email);
        if (email == null) {
            System.out.println("Session email is null");
            return "redirect:/sample/findPwd";
        }
        model.addAttribute("email", email);
        return "/sample/changePwd";
    }
    @PostMapping("/changePwd")
    public String changePwd(@RequestParam String email,
                            @RequestParam String newPassword,
                            @RequestParam String confirmPassword) {
        if (newPassword.equals(confirmPassword)) {
            Member member = memberRepository.findByEmail(email);
            member.setPassword(passwordEncoder.encode(newPassword));
            memberRepository.save(member);
            System.out.println("비밀번호 변경");
            return "redirect:/sample/login";
        } else {
            System.out.println("입력한 비밀번호가 일치하지 않습니다.");
            return "sample/changePwd";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("member");

        // 서버 측에서 메시지 추가
        if (member != null) {
            // 현재 비밀번호가 일치하는지 확인
            if (passwordEncoder.matches(currentPassword, member.getPassword())) {
                // 새 비밀번호와 확인 비밀번호가 일치하는지 확인
                if (newPassword.equals(confirmPassword)) {
                    // 비밀번호 변경
                    member.setPassword(passwordEncoder.encode(newPassword));
                    memberRepository.save(member);
                    model.addAttribute("successMessage", "비밀번호가 변경되었습니다.");
                    return "redirect:/sample/login"; // 성공 시 로그인 페이지
                } else {
                    model.addAttribute("errorMessage", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
                }
            } else {
                model.addAttribute("errorMessage", "현재 비밀번호가 일치하지 않습니다.");
            }
        }
        return "sample/changePassword"; // 실패 시 비밀번호 변경 페이지로 돌아가기
    }

    @GetMapping("/findEmail") // 아이디 찾기 폼
    public String findEmail() {
        return "sample/findEmail";
    }

    @PostMapping("/findEmail") // 아이디 찾기 컨트롤러
    public String findEmail(@RequestParam String username, Model model) {
        Member member = memberRepository.findByUsername(username);

        // 기본값을 설정하여 null을 방지
        model.addAttribute("emailFound", false);  // 기본값 false 설정

        if (member != null) {
            model.addAttribute("emailFound", true);
            model.addAttribute("email", member.getEmail());
            System.out.println("이메일: " + member.getEmail());
        } else {
            model.addAttribute("errorMessage", "해당 이름으로 등록된 사용자가 없습니다.");
            System.out.println("해당 이름으로 가입된 정보가 없습니다.");
        }

        return "sample/findEmail"; // 같은 페이지로 돌아가서 결과를 표시
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpSession session) {
        // 세션에서 Member 객체 가져오기
        Member member = (Member) session.getAttribute("member");

        // member가 null인지 확인
        if (member == null) {
            System.out.println("관리자 전용 페이지입니다. 로그인하세요.");
            return "redirect:/sample/login"; // 로그인 페이지로 리디렉션
        }

        // member의 role이 USER인 경우
        if ("USER".equals(member.getRole())) {
            System.out.println("관리자 전용 페이지입니다. 권한이 없습니다.");
            return "redirect:/sample/all"; // 권한 없는 페이지로 리디렉션
        }

        // ADMIN 권한이 있는 경우
        if ("ADMIN".equals(member.getRole())) {
            // 세션에서 가져온 member의 username을 모델에 추가
            model.addAttribute("username", member.getUsername());
            model.addAttribute("role", member.getRole());

            // 회원 목록 가져오기
            List<Member> members = memberRepository.findAllByOrderByIdAsc();
            model.addAttribute("memberList", members);
        }

        return "sample/admin";
    }
    @PostMapping("/memberList")
    public String memberList(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

        if (member != null && "ADMIN".equals(member.getRole())) {
            List<Member> members = memberRepository.findAllByOrderByIdAsc();
            log.info("Fetched members: {}", members);

            model.addAttribute("memberList", members);

            return "sample/admin"; // 관리자 페이지로 이동
        } else {
            System.out.println("관리자 전용 페이지입니다.");
            return "redirect:/sample/all";
        }
    }
    @PostMapping("/memberDel")
    public String memberDel(@RequestParam Long id, HttpSession session) {
        Member member = (Member) session.getAttribute("member");

    // 회원 정보 가져오기
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            return "redirect:/sample/admin"; // 회원이 존재하지 않음
        }
        Member targetMember = optionalMember.get();

        // 해당 회원이 작성한 게시글 모두 삭제
        List<Board> boards = boardRepository.findByCreatorId(targetMember.getUsername());
        if (!boards.isEmpty()) {
            boardRepository.deleteAll(boards);
        }
        // 회원 삭제
        memberRepository.deleteById(id);
        return "redirect:/sample/admin"; // 변경된 URL로 리다이렉트
    }
}
