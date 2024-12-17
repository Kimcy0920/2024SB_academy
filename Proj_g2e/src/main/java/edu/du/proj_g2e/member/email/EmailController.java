package edu.du.proj_g2e.member.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/emailConfirm")
    @ResponseBody
    public String emailConfirm(@RequestParam String email) throws Exception {
        // 이메일을 전송하고 인증코드를 반환하는 서비스 메서드
        String confirm = emailService.sendSimpleMessage(email);

        // 인증코드 전송 성공 시 "success" 반환
        return "success";
    }

    // 인증 코드 확인
    @GetMapping("/verifyCode")
    @ResponseBody
    public String verifyCode(@RequestParam String email, @RequestParam String authCode) {
        boolean isValid = emailService.verifyCode(email, authCode); // 인증 코드 확인 로직
        return isValid ? "success" : "failure"; // 인증 성공 여부에 따라 success 또는 failure 반환
    }
}
