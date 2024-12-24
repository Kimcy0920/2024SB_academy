package edu.du.proj_g2e.member.email;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Profile("private")
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendVerificationEmail(String toEmail) {
        String verificationCode = generateVerificationCode();
        String htmlContent =
                "<html><body style='font-family: Arial, sans-serif; background-color: #f4f6f9; color: #333;'>" +
                "<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);'>" +
                "<h2 style='color: #007bff; text-align: center;'>Medical Info Service [MIS]</h2>" +
                "<p style='font-size: 16px; color: #555;'>본 이메일은 이메일 인증번호를 안내드리기 위해 발송되었습니다.</p>" +
                "<div style='border: 2px solid #007bff; border-radius: 5px; background-color: #f1faff; padding: 20px; margin: 20px 0;'>" +
                "<h3 style='font-size: 24px; color: #007bff; text-align: center;'>" +
                "인증번호: <span style='font-size: 36px; font-weight: bold; color: #28a745;'>" + verificationCode + "</span>" +
                "</h3>" +
                "</div>" +
                "<p style='font-size: 16px; color: #555;'>위 인증번호를 입력하여 인증을 완료해 주세요.</p>" +
                "<p style='font-size: 16px; color: #555;'>감사합니다.</p>" +
                "<footer style='font-size: 12px; color: #888; text-align: center; margin-top: 30px;'>" +
                "<p>Medical Info Service [MIS]</p>" +
                "</footer>" +
                "</div>" +
                "</body></html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true for HTML content
            helper.setFrom("operztioncwal@gmail.com", "Medical Info Service");
            helper.setTo(toEmail);
            helper.setSubject("이메일 인증번호");
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return verificationCode;
    }

    private String generateVerificationCode() {
        Random rand = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(rand.nextInt(10));
        }
        return code.toString();
    }
}
