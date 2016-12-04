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
    <INPUT type="hidden" name="command" value="CARFLOWS"/>
    <INPUT type="hidden" name="do" value="update"/>

    <table class="edit">
        <tr>
            <th>Field</th>
            <th>Value</th>
        </tr>
        <%@include file="includes/fields/id.jspf" %>
        <%@include file="includes/fields/car.jspf" %>
        <%@include file="includes/fields/car_flow_type.jspf" %>
        <%@include file="includes/fields/car_request.jspf" %>
        <%@include file="includes/fields/responsible_user.jspf" %>
        <%@include file="includes/fields/invoice.jspf" %>
        <tr>
            <td><fmt:message key="supplement" bundle="${bundle}"/></td>
            <td><INPUT type=text name="supplement"
                       value="${empty entity ? '' : entity.getSupplement()}"/></td>
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
