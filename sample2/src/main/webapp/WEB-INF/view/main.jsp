<%@page import="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder"%>
<%@ page import="com.example.sample2.utils.Define" %>
<%@ page import="com.example.sample2.dto.response.PrincipalDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@48,400,1,0" />
<link rel="stylesheet" href="/css/mainPage.css">
<head>
</head>
<body onLoad="javascript:pop()">

<!-- 세부 메뉴 + 메인 -->
<div class="d-flex justify-content-center align-items-start" style="min-width: 100em;">

	<div>
		<img alt="" src="/images/main_photo.jpg" class="main--page--img">
		<div class="d-flex justify-content-center align-items-start">
			<div class="main--page--div">
				<div class="d-flex">
					<!-- 공지사항 -->
					<div class="main--page--notice">
						<h3>
							<a href="/notice">공지사항</a>
						</h3>
						<div class="main--page--split"></div>
						<table>
							<c:forEach var="notice" items="${noticeList}">
								<tr>
									<td><a href="/notice/read?id=${notice.id}">${notice.category}&nbsp;${notice.title}</a></td>
									<td>${notice.dateFormat()}
								</tr>
							</c:forEach>
						</table>
					</div>
					<div class="main--page--calander">
						<h3>
							<a href="/schedule">학사일정</a>
						</h3>
						<div class="main--page--split"></div>
						<table>
							<c:forEach var="schedule" items="${scheduleList}">
								<tr>
									<td>${schedule.startDay.substring(5)}&nbsp;-&nbsp;${schedule.endDay.substring(5)}</td>
									<td>${schedule.information}</td>
								</tr>
							</c:forEach>
						</table>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>

  		<footer>
			COPYRIGHT(C) 2024 <a href="https://github.com/seoyounglee0105/university_management_project">호그와트 UNIVERSITY</a>. ALL RIGHTS RESERVED.
		</footer>

</div>

</body>
</html>

