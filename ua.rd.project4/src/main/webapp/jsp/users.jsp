<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="localization/messages" var="bundle"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<%@include file="includes/header.jspf" %>
<table class="list">
    <tr>
        <th>ID</th>
        <th><fmt:message key="isAdmin" bundle="${bundle}"/></th>
        <th><fmt:message key="Login" bundle="${bundle}"/></th>
        <th><fmt:message key="Client" bundle="${bundle}"/></th>
        <th><fmt:message key="Edit" bundle="${bundle}"/></th>
        <th><fmt:message key="Delete" bundle="${bundle}"/></th>
    </tr>
    <c:forEach var="user" items="${entities}">
        <tr>
            <td align="center"><c:out value="${user.getId()}"/></td>
            <td align="center"><c:out value="${user.isAdmin()}"/></td>
            <td align="center"><c:out value="${user.getLogin()}"/></td>
            <td align="center">
                <c:if test="${user.getClientId() > 0}">
                    <a href="${pageContext.request.contextPath}/Controller?command=CLIENTS&do=get&id=${user.getClientId()}">
                        <c:out value="${user.getClient().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td align="center">
                <form action="${pageContext.request.contextPath}/Controller" method="post">
                    <INPUT type="hidden" name="command" value="USERS"/>
                    <INPUT type="hidden" name="do" value="get"/>
                    <INPUT type="hidden" name="id" value="${user.getId()}"/>
                    <INPUT type="submit" value="Edit">
                </form>
            </td>
            <td align="center">
                <form action="${pageContext.request.contextPath}/Controller" method="post">
                    <INPUT type="hidden" name="command" value="USERS"/>
                    <INPUT type="hidden" name="do" value="delete"/>
                    <INPUT type="hidden" name="id" value="${user.getId()}"/>
                    <INPUT type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="7">
            <form action="${pageContext.request.contextPath}/Controller" method="post">
                <INPUT type="hidden" name="command" value="USERS"/>
                <INPUT type="hidden" name="do" value="new"/>
                <INPUT type="submit" value="Create New">
            </form>
        </td>
    </tr>
</table>
</body>
</html>
