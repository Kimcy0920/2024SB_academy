package edu.du.proj_g2e.member.email;

public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;
    public boolean verifyCode(String email, String authCode);
}