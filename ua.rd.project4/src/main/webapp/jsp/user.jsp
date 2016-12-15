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
    <c:if test="${not empty entity}">
        <INPUT type=hidden class="readonly" readonly="readonly" name="passwordHash"
               value="${empty entity ? '' : entity.getPasswordHash()}"/>
    </c:if>
    <table class="edit">
        <tr>
            <th><fmt:message key="field" bundle="${bundle}"/></th>
            <th><fmt:message key="value" bundle="${bundle}"/></th>
        </tr>
        <%@include file="includes/fields/id.jspf" %>
        <tr>
            <td><fmt:message key="isAdmin" bundle="${bundle}"/></td>
            <td><INPUT type="checkbox" name="isAdmin" ${empty entity ? '' : entity.isAdmin()? 'checked': ''}/></td>
        </tr>
        <tr>
            <td><fmt:message key="Login" bundle="${bundle}"/></td>
            <td><INPUT type="text" name="login" value="${empty entity ? '' : entity.getLogin()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="Password" bundle="${bundle}"/></td>
            <td><INPUT type="password" name="pass" value=""/></td>
        </tr>
        <%@include file="includes/fields/client.jspf" %>
        <tr>
            <td colspan="2"><INPUT type="submit" value="<fmt:message key="save" bundle="${bundle}"/>">
        </tr>
    </table>
</form>


</body>
</html>
