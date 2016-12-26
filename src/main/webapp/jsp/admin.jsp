<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="localization/messages" var="bundle"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<%@include file="includes/header.jspf" %>
<table>
    <tr>
        <td>
            <table>
                <tr>
                    <td width="50%">
                        <table>
                            <caption><fmt:message key="CarsInBox" bundle="${bundle}"/></caption>
                            <tr>
                                <th><fmt:message key="car" bundle="${bundle}"/></th>
                                <th><fmt:message key="registrationNumber" bundle="${bundle}"/></th>
                                <th><fmt:message key="newСarRequest" bundle="${bundle}"/></th>
                            </tr>
                            <c:forEach var="car" items="${listCarsIn}">
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/Controller?command=CARS&do=get&id=${car.getId()}">
                                            <c:out value="${car.toString()}"/>
                                        </a></td>
                                    <td><c:out value="${car.getRegistrationNumber()}"/></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                                            <INPUT type="hidden" name="do" value="new"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
                                            <INPUT type="submit"
                                                   value="<fmt:message key="newRequest" bundle="${bundle}"/>">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                    <td>
                        <table>
                            <caption><fmt:message key="carsOut" bundle="${bundle}"/></caption>
                            <tr>
                                <th><fmt:message key="car" bundle="${bundle}"/></th>
                                <th><fmt:message key="registrationNumber" bundle="${bundle}"/></th>
                                <th><fmt:message key="registerReturn" bundle="${bundle}"/></th>
                            </tr>
                            <c:forEach var="car" items="${listCarsOut}">
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/Controller?command=CARS&do=get&id=${car.getId()}">
                                            <c:out value="${car.toString()}"/>
                                        </a></td>
                                    <td><c:out value="${car.getRegistrationNumber()}"/></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/Controller" method="get">
                                            <INPUT type="hidden" name="command" value="CAR_IN"/>
                                            <INPUT type="hidden" name="car" value="${car.getId()}"/>
                                            <INPUT type="submit"
                                                   value="<fmt:message key="registerIn" bundle="${bundle}"/>">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr><br></tr>
    <tr><br></tr>
    <tr>
        <table>
            <caption><fmt:message key="newCarRequests" bundle="${bundle}"/></caption>
            <tr>
                <th><fmt:message key="car" bundle="${bundle}"/></th>
                <th><fmt:message key="Client" bundle="${bundle}"/></th>
                <th><fmt:message key="dateFrom" bundle="${bundle}"/></th>
                <th><fmt:message key="dateTo" bundle="${bundle}"/></th>
                <th><fmt:message key="status" bundle="${bundle}"/></th>
                <th><fmt:message key="dateCreated" bundle="${bundle}"/></th>
                <th><fmt:message key="invoice" bundle="${bundle}"/></th>
                <th><fmt:message key="available" bundle="${bundle}"/></th>
                <th><fmt:message key="APPROVE" bundle="${bundle}"/></th>
                <th><fmt:message key="REJECT" bundle="${bundle}"/></th>
                <th><fmt:message key="сheckInCarOut" bundle="${bundle}"/></th>
            </tr>
            <c:forEach var="request" items="${mapsCarRequests}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/Controller?command=CARS&do=get&id=${request.carId}">
                        <c:out value="${request.carStr}"/></a></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/Controller?command=CLIENTS&do=get&id=${request.clientId}">
                            <c:out value="${request.clientStr}"/></a></td>
                    <td><c:out value="${request.dateFrom}"/></td>
                    <td><c:out value="${request.dateTo}"/></td>
                    <td><c:out value="${request.status}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/Controller?command=CARREQUESTS&do=get&id=${request.id}">
                            <c:out value="${request.dateCreated}"/></a></td>
                    <td>
                        <c:if test="${not empty request.invoiceId}">
                            <a href="${pageContext.request.contextPath}/Controller?command=INVOICES&do=get&id=${request.invoiceId}">
                                <c:out value="${request.paid=='true'?'paid':'not paid'}"/></a>
                        </c:if>
                    </td>
                    <td><c:out value="${request.available}"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                            <INPUT type="hidden" name="do" value="approve"/>
                            <INPUT type="hidden" name="id" value="${request.id}"/>
                            <INPUT type="submit"
                                   value="<fmt:message key="APPROVE" bundle="${bundle}"/>" ${request.available=='IMPOSSIBLE'?'disabled="disabled"':''} ${(request.status=='APPROVED' || request.status=='PROGRESS')?'disabled="disabled"':''}>
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                            <INPUT type="hidden" name="do" value="reject"/>
                            <INPUT type="hidden" name="id" value="${request.id}"/>
                            <INPUT type="submit"
                                   value="<fmt:message key="REJECT" bundle="${bundle}"/>" ${request.paid=='true'?'disabled="disabled"':''}>
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <INPUT type="hidden" name="command" value="CAR_OUT"/>
                            <INPUT type="hidden" name="do" value="reject"/>
                            <INPUT type="hidden" name="carRequest" value="${request.id}"/>
                            <INPUT type="submit"
                                   value="<fmt:message key="сheckInCarOut" bundle="${bundle}"/>" ${request.paid=='false'?'disabled="disabled"':''}>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </tr>
    <tr><br></tr>
    <tr><br></tr>
    <tr>
        <table>
            <caption><fmt:message key="lastCarFlows" bundle="${bundle}"/></caption>
            <tr>
                <th><fmt:message key="id" bundle="${bundle}"/></th>
                <th><fmt:message key="car" bundle="${bundle}"/></th>
                <th><fmt:message key="direction" bundle="${bundle}"/></th>
                <th></th>
            </tr>
            <c:forEach var="carFlow" items="${listCarFlows}">
                <tr>
                    <td><c:out value="${carFlow.getId()}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/Controller?command=CARS&do=get&id=${carFlow.getCarId()}">
                            <c:out value="${carFlow.getCar().toString()}"/></a>
                    <td>
                        <a href="${pageContext.request.contextPath}/Controller?command=CARFLOWS&do=get&id=${carFlow.getId()}">
                            <c:out value="${carFlow.getCarFlowType()}"/></a>
                    <td><c:out value="${carFlow.getDateCreated()}"/></td>
                </tr>
            </c:forEach>
        </table>
    </tr>
</table>
</body>
</html>