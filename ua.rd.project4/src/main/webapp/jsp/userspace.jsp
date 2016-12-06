<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="localization/messages" var="bundle"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<%@include file="includes/header.jspf" %>
<c:choose>
    <c:when test="${not empty listAvailableCars}">
        <c:set var="cars" value="${listAvailableCars}"/>
    </c:when>
    <c:otherwise>
        <c:set var="cars" value="${listCars}"/>
    </c:otherwise>
</c:choose>
<br>
<div style="text-align: center">
    <form action="${pageContext.request.contextPath}/Controller" method="post">
        <INPUT type="hidden" name="command" value="CARSAVAILABLE"/>
        <label for="dateFrom1">dateFrom</label>
        <input type="date" name="dateFrom" id="dateFrom1"/>
        <label for="dateTo1">dateTo</label>
        <input type="date" name="dateTo" id="dateTo1"/>
        <input type="submit" value="Found available cars for period"></td>
    </form>
</div>
<br>
<div>
    <form action="${pageContext.request.contextPath}/Controller" method="post">
        <INPUT type="hidden" name="command" value="CREATECARREQUEST"/>
        <table>
            <c:choose>
                <c:when test="${not empty listAvailableCars}">
                    <caption>FoundCars</caption>
                </c:when>
                <c:otherwise>
                    <caption>Place New Request For Car</caption>
                </c:otherwise>
            </c:choose>
            <tr>
                <td><label for="dateFrom">dateFrom</label></td>
                <td><input type="date" name="dateFrom" id="dateFrom"/></td>
            </tr>
            <tr>
                <td><label for="dateTo">dateTo</label></td>
                <td><input type="date" name="dateTo" id="dateTo"/></td>
            </tr>
            <c:forEach var="car" items="${cars}">
                <tr>
                    <td>
                        <label for="car${car.getId()}"><a
                                href="${pageContext.request.contextPath}/Controller?command=CARS&do=get&id=${car.getId()}">${car.toString()}</a>
                            <b><span
                                    style="color: chocolate">per day: ${car.getRentPricePerDay()}</span></b></label>
                    </td>
                    <td><input type="radio" name="car" value="${car.getId()}" id="car${car.getId()}"></td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="2">
                    <c:choose>
                        <c:when test="${not empty user}">
                            <INPUT type="submit" value="New Request">
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/jsp/register.jsp">Register</a> or
                            <a href="${pageContext.request.contextPath}/jsp/login.jsp">Login</a>
                            to place new request
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </form>
</div>
<br>
<c:if test="${not empty user}">
    <table class="list">
        <caption>My Requests</caption>
        <tr>
            <th>ID</th>
            <th><fmt:message key="car" bundle="${bundle}"/></th>
            <th><fmt:message key="client" bundle="${bundle}"/></th>
            <th><fmt:message key="dateFrom" bundle="${bundle}"/></th>
            <th><fmt:message key="dateTo" bundle="${bundle}"/></th>
            <th><fmt:message key="totalCost" bundle="${bundle}"/></th>
            <th><fmt:message key="status" bundle="${bundle}"/></th>
            <th><fmt:message key="invoice" bundle="${bundle}"/></th>
            <th><fmt:message key="DELETE" bundle="${bundle}"/></th>
        </tr>
        <c:forEach var="carRequest" items="${listCarRequests}">
            <tr>
                <td align="center"><c:out value="${carRequest.getId()}"/></td>
                <td>
                    <c:if test="${carRequest.getCarId() > 0}">
                        <a href="${pageContext.request.contextPath}/Controller?command=CARS&do=get&id=${carRequest.getCarId()}">
                            <c:out value="${carRequest.getCar().toString()}"/>
                        </a>
                    </c:if>
                </td>
                <td>
                    <c:if test="${carRequest.getClientId() > 0}">
                        <a href="${pageContext.request.contextPath}/Controller?command=CLIENTS&do=get&id=${carRequest.getClientId()}">
                            <c:out value="${carRequest.getClient().toString()}"/>
                        </a>
                    </c:if>
                </td>
                <td align="center"><c:out value="${carRequest.getDateFrom()}"/></td>
                <td align="center"><c:out value="${carRequest.getDateTo()}"/></td>
                <td align="center"><c:out value="${carRequest.getTotalCost()}"/></td>
                <td align="center"><c:out value="${carRequest.getStatus()}"/></td>
                <td>
                    <c:if test="${carRequest.getInvoiceId() > 0}">
                        <a href="${pageContext.request.contextPath}/Controller?command=INVOICES&do=get&id=${carRequest.getInvoiceId()}">
                            <c:out value="${carRequest.getInvoice().toString()}"/>
                        </a>
                    </c:if>
                </td>
                <td align="center">
                    <form action="${pageContext.request.contextPath}/Controller" method="post">
                        <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                        <INPUT type="hidden" name="do" value="delete"/>
                        <INPUT type="hidden" name="id" value="${carRequest.getId()}"/>
                        <INPUT type="submit" value="Delete">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <table class="list">
        <tr>
            <th>ID</th>
            <th><fmt:message key="client" bundle="${bundle}"/></th>
            <th><fmt:message key="total" bundle="${bundle}"/></th>
            <th><fmt:message key="paid" bundle="${bundle}"/></th>
            <th><fmt:message key="description" bundle="${bundle}"/></th>
        </tr>
        <c:forEach var="invoice" items="${listInvoices}">
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
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>