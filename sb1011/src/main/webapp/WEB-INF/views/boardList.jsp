<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html lang="ko">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <title>board</title>--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="container">--%>
<%--    <h2>게시글 목록</h2>--%>
<%--    <table class="board_list">--%>
<%--        <colgroup>--%>
<%--            <col width="15%"/>--%>
<%--            <col width="*"/>--%>
<%--            <col width="15%"/>--%>
<%--            <col width="20%"/>--%>
<%--        </colgroup>--%>
<%--        <thead>--%>
<%--        <tr>--%>
<%--            <th scope="col">글번호</th>--%>
<%--            <th scope="col">제목</th>--%>
<%--            <th scope="col">조회수</th>--%>
<%--            <th scope="col">작성일</th>--%>
<%--        </tr>--%>
<%--        </thead>--%>
<%--        <tbody>--%>
<%--        <c:choose>--%>
<%--            <c:when test="${not empty list}">--%>
<%--                <c:forEach var="list" items="${list}">--%>
<%--                    <tr>--%>
<%--                        <td><c:out value="${list.boardIdx}"/></td>--%>
<%--                        <td class="title">--%>
<%--                            <a href="${pageContext.request.contextPath}/board/openBoardDetail.do?boardIdx=${list.boardIdx}">--%>
<%--                                <c:out value="${list.title}"/>--%>
<%--                            </a>--%>
<%--                        </td>--%>
<%--                        <td><c:out value="${list.hitCnt}"/></td>--%>
<%--                        <td><c:out value="${list.createdDatetime}"/></td>--%>
<%--                    </tr>--%>
<%--                </c:forEach>--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--                <tr>--%>
<%--                    <td colspan="4">조회된 결과가 없습니다.</td>--%>
<%--                </tr>--%>
<%--            </c:otherwise>--%>
<%--        </c:choose>--%>
<%--        </tbody>--%>
<%--    </table>--%>
<%--    <a href="${pageContext.request.contextPath}/board/openBoardWrite.do" class="btn">글 쓰기</a>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
