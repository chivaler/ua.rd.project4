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
    <INPUT type="hidden" name="command" value="CARFLOWS"/>
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
            <td><fmt:message key="car" bundle="${bundle}"/></td>
            <td><INPUT type=text name="car"
                       value="${empty entity ? '' : entity.getCarId()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="client" bundle="${bundle}"/></td>
            <td><INPUT type=text name="client"
                       value="${empty entity ? '' : entity.getClientId()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="dateFrom" bundle="${bundle}"/></td>
            <td><INPUT type="date" name="dateFrom"
                       value="${empty entity ? '' : entity.getDateFrom()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="dateTo" bundle="${bundle}"/></td>
            <td><INPUT type="date" name="dateTo"
                       value="${empty entity ? '' : entity.getDateTo()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="totalCost" bundle="${bundle}"/></td>
            <td><INPUT type=text name="totalCost"
                       value="${empty entity ? '' : entity.getTotalCost()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="approved" bundle="${bundle}"/></td>
            <td><INPUT type=text name="approved"
                       value="${empty entity ? '' : entity.isApproved()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="invoice" bundle="${bundle}"/></td>
            <td><INPUT type=text name="invoice"
                       value="${empty entity ? '' : entity.getInvoiceId()}"/></td>
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
