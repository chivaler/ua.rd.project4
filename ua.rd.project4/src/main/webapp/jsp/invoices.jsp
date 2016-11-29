<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/style.css">
</head>
<body>
<fmt:setBundle basename="localization/messages" var="bundle"/>
<c:if test="${not empty result}">
    <div id="result"><c:out value="${result}"/></div>
</c:if>
<c:if test="${not empty error}">
    <div id="error"><c:out value="${error}"/></div>
</c:if>
<h1>
    <fmt:message key="Invoices" bundle="${bundle}"/>
</h1>
<table class="list">
    <tr>
        <th>ID</th>
        <th><fmt:message key="Client" bundle="${bundle}"/></th>
        <th><fmt:message key="Total" bundle="${bundle}"/></th>
        <th><fmt:message key="Paid" bundle="${bundle}"/></th>
        <th><fmt:message key="Description" bundle="${bundle}"/></th>
        <th><fmt:message key="EDIT" bundle="${bundle}"/></th>
        <th><fmt:message key="DELETE" bundle="${bundle}"/></th>
    </tr>
    <c:forEach var="invoice" items="${entities}">
        <tr>
            <td align="center"><c:out value="${invoice.getId()}"/></td>
            <td>
                <c:if test="${invoice.getClientId() > 0}">
                    <a href="/Controller?command=CLIENTS&do=get&id=${invoice.getClientId()}">
                        <c:out value="${invoice.getClient().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td align="center"><c:out value="${invoice.getTotal()}"/></td>
            <td align="center"><c:out value="${invoice.isPaid()}"/></td>
            <td align="center"><c:out value="${invoice.getDescription()}"/></td>
            <td align="center">
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="INVOICES"/>
                    <INPUT type="hidden" name="id" value="${invoice.getId()}"/>
                    <INPUT type="hidden" name="do" value="get"/>
                    <INPUT type="hidden" name="id" value="${invoice.getId()}"/>
                    <INPUT type="submit" value="Edit">
                </form>
            </td>
            <td align="center">
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="INVOICES"/>
                    <INPUT type="hidden" name="do" value="delete"/>
                    <INPUT type="hidden" name="id" value="${invoice.getId()}"/>
                    <INPUT type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
</body>
</html>
