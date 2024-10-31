package edu.du.sb1030.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// Interceptor 코드 추가
public class AuthCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        /*
        getSession(), getSession(true)
        null 체크없이 바로 사용해도 무방함. 세션이 존재하면 현재 세션을 반환하고, 없으면 세션을 생성함.
        getSession(false)
        null 값을 리턴할 수도 있기 때문에 null 체크를 해야함.
         */

        if (session != null) {
            Object authInfo = session.getAttribute("authInfo");
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
    
}
