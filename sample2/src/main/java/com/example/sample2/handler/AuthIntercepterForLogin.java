package com.example.sample2.handler;

import com.example.sample2.handler.exception.UnAuthorizedException;
import com.example.sample2.utils.Define;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 로그인창에서 로그인 되어있으면 돌려보내는 인터셉터
 * @author
 */
@Component
public class AuthIntercepterForLogin implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		if (session.getAttribute(Define.PRINCIPAL) != null) {
			throw new UnAuthorizedException("이미 로그인되어있습니다.", HttpStatus.UNAUTHORIZED, "/");
		}
		return true;
	}



}