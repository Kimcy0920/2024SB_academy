package edu.du.proj_g2e.member.email_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class EmailVerificationController {

    private final EmailService emailService;
    private String sentCode;

    @Autowired
    public EmailVerificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(@RequestParam String email) {
        sentCode = emailService.sendVerificationEmail(email);
        return "이메일이 전송되었습니다. 인증번호를 입력하세요.";
    }

    @PostMapping("/verifyEmail")
    @ResponseBody
    public String verifyEmail(@RequestParam String verificationCode) {
        if (sentCode != null && sentCode.equals(verificationCode)) {
            return "인증이 완료되었습니다.";
        } else {
            return "인증번호가 일치하지 않습니다.";
        }
    }
}