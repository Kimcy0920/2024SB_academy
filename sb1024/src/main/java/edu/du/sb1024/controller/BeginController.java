package edu.du.sb1024.controller;

import edu.du.sb1024.entity.Member;
import edu.du.sb1024.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Controller
@Log4j2
public class BeginController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/")
    public String index() {
        return "/sample/all";
    }

    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .id(1001L)
                .email("admin@mail.com")
                .username("관리자")
                .password(passwordEncoder().encode("1234"))
                .role("ADMIN")
                .registerDateTime(LocalDateTime.now())
                .build();
        memberRepository.save(member);

        member = Member.builder()
                .id(1002L)
                .email("user@mail.com")
                .username("유저")
                .password(passwordEncoder().encode("1234"))
                .role("USER")
                .registerDateTime(LocalDateTime.now())
                .build();
        memberRepository.save(member);
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
