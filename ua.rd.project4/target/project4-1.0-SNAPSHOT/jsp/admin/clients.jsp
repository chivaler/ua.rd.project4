<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<c:set var="lang" value="${empty lang ? 'uk_UA' : sessionScope.lang}"--%>
       <%--scope="session" />--%>
<%--<fmt:setLocale value="${lang}" />--%>
<fmt:setLocale value="en_US" />
<fmt:setBundle basename="messages" var="bundle" />
<h1>
    <fmt:message key="TheClientList" bundle="${bundle}" />
</h1>
<table>
    <tr>
        <th><fmt:message key="ClientID" bundle="${bundle}" /></th>
        <th><fmt:message key="FirstName" bundle="${bundle}" /></th>
        <th><fmt:message key="LastName" bundle="${bundle}" /></th>
        <th><fmt:message key="Address" bundle="${bundle}" /></th>
        <th><fmt:message key="Telephone" bundle="${bundle}" /></th>
        <th><fmt:message key="Email" bundle="${bundle}" /></th>
    </tr>

    <c:forEach var="client" items="${clients}">
        <c:set var="adm" scope="page" value="${client.isAdmin()}" />
            <tr>
                <td align="center"><c:out value="${client.getId()}" /></td>
                <td align="center"><c:out value="${client.getFirstName()}" /></td>
                <td align="center"><c:out value="${client.getLastName()}" /></td>
                <td align="center"><c:out value="${client.getAddress()}" /></td>
                <td align="center"><c:out value="${client.getTelephone()}" /></td>
                <td align="center"><c:out value="${client.getEmail()}" /></td>
            </tr>
    </c:forEach>
</table>
<br>
</body>
</html>
