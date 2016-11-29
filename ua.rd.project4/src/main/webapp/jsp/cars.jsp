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
    <fmt:message key="CarList" bundle="${bundle}"/>
</h1>
<table class="list">
    <tr>
        <th>ID</th>
        <th><fmt:message key="model" bundle="${bundle}"/></th>
        <th><fmt:message key="color" bundle="${bundle}"/></th>
        <th><fmt:message key="carType" bundle="${bundle}"/></th>
        <th><fmt:message key="registrationNumber" bundle="${bundle}"/></th>
        <th><fmt:message key="description" bundle="${bundle}"/></th>
        <th><fmt:message key="price" bundle="${bundle}"/></th>
        <th><fmt:message key="rentPricePerDay" bundle="${bundle}"/></th>
        <th><fmt:message key="EDIT" bundle="${bundle}"/></th>
        <th><fmt:message key="DELETE" bundle="${bundle}"/></th>
    </tr>
    <c:forEach var="car" items="${entities}">
        <tr>
            <td><c:out value="${car.getId()}"/></td>
            <td><c:out value="${car.getModel()}"/></td>
            <td><c:out value="${car.getColor()}"/></td>
            <td><c:out value="${car.getCarType()}"/></td>
            <td><c:out value="${car.getRegistrationNumber()}"/></td>
            <td><c:out value="${car.getDescription()}"/></td>
            <td><c:out value="${car.getPrice()}"/></td>
            <td><c:out value="${car.getRentPricePerDay()}"/></td>
            <td>
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="CARS"/>
                    <INPUT type="hidden" name="id" value="${car.getId()}"/>
                    <INPUT type="hidden" name="do" value="get"/>
                    <INPUT type="hidden" name="id" value="${car.getId()}"/>
                    <INPUT type="submit" value="Edit">
                </form>
            </td>
            <td>
                <form action="/Controller" method="post">
                    <INPUT type="hidden" name="command" value="CARS"/>
                    <INPUT type="hidden" name="do" value="delete"/>
                    <INPUT type="hidden" name="id" value="${car.getId()}"/>
                    <INPUT type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
</body>
</html>
