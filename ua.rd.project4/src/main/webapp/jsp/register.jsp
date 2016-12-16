<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>User add/update</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<%@include file="includes/header.jspf" %>

<form action="${pageContext.request.contextPath}/Controller" method="post">
    <INPUT type="hidden" name="command" value="REGISTER"/>
    <table class="edit">
        <caption><fmt:message key="registerNewUser" bundle="${bundle}"/></caption>
        <tr>
            <th><fmt:message key="field" bundle="${bundle}"/></th>
            <th><fmt:message key="value" bundle="${bundle}"/></th>
        </tr>
        <tr>
            <td><fmt:message key="FirstName" bundle="${bundle}"/></td>
            <td><INPUT type=text name="firstName"
                       value="${empty entity ? '' : entity.getFirstName()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="LastName" bundle="${bundle}"/></td>
            <td><INPUT type=text name="lastName"
                       value="${empty entity ? '' : entity.getLastName()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="Address" bundle="${bundle}"/></td>
            <td><INPUT type=text name="address"
                       value="${empty entity ? '' : entity.getAddress()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="Telephone" bundle="${bundle}"/></td>
            <td><INPUT type=text name="telephone"
                       value="${empty entity ? '' : entity.getTelephone()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="Email" bundle="${bundle}"/></td>
            <td><INPUT type=text name="email"
                       value="${empty entity ? '' : entity.getEmail()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="IDCard" bundle="${bundle}"/></td>
            <td><INPUT type=text name="idCard"
                       value="${empty entity ? '' : entity.getIdCard()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="Login" bundle="${bundle}"/></td>
            <td><INPUT type="text" name="login" value="${empty entity ? '' : entity.getLogin()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="Password" bundle="${bundle}"/></td>
            <td><INPUT type="password" name="pass" value=""/></td>
        </tr>

        <tr>
            <td colspan="2"><INPUT type="submit" value="<fmt:message key="registerNew" bundle="${bundle}"/>">
        </tr>
    </table>
</form>


</body>
</html>
