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
    <INPUT type="hidden" name="command" value="CARREQUESTS"/>
    <INPUT type="hidden" name="do" value="update"/>

    <table class="edit">
        <tr>
            <th><fmt:message key="field" bundle="${bundle}"/></th>
            <th><fmt:message key="value" bundle="${bundle}"/></th>
        </tr>
        <%@include file="includes/fields/id.jspf" %>
        <%@include file="includes/fields/car.jspf" %>
        <%@include file="includes/fields/client.jspf" %>
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
            <td><fmt:message key="status" bundle="${bundle}"/></td>
            <td><INPUT type=text name="status"
                       value="${empty entity ? '' : entity.getStatus()}"/></td>
        </tr>
        <%@include file="includes/fields/invoice.jspf" %>
        <tr>
            <td colspan="2">
                <INPUT type="submit" value="${empty entity ? 'Create' : 'Save'}">
            </td>
        </tr>
    </table>
</form>


</body>
</html>
