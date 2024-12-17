package edu.du.proj_g2e.member.email_2;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 이메일 인증번호 생성 및 전송
    public String sendVerificationEmail(String toEmail) {
        // 인증번호 생성 (6자리 숫자)
        String verificationCode = generateVerificationCode();

        // 이메일 본문 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("이메일 인증번호");
        message.setText("인증번호는 " + verificationCode + " 입니다.");

        // 이메일 전송
        mailSender.send(message);

        return verificationCode; // 인증번호를 반환
    }

    // 인증번호 생성
    private String generateVerificationCode() {
        Random rand = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(rand.nextInt(10));
        }
        return code.toString();
    }
}