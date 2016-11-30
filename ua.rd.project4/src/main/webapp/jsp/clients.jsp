<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/style.css">
</head>
<body>
<%@include file="includes/header.jspf" %>
<h1>
    <fmt:message key="TheClientList" bundle="${bundle}"/>
</h1>
<table class="list">
    <tr>
        <th><fmt:message key="ID" bundle="${bundle}"/></th>
        <th><fmt:message key="FirstName" bundle="${bundle}"/></th>
        <th><fmt:message key="LastName" bundle="${bundle}"/></th>
        <th><fmt:message key="Address" bundle="${bundle}"/></th>
        <th><fmt:message key="Telephone" bundle="${bundle}"/></th>
        <th><fmt:message key="Email" bundle="${bundle}"/></th>
        <th><fmt:message key="IDCard" bundle="${bundle}"/></th>
        <th><fmt:message key="EDIT" bundle="${bundle}"/></th>
        <th><fmt:message key="DELETE" bundle="${bundle}"/></th>
    </tr>
    <c:forEach var="client" items="${entities}">
        <tr>
            <td><c:out value="${client.getId()}"/></td>
            <td><c:out value="${client.getFirstName()}"/></td>
            <td><c:out value="${client.getLastName()}"/></td>
            <td><c:out value="${client.getAddress()}"/></td>
            <td><c:out value="${client.getTelephone()}"/></td>
            <td><c:out value="${client.getEmail()}"/></td>
            <td><c:out value="${client.getIdCard()}"/></td>
            <td>
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="CLIENTS"/>
                    <INPUT type="hidden" name="id" value="${client.getId()}"/>
                    <INPUT type="hidden" name="do" value="get"/>
                    <INPUT type="hidden" name="id" value="${client.getId()}"/>
                    <INPUT type="submit" value="Edit">
                </form>
            </td>
            <td>
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="CLIENTS"/>
                    <INPUT type="hidden" name="do" value="delete"/>
                    <INPUT type="hidden" name="id" value="${client.getId()}"/>
                    <INPUT type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="9">
            <form action="/jsp/client.jsp" method="get">
                <INPUT type="submit" value="Create New">
            </form>
        </td>
    </tr>
</table>
<br>
</body>
</html>
