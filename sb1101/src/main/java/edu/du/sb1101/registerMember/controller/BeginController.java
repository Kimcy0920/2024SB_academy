package edu.du.sb1101.registerMember.controller;

import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @PostConstruct
//    public void init() {
//        LocalDateTime now = LocalDateTime.now(); // 현재 날짜와 시간 가져오기
//
//        Member member = Member.builder()
//                .id(1001L)
//                .username("관리자")
//                .password("1234")
//                .email("admin@mail.com")
//                .address("성남시 수정구 복정동")
//                .regdate(now)
//                .role("ADMIN")
//                .point(100000)
//                .build();
//        memberRepository.save(member);
//
//        member = Member.builder()
//                .id(1002L)
//                .username("사용자")
//                .password("1234")
//                .email("user@mail.com")
//                .address("서울특별시 광진구 자양로44나길 11 스위트빌라")
//                .regdate(now)
//                .role("USER")
//                .point(5000)
//                .build();
//        memberRepository.save(member);
//    }

//    private PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
