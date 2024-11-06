package edu.du.sb1022secu4.controller;

import edu.du.sb1022secu4.entity.Member;
import edu.du.sb1022secu4.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;

@Controller
public class BeginController {

    @Autowired
    MemberRepository memberRepository;

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

}
