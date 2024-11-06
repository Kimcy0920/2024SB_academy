package edu.du.sb1030.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.du.sb1030.spring.AuthInfo;
import edu.du.sb1030.spring.AuthService;
import edu.du.sb1030.spring.WrongIdPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public String form(LoginCommand loginCommand,
                       @CookieValue(value = "REMEMBER", required = false) Cookie rCookie) {
        // 쿠키가 있는지 없는지 여부 확인, 쿠키 생성
        if (rCookie != null) {
            loginCommand.setEmail(rCookie.getValue());
            loginCommand.setRememberEmail(true);
        }
        System.out.println("-----------------여기");
    	return "/login/loginForm";
    }

    @PostMapping
    public String submit(@ModelAttribute("loginCommand") LoginCommand loginCommand, Errors errors, HttpSession session,
                         HttpServletResponse response) {
        // LoginCommand loginCommand 커맨드 객체, @ModelAttribute가 생략된 것.
        // 로그인 폼-> <form th:action="@{/login}" th:object="${loginCommand}" method="post">

        new LoginCommandValidator().validate(loginCommand, errors);
        if (errors.hasErrors()) {
            return "/login/loginForm";
        }
        try {
            AuthInfo authInfo = authService.authenticate(
                    loginCommand.getEmail(),
                    loginCommand.getPassword());
            session.setAttribute("authInfo", authInfo);
            
            // 쿠키 생명주기 관련 코드
            Cookie rememberCookie = new Cookie("REMEMBER", loginCommand.getEmail()); // 쿠키 생성
            rememberCookie.setPath("/"); // "/" 루트에 저장함, 디렉토리 구조
            // 쿠키
            if (loginCommand.isRememberEmail()) { // 커맨드객체 isRememberEmail_Boolean true/false
                rememberCookie.setMaxAge(60 * 60 * 24 * 30); // true
            } else {
                rememberCookie.setMaxAge(0); // false
            }
            response.addCookie(rememberCookie); // 쿠키 저장, 브라우저 응답.

            System.out.println(authInfo.getName() + " 세션 저장!");
            return "/login/loginSuccess";
        } catch (WrongIdPasswordException e) {
            errors.reject("idPasswordNotMatching");
            return "/login/loginForm";
        }
    }
}
