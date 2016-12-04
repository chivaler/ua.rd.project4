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
    <INPUT type="hidden" name="command" value="INVOICES"/>
    <INPUT type="hidden" name="do" value="update"/>
    <table class="edit">
        <tr>
            <th>Field</th>
            <th>Value</th>
        </tr>
        <%@include file="includes/fields/id.jspf" %>
        <%@include file="includes/fields/client.jspf" %>
        <tr>
            <td><fmt:message key="paid" bundle="${bundle}"/></td>
            <td><INPUT type=text name="paid"
                       value="${empty entity ? '' : entity.isPaid()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="total" bundle="${bundle}"/></td>
            <td><INPUT type=text name="total"
                       value="${empty entity ? '' : entity.getTotal()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="description" bundle="${bundle}"/></td>
            <td><INPUT type=text name="description"
                       value="${empty entity ? '' : entity.getDescription()}"/></td>
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
