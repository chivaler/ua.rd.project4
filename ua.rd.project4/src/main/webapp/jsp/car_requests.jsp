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
    <fmt:message key="CarRequests" bundle="${bundle}"/>
</h1>
<table class="list">
    <tr>
        <th>ID</th>
        <th><fmt:message key="car" bundle="${bundle}"/></th>
        <th><fmt:message key="client" bundle="${bundle}"/></th>
        <th><fmt:message key="dateFrom" bundle="${bundle}"/></th>
        <th><fmt:message key="dateTo" bundle="${bundle}"/></th>
        <th><fmt:message key="totalCost" bundle="${bundle}"/></th>
        <th><fmt:message key="approved" bundle="${bundle}"/></th>
        <th><fmt:message key="invoice" bundle="${bundle}"/></th>
        <th><fmt:message key="EDIT" bundle="${bundle}"/></th>
        <th><fmt:message key="DELETE" bundle="${bundle}"/></th>
    </tr>
    <c:forEach var="carRequest" items="${entities}">
        <tr>
            <td align="center"><c:out value="${carRequest.getId()}"/></td>
            <td>
                <c:if test="${carRequest.getCarId() > 0}">
                    <a href="/Controller?command=CARS&do=get&id=${carRequest.getCarId()}">
                        <c:out value="${carRequest.getCar().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td>
                <c:if test="${carRequest.getClientId() > 0}">
                    <a href="/Controller?command=CLIENTS&do=get&id=${carRequest.getClientId()}">
                        <c:out value="${carRequest.getClient().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td align="center"><c:out value="${carRequest.getDateFrom()}"/></td>
            <td align="center"><c:out value="${carRequest.getDateTo()}"/></td>
            <td align="center"><c:out value="${carRequest.getTotalCost()}"/></td>
            <td align="center"><c:out value="${carRequest.isApproved()}"/></td>
            <td>
                <c:if test="${carRequest.getInvoiceId() > 0}">
                    <a href="/Controller?command=INVOICES&do=get&id=${carRequest.getInvoiceId()}">
                        <c:out value="${carRequest.getInvoice().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td align="center">
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                    <INPUT type="hidden" name="id" value="${carRequest.getId()}"/>
                    <INPUT type="hidden" name="do" value="get"/>
                    <INPUT type="hidden" name="id" value="${carRequest.getId()}"/>
                    <INPUT type="submit" value="Edit">
                </form>
            </td>
            <td align="center">
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                    <INPUT type="hidden" name="do" value="delete"/>
                    <INPUT type="hidden" name="id" value="${carRequest.getId()}"/>
                    <INPUT type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="90">
            <form action="/Controller" method="get">
                <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                <INPUT type="hidden" name="do" value="new"/>
                <INPUT type="submit" value="Create New">
            </form>
        </td>
    </tr>
</table>
<br>
</body>
</html>
