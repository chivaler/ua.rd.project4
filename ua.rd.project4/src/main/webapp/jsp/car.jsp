<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/carTag.tld" prefix="carCalendar"%>
<html>
<head>
    <title>User add/update</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<%@include file="includes/header.jspf" %>
<form action="${pageContext.request.contextPath}/Controller" method="post">
    <INPUT type="hidden" name="command" value="CARS"/>
    <INPUT type="hidden" name="do" value="update"/>
    <table class="edit">
        <tr>
            <th><fmt:message key="field" bundle="${bundle}"/></th>
            <th><fmt:message key="value" bundle="${bundle}"/></th>
        </tr>
        <c:if test="${not empty entity}">
            <c:if test="${not empty entity.getId()}">
                <tr>
                    <td><fmt:message key="id" bundle="${bundle}"/></td>
                    <td><INPUT type=text class="readonly" readonly="readonly" name="id"
                               value="${empty entity ? '' : entity.getId()}"/></td>
                </tr>
            </c:if>
        </c:if>
        <tr>
            <td><fmt:message key="model" bundle="${bundle}"/></td>
            <td><INPUT type=text name="model"
                       value="${empty entity ? '' : entity.getModel()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="color" bundle="${bundle}"/></td>
            <td><INPUT type=text name="color"
                       value="${empty entity ? '' : entity.getColor()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="carType" bundle="${bundle}"/></td>
            <td><select name="carType">
                <option disabled>Select CarType</option>
                <option value="PICKUP"  ${'PICKUP' == entity.getCarType() ? 'selected="selected"' : ''}>PICKUP</option>
                <option value="SEDAN" ${'SEDAN' == (empty entity ? '' : entity.getCarType()) ? 'selected="selected"' : ''}>SEDAN</option>
                <option value="SPORT" ${'SPORT' == (empty entity ? '' : entity.getCarType()) ? 'selected="selected"' : ''}>SPORT</option>
                <option value="LIMO" ${'LIMO' == (empty entity ? '' : entity.getCarType()) ? 'selected="selected"' : ''}>LIMO</option>
                <option value="MINI" ${'MINI' == (empty entity ? '' : entity.getCarType()) ? 'selected="selected"' : ''}>MINI</option>
            </select>
        </tr>
        <tr>
            <td><fmt:message key="description" bundle="${bundle}"/></td>
            <td><INPUT type=text name="description"
                       value="${empty entity ? '' : entity.getDescription()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="registrationNumber" bundle="${bundle}"/></td>
            <td><INPUT type=text name="registrationNumber"
                       value="${empty entity ? '' : entity.getRegistrationNumber()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="price" bundle="${bundle}"/></td>
            <td><INPUT type=text name="price"
                       value="${empty entity ? '' : entity.getPrice()}"/></td>
        </tr>
        <tr>
            <td><fmt:message key="rentPricePerDay" bundle="${bundle}"/></td>
            <td><INPUT type=text name="rentPricePerDay"
                       value="${empty entity ? '' : entity.getRentPricePerDay()}"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <INPUT type="submit" value="${empty entity ? 'Create' : 'Save'}">
            </td>
        </tr>
    </table>
</form>
<c:if test="${not empty entity}">
    <carCalendar:customattr carId="${entity.getId()}"/>
</c:if>



</body>
</html>
