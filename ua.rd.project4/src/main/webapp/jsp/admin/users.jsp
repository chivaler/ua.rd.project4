<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="localization/messages" var="bundle"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/style.css">
</head>
<body>
<c:if test="${not empty result}">
    <div id="result"><c:out value="${result}"/></div>
</c:if>
<c:if test="${not empty error}">
    <div id="error"><c:out value="${error}"/></div>
</c:if>
<table>
    <tr>
        <th>ID</th>
        <th><fmt:message key="isAdmin" bundle="${bundle}"/></th>
        <th><fmt:message key="Login" bundle="${bundle}"/></th>
        <th><fmt:message key="HashedPassword" bundle="${bundle}"/></th>
        <th><fmt:message key="Client" bundle="${bundle}"/></th>
        <th><fmt:message key="Edit" bundle="${bundle}"/></th>
        <th><fmt:message key="Delete" bundle="${bundle}"/></th>
    </tr>

    <c:forEach var="user" items="${entities}">
        <tr>
            <td align="center"><c:out value="${user.getId()}"/></td>
            <td align="center"><c:out value="${user.isAdmin()}"/></td>
            <td align="center"><c:out value="${user.getLogin()}"/></td>
            <td align="center"><c:out value="${user.getPassword()}"/></td>
            <td align="center">
                <c:if test="${user.getClientId() > 0}">
                    <a href="/Controller?command=CLIENTS&do=get&id=${user.getClientId()}">
                        <c:out value="${user.getClient().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td align="center">
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="USERS"/>
                    <INPUT type="hidden" name="do" value="get"/>
                    <INPUT type="hidden" name="id" value="${user.getId()}"/>
                    <INPUT type="submit" value="Edit">
                </form>
            </td>
            <td align="center">
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="USERS"/>
                    <INPUT type="hidden" name="do" value="delete"/>
                    <INPUT type="hidden" name="id" value="${user.getId()}"/>
                    <INPUT type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="/jsp/admin/user.jsp" method="get">
    <INPUT type="submit" value="Create New">
</form>
</body>
</html>
