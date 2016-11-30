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
    <fmt:message key="CarFlows" bundle="${bundle}"/>
</h1>
<table class="list">
    <tr>
        <th>ID</th>
        <th><fmt:message key="car" bundle="${bundle}"/></th>
        <th><fmt:message key="carFlowType" bundle="${bundle}"/></th>
        <th><fmt:message key="client" bundle="${bundle}"/></th>
        <th><fmt:message key="carRequest" bundle="${bundle}"/></th>
        <th><fmt:message key="responsiblePerson" bundle="${bundle}"/></th>
        <th><fmt:message key="invoice" bundle="${bundle}"/></th>
        <th><fmt:message key="supplement" bundle="${bundle}"/></th>
        <th><fmt:message key="EDIT" bundle="${bundle}"/></th>
        <th><fmt:message key="DELETE" bundle="${bundle}"/></th>
    </tr>
    <c:forEach var="carFlow" items="${entities}">
        <tr>
            <td><c:out value="${carFlow.getId()}"/></td>
            <td>
                <c:if test="${carFlow.getCarId() > 0}">
                    <a href="/Controller?command=CARS&do=get&id=${carFlow.getCarId()}">
                        <c:out value="${carFlow.getCar().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td><c:out value="${carFlow.getCarFlowType()}"/></td>
            <td>
                <c:if test="${carFlow.getClientId() > 0}">
                    <a href="/Controller?command=CLIENTS&do=get&id=${carFlow.getClientId()}">
                        <c:out value="${carFlow.getClient().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td>
                <c:if test="${carFlow.getCarRequestId() > 0}">
                    <a href="/Controller?command=CARREQUESTS&do=get&id=${carFlow.getCarRequestId()}">
                        <c:out value="${carFlow.getCarRequest().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td>
                <c:if test="${carFlow.getResponsiblePersonId() > 0}">
                    <a href="/Controller?command=USERS&do=get&id=${carFlow.getResponsiblePersonId()}">
                        <c:out value="${carFlow.getResponsiblePerson().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td>
                <c:if test="${carFlow.getInvoiceId() > 0}">
                    <a href="/Controller?command=INVOICES&do=get&id=${carFlow.getInvoiceId()}">
                        <c:out value="${carFlow.getInvoice().toString()}"/>
                    </a>
                </c:if>
            </td>
            <td><c:out value="${carFlow.getSupplement()}"/></td>
            <td>
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="CARFLOWS"/>
                    <INPUT type="hidden" name="id" value="${carFlow.getId()}"/>
                    <INPUT type="hidden" name="do" value="get"/>
                    <INPUT type="hidden" name="id" value="${carFlow.getId()}"/>
                    <INPUT type="submit" value="Edit">
                </form>
            </td>
            <td>
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="CARFLOWS"/>
                    <INPUT type="hidden" name="do" value="delete"/>
                    <INPUT type="hidden" name="id" value="${carFlow.getId()}"/>
                    <INPUT type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="10">
            <form action="/jsp/car_flow.jsp" method="post">
                <INPUT type="submit" value="Create New">
            </form>
        </td>
    </tr>
</table>
<br>
</body>
</html>
