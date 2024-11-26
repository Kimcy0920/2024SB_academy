package edu.du.sb1101.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션에서 사용자 정보를 확인
        Object member = request.getSession().getAttribute("member");
        if (member == null) {
            // 로그인 페이지로 리다이렉트
            response.sendRedirect("/sample/login");
            return false; // 요청을 중단
        }
        return true; // 요청을 계속 진행
    }
}
