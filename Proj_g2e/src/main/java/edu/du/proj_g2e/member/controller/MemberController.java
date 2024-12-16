package edu.du.proj_g2e.member.controller;

import edu.du.proj_g2e.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/createMember")
    public String createMember() {
        // 샘플 데이터 생성
        memberService.createMember("testuser", "testuser@example.com");
        return "Member created!";
    }

}