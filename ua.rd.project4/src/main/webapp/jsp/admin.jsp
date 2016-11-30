<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="localization/messages" var="bundle"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/style.css">
</head>
<body>
<%@include file="includes/header.jspf" %>
<table>
    <tr>
        <td>
            <div>Cars</div>
            <table>
                <tr>
                    <th>In Box</th>
                    <th>Out</th>
                </tr>
                <tr>
                    <td>
                        <table>
                            <c:forEach var="car" items="${listCarsOut}">
                                <tr>
                                    <td><c:out value="${car.getId()}"/></td>
                                    <td><c:out value="${car.getModel()}"/></td>
                                    <td><c:out value="${car.getColor()}"/></td>
                                    <td><c:out value="${car.getRegistrationNumber()}"/></td>
                                    <td>
                                        <form action="/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARS"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
                                            <INPUT type="hidden" name="do" value="get"/>
                                            <INPUT type="submit" value="Info">
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                                            <INPUT type="hidden" name="do" value="new"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
                                            <INPUT type="submit" value="New request">
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARFLOWS"/>
                                            <INPUT type="hidden" name="do" value="new"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
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
                                    <td><a href="/Controller?command=CARS&do=get&id=${car.getId()}">
                                        <c:out value="${car.toString()}"/>
                                    </a></td>
                                    <td><c:out value="${car.getRegistrationNumber()}"/></td>
                                    <td>
                                        <form action="/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARFLOWS"/>
                                            <INPUT type="hidden" name="do" value="new"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
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
                    <th>CarRequests</th>
                    <th>CarFlows</th>
                </tr>
                <tr>
                    <td>
                        <table>
                            <c:forEach var="car" items="${listCarRequests}">
                                <tr>
                                    <td><c:out value="${car.getId()}"/></td>
                                    <td><c:out value="${car.getModel()}"/></td>
                                    <td><c:out value="${car.getColor()}"/></td>
                                    <td><c:out value="${car.getRegistrationNumber()}"/></td>
                                    <td>
                                        <form action="/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARS"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
                                            <INPUT type="hidden" name="do" value="get"/>
                                            <INPUT type="submit" value="Info">
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                                            <INPUT type="hidden" name="do" value="new"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
                                            <INPUT type="submit" value="New request">
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARFLOWS"/>
                                            <INPUT type="hidden" name="do" value="new"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
                                            <INPUT type="submit" value="Register out">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                    <td>
                        <table>
                            <c:forEach var="car" items="${listCarFlows}">
                                <tr>
                                    <td><c:out value="${car.getId()}"/></td>
                                    <td><c:out value="${car.getModel()}"/></td>
                                    <td><c:out value="${car.getColor()}"/></td>
                                    <td><c:out value="${car.getRegistrationNumber()}"/></td>
                                    <td>
                                        <form action="/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARS"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
                                            <INPUT type="hidden" name="do" value="get"/>
                                            <INPUT type="submit" value="Info">
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARREQUESTS"/>
                                            <INPUT type="hidden" name="do" value="new"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
                                            <INPUT type="submit" value="New request">
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/Controller" method="post">
                                            <INPUT type="hidden" name="command" value="CARFLOWS"/>
                                            <INPUT type="hidden" name="do" value="new"/>
                                            <INPUT type="hidden" name="id" value="${car.getId()}"/>
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
</table>

Cars in box (button -> out, button request)
Cars out (button -> comeback)
CarRequests (not ) (possible/approve/reject - paid/not paid)
Possible conflicts for car-requests
listLastCarFlows
</body>
</html>

<td><c:out value="${carRequest.getId()}"/></td>
<td>
    <c:if test="${carRequest.getCarId() > 0}">
        <a href="/Controller?command=CARS&do=get&id=${carRequest.getCarId()}">
            <c:out value="${carRequest.getCar().toString()}"/>
        </a>
    </c:if>
</td>
<td><c:out value="${carRequest.getDateFrom()}"/></td>
<td><c:out value="${carRequest.getDateTo()}"/></td>
<td><c:out value="${carRequest.getTotalCost()}"/></td>
<td>
    <c:if test="${carRequest.isApproved()}">
        <b>Approved</b>
</c:if>
</td>
<td>
    <c:if test="${carRequest.getInvoiceId() > 0}">
    <c:if test="${carRequest.getInvoice().isPaid()}">
        <a href="/Controller?command=INVOICES&do=get&id=${carRequest.getInvoiceId()}">
            <b>Paid</b>
        </a>
    </c:if>
</td>