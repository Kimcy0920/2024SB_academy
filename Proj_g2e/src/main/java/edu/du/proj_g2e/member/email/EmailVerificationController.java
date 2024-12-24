package edu.du.proj_g2e.member.email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class EmailVerificationController {

    private final EmailService emailService;
    private String sentCode;

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestParam String email) {
        sentCode = emailService.sendVerificationEmail(email);
        return ResponseEntity.ok("이메일이 전송되었습니다. 인증번호를 입력하세요.");
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestParam String verificationCode) {
        if (sentCode != null && sentCode.equals(verificationCode)) {
            return ResponseEntity.ok("인증이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");
        }
    }
}

//@Controller
//@RequestMapping("/auth")
//public class EmailVerificationController {
//
//    private final EmailService emailService;
//    private String sentCode;
//
//    @Autowired
//    public EmailVerificationController(EmailService emailService) {
//        this.emailService = emailService;
//    }
//
//    @PostMapping("/sendEmail")
//    @ResponseBody
//    public String sendEmail(@RequestParam String email) {
//        sentCode = emailService.sendVerificationEmail(email);
//        return "이메일이 전송되었습니다. 인증번호를 입력하세요.";
//    }
//
//    @PostMapping("/verifyEmail")
//    @ResponseBody
//    public String verifyEmail(@RequestParam String verificationCode) {
//        if (sentCode != null && sentCode.equals(verificationCode)) {
//            return "인증이 완료되었습니다.";
//        } else {
//            return "인증번호가 일치하지 않습니다.";
//        }
//    }
//}