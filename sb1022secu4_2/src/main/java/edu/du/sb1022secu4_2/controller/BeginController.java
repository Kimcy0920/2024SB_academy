package edu.du.sb1022secu4_2.controller;

import edu.du.sb1022secu4_2.entity.Member;
import edu.du.sb1022secu4_2.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;

@Controller
public class BeginController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .id(1001L)
                .username("홍길동")
                .password(passwordEncoder().encode("1234"))
                .email("hong@korea.com")
                .build();
        memberRepository.save(member);
    }
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/")
    public String index() {
        return "/sample/all";
    }

    @GetMapping("/sample/register")
    public String register(Model model) {
        model.addAttribute("member", new Member()); // Member 객체를 Model에 추가
        return "/sample/register"; // register.html 뷰를 반환
    }
    @PostMapping("/sample/register2")
    public String register2(@ModelAttribute("member") Member member) {
//        member.setPassword(passwordEncoder.encode(member.getPassword()));
        Member mem = Member.builder()
                .username(member.getUsername())
                .email(member.getEmail())
                .password(passwordEncoder.encode(member.getPassword()))
                .build();
        memberRepository.save(mem);
        return "redirect:/sample/all";
    }
}
