package edu.du.sb1030.controller;

import edu.du.sb1030.spring.Member;
import edu.du.sb1030.spring.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MemberListController {

    @Autowired
    private MemberDao memberDao;

    @RequestMapping("/members")
    public String list(@ModelAttribute("cmd") ListCommand listCommand, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "/member/memberList";
        }
        // from과 to가 null이 아니면 문자열을 LocalDate로 변환
        if (listCommand.getFrom() != null && listCommand.getTo() != null) {
            LocalDate fromDate = listCommand.getFrom();
            LocalDate toDate = listCommand.getTo();

            // 변환된 날짜로 조회
            List<Member> members = memberDao.selectByRegdate(fromDate, toDate);
            model.addAttribute("members", members);
        }
        return "/member/memberList";
    }
}
