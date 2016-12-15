<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<%@include file="includes/header.jspf" %>
<h1>
    <fmt:message key="Invoices" bundle="${bundle}"/>
</h1>
<table class="list">
    <tr>
        <th>ID</th>
        <th><fmt:message key="Client" bundle="${bundle}"/></th>
        <th><fmt:message key="total" bundle="${bundle}"/></th>
        <th><fmt:message key="paid" bundle="${bundle}"/></th>
        <th><fmt:message key="description" bundle="${bundle}"/></th>
        <th><fmt:message key="EDIT" bundle="${bundle}"/></th>
        <th><fmt:message key="DELETE" bundle="${bundle}"/></th>
    </tr>
    <c:forEach var="invoice" items="${entities}">
        <tr>
            <td align="center"><c:out value="${invoice.getId()}"/></td>
            <td>
                <c:if test="${invoice.getClientId() > 0}">
                    <a href="${pageContext.request.contextPath}/Controller?command=CLIENTS&do=get&id=${invoice.getClientId()}">
                        <c:out value="${invoice.getClient().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td align="center"><c:out value="${invoice.getTotal()}"/></td>
            <td align="center"><c:out value="${invoice.isPaid()}"/></td>
            <td align="center"><c:out value="${invoice.getDescription()}"/></td>
            <td align="center">
                <form action="${pageContext.request.contextPath}/Controller" method="post">
                    <INPUT type="hidden" name="command" value="INVOICES"/>
                    <INPUT type="hidden" name="id" value="${invoice.getId()}"/>
                    <INPUT type="hidden" name="do" value="get"/>
                    <INPUT type="hidden" name="id" value="${invoice.getId()}"/>
                    <INPUT type="submit" value="<fmt:message key="EDIT" bundle="${bundle}"/>">
                </form>
            </td>
            <td align="center">
                <form action="${pageContext.request.contextPath}/Controller" method="post">
                    <INPUT type="hidden" name="command" value="INVOICES"/>
                    <INPUT type="hidden" name="do" value="delete"/>
                    <INPUT type="hidden" name="id" value="${invoice.getId()}"/>
                    <INPUT type="submit" value="<fmt:message key="DELETE" bundle="${bundle}"/>">
                </form>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="7">
            <form action="/jsp/invoice.jsp" method="get">
                <INPUT type="submit" value="<fmt:message key="createNew" bundle="${bundle}"/>">
            </form>
        </td>
    </tr>
</table>
<br>
</body>
</html>
