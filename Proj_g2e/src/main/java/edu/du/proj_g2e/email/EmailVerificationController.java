package edu.du.proj_g2e.email;

import edu.du.proj_g2e.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class EmailVerificationController {

    private final MemberRepository memberRepository;
    private final EmailService emailService;
    private String sentCode;

    @PostMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        if (memberRepository.findByEmail(email) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 등록된 이메일입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        }
    }
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
