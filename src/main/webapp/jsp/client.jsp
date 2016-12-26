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
    <INPUT type="hidden" name="command" value="CLIENTS"/>
    <INPUT type="hidden" name="do" value="update"/>
    <table class="edit">
        <tr>
            <th><fmt:message key="field" bundle="${bundle}"/></th>
            <th><fmt:message key="value" bundle="${bundle}"/></th>
        </tr>
        <c:if test="${not empty entity}">
            <c:if test="${not empty entity.getId()}">
                <tr>
                    <td><fmt:message key="id" bundle="${bundle}"/></td>
                    <td><INPUT type=text class="readonly" readonly="readonly" name="id"
                               value="${empty entity ? '' : entity.getId()}"/></td>
                </tr>
            </c:if>
        </c:if>
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
            <td colspan="2">
                <INPUT type="submit" value="${empty entity ? 'Create' : 'Save'}">
            </td>
        </tr>
    </table>
</form>


</body>
</html>
