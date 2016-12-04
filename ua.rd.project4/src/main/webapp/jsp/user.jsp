<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="localization/messages" var="bundle"/>
<html>
<head>
    <title>User add/update</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<%@include file="includes/header.jspf" %>

<form action="${pageContext.request.contextPath}/Controller" method="post">
    <INPUT type="hidden" name="command" value="USERS"/>
    <INPUT type="hidden" name="do" value="update"/>
    <table class="edit">
        <tr>
            <th>Field</th>
            <th>Value</th>
        </tr>
        <%@include file="includes/fields/id.jspf" %>
        <tr>
            <td>isAdmin</td>
            <td><INPUT type="checkbox" name="isAdmin" ${empty entity ? '' : entity.isAdmin()? 'checked': ''}/></td>
        </tr>
        <tr>
            <td>login</td>
            <td><INPUT type="text" name="login" value="${empty entity ? '' : entity.getLogin()}"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><INPUT type="password" name="pass" value=""/></td>
        </tr>
        <c:if test="${not empty entity}">
            <tr>
                <td>HashedPassword</td>
                <td><INPUT type=text class="readonly" readonly="readonly" name="passwordHash"
                           value="${empty entity ? '' : entity.getPasswordHash()}"/>
                </td>
            </tr>
        </c:if>
        <%@include file="includes/fields/client.jspf" %>
        <tr>
            <td></td>
            <td><INPUT type="submit" value="Save">
        </tr>
    </table>
</form>


</body>
</html>
