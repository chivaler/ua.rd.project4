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
            <div>Cars</div>
            <table>
                <tr>
                    <th width="50%">In Box</th>
                    <th>Out</th>
                </tr>
                <tr>
                    <td>
                        <table>
                            <c:forEach var="car" items="${listCarsIn}">
                                <tr>
                                    <td><c:out value="${car.getId()}"/></td>
                                    <td><c:out value="${car.getModel()}"/></td>
                                    <td><c:out value="${car.getColor()}"/></td>
                                    <td><c:out value="${car.getRegistrationNumber()}"/></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARS"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
                                            <INPUT type="hidden" name="do" value="get"/>
                                            <INPUT type="submit" value="Info">
                                        </form>
                                    </td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                                            <INPUT type="hidden" name="do" value="new"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
                                            <INPUT type="submit" value="New request">
                                        </form>
                                    </td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/Controller" method="get">
                                            <INPUT type="hidden" name="command" value="CARFLOWS"/>
                                            <INPUT type="hidden" name="do" value="new"/>
                                            <INPUT type="hidden" name="carId" value="${car.getId()}"/>
                                            <INPUT type="hidden" name="carFlowType" value="OUT"/>
                                            <INPUT type="hidden" name="responsibleUser" value="${sessionScope.user.getId()}">
                                            <INPUT type="submit" value="Register out">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                    <td>
                        <table>
                            <c:forEach var="car" items="${listCarsOut}">
                                <tr>
                                    <td><a href="${pageContext.request.contextPath}/Controller?command=CARS&do=get&id=${car.getId()}">
                                        <c:out value="${car.toString()}"/>
                                    </a></td>
                                    <td><c:out value="${car.getRegistrationNumber()}"/></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/Controller" method="get">
                                            <INPUT type="hidden" name="command" value="CARFLOWS"/>
                                            <INPUT type="hidden" name="do" value="new"/>
                                            <INPUT type="hidden" name="carId" value="${car.getId()}"/>
                                            <INPUT type="hidden" name="carFlowType" value="IN"/>
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
    <tr>
        <td>
            <table>
                <tr>
                    <th width="50%">CarRequests</th>
                    <th>CarFlows</th>
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <th>car</th>
                                <th>client</th>
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
                                    <td><a href="${pageContext.request.contextPath}/Controller?command=CLIENTS&do=get&id=${request.clientid}">
                                        <c:out value="${request.clientStr}"/></a></td>
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
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <th>ID</th>
                                <th>Car</th>
                                <th>Direction</th>
                                <th></th>
                            </tr>
                            <c:forEach var="carFlow" items="${listCarFlows}">
                                <tr>
                                    <td><c:out value="${carFlow.getId()}"/></td>
                                    <td><a href="${pageContext.request.contextPath}/Controller?command=CARS&do=get&id=${carFlow.getCarId()}">
                                        <c:out value="${carFlow.getCar().toString()}"/></a>
                                    <td><a href="${pageContext.request.contextPath}/Controller?command=CARFLOWS&do=get&id=${carFlow.getId()}">
                                        <c:out value="${carFlow.getCarFlowType()}"/></a>
                                    <td><c:out value="${carFlow.getDateCreated()}"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>

Cars in box (button -> out, button request)
Cars out (button -> comeback)
CarRequests (not ) (possible/approve/reject/view - paid/not paid)
Possible conflicts for car-requests
listLastCarFlows
</body>
</html>