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
<table>
    <tr>
        <td>
            <table>
                <tr>
                    <td width="50%">
                        <table>
                            <caption>Cars In Box</caption>
                            <tr>
                                <th>car</th>
                                <th>registrationNumber</th>
                                <th>new carRequest</th>
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
                                            <INPUT type="submit" value="New request">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                    <td>
                        <table>
                            <caption>Cars Out</caption>
                            <tr>
                                <th>car</th>
                                <th>registrationNumber</th>
                                <th>Register return</th>
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
                                            <INPUT type="submit" value="Register in">
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
            <caption>new CarRequests</caption>
            <tr>
                <th>car</th>
                <th>client</th>
                <th>dateFrom</th>
                <th>dateTo</th>
                <th>status</th>
                <th>available</th>
                <th>INFO</th>
                <th>APPROVE</th>
                <th>REJECT</th>
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
                    <td><c:out value="${request.available}"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                            <INPUT type="hidden" name="id" value="${request.id}"/>
                            <INPUT type="hidden" name="do" value="get"/>
                            <INPUT type="submit" value="Info">
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                            <INPUT type="hidden" name="do" value="approve"/>
                            <INPUT type="hidden" name="id" value="${request.id}"/>
                            <INPUT type="submit" value="Approve">
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                            <INPUT type="hidden" name="do" value="reject"/>
                            <INPUT type="hidden" name="id" value="${request.id}"/>
                            <INPUT type="submit" value="Reject">
                        </form>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                            <INPUT type="hidden" name="command" value="CAR_OUT"/>
                            <INPUT type="hidden" name="do" value="reject"/>
                            <INPUT type="hidden" name="carRequest" value="${request.id}"/>
                            <INPUT type="submit" value="CheckIn Car Out">
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
            <caption>Last CarFlows</caption>
            <tr>
                <th>ID</th>
                <th>Car</th>
                <th>Direction</th>
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