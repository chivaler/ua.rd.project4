<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="localization/messages" var="bundle"/>
<html>
<head>
    <title>User add/update</title>
    <link rel="stylesheet" type="text/css" href="/style.css">
</head>
<body>
<c:if test="${not empty result}">
    <div id="result"><c:out value="${result}"/></div>
</c:if>
<c:if test="${not empty error}">
    <div id="error"><c:out value="${error}"/></div>
</c:if>

<form action="/Controller" method="post">
    <INPUT type="hidden" name="command" value="CLIENTS"/>
    <INPUT type="hidden" name="do" value="update"/>
    <table class="edit">
        <tr>
            <th>Field</th>
            <th>Value</th>
        </tr>
        <c:if test="${not empty entity}">
            <c:if test="${not empty entity.getId()}">
                <tr>
                    <td><fmt:message key="ID" bundle="${bundle}"/></td>
                    <td><INPUT type=text class="readonly" readonly="readonly" name="id"
                               value="${empty entity ? '' : entity.getId()}"/></td>
                </tr>
            </c:if>
        </c:if>
        <tr>
            <td><fmt:message key="firstName" bundle="${bundle}"/></td>
            <td><INPUT type=text name="firstName"
                       value="${empty entity ? '' : entity.getFirstName()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="lastName" bundle="${bundle}"/></td>
            <td><INPUT type=text name="lastName"
                       value="${empty entity ? '' : entity.getLastName()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="address" bundle="${bundle}"/></td>
            <td><INPUT type=text name="address"
                       value="${empty entity ? '' : entity.getAddress()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="telephone" bundle="${bundle}"/></td>
            <td><INPUT type=text name="telephone"
                       value="${empty entity ? '' : entity.getTelephone()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="email" bundle="${bundle}"/></td>
            <td><INPUT type=text name="email"
                       value="${empty entity ? '' : entity.getEmail()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="idCard" bundle="${bundle}"/></td>
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
