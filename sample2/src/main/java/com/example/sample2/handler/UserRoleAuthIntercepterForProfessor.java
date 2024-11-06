package com.example.sample2.handler;

import com.example.sample2.dto.response.PrincipalDto;
import com.example.sample2.handler.exception.UnAuthorizedException;
import com.example.sample2.utils.Define;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 세션값 검사해서 userRole이 professor인지 확인하는 인터셉터 
 * @author
 */
@Component
public class UserRoleAuthIntercepterForProfessor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		PrincipalDto principal = (PrincipalDto) session.getAttribute(Define.PRINCIPAL);
		if (!"professor".equals(principal.getUserRole())) {
			throw new UnAuthorizedException("접근 권한이 없습니다. 교수 전용 페이지", HttpStatus.UNAUTHORIZED, "/");
			// return false;
		}
		return true;
	}



}