<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="localization/messages" var="bundle"/>
<html>
<head>
    <title>User add/update</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/print/style.css">
</head>
<body>
<table width="90%" style="align-self: center; border-collapse:collapse">
    <tr class="border_bottom">
        <td style="text-align: left">
            <h2 style="margin-bottom: 0">CARS RENT Inc.</h2>
            <a href="${pageContext.request.contextPath}/index.php">www.carrent.com.ua</a> <br>
            Tel: +123(456)888-99-999<br>
            Fax: +123(456)555-55-555<br>
            Address: Somewhere Far Beyond<br>
        </td>
        <td style="text-align: right;">
            <h1 style="color: #c9c9c9">INVOICE</h1>
            Date: <fmt:formatDate type="date"
                                  value="${entity.getDateCreated()}"/> <br>
            Invoice # <c:out value="${entity.getId()}"/><br>
            Customer ID: <c:out value="${entity.getClientId()}"/><br>
            Terms: Prepayment
        </td>
    </tr>
    <tr>
        <td>
            <table>
                <tr>
                    <th colspan="2">To:</th>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td><c:out value="${entity.getClient().getFirstName()}"/></td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td><c:out value="${entity.getClient().getLastName()}"/></td>
                </tr>
                <tr>
                    <td>Address:</td>
                    <td><c:out value="${entity.getClient().getAddress()}"/></td>
                </tr>
                <tr>
                    <td>Phone:</td>
                    <td><c:out value="${entity.getClient().getTelephone()}"/></td>
                </tr>
                <tr>
                    <td>email:</td>
                    <td><c:out value="${entity.getClient().getEmail()}"/></td>
                </tr>
            </table>
    </tr>
    <tr>
        <td>

        </td>
    </tr>
</table>

<table width="90%" style="border-collapse: collapse; border-color: black">
    <tr class="border_bottom">
        <th width="70%">service</th>
        <th width="15%">terms</th>
        <th>total</th>
    </tr>
    <tr class="border_bottom">
        <td><c:out value="${entity.getDescription()}"/></td>
        <td>prepayment</td>
        <td><c:out value="${entity.getTotal()}"/></td>
    </tr>
    <tr><td> </td></tr>
    <tr><td></td><td style="background-color: #4CAF50;">total</td><td><c:out value="${entity.getTotal()}"/></td></tr>
    <tr><td></td><td colspan="2"><div style="font-size: smaller">Make all checks payable to CARRENT Inc.</div></td></tr>
</table>


<%--<form action="${pageContext.request.contextPath}/Controller" method="post">--%>
<%--<INPUT type="hidden" name="command" value="INVOICES"/>--%>
<%--<INPUT type="hidden" name="do" value="update"/>--%>
<%--<table class="edit">--%>
<%--<tr>--%>
<%--<th>Field</th>--%>
<%--<th>Value</th>--%>
<%--</tr>--%>
<%--<%@include file="includes/fields/id.jspf" %>--%>
<%--<%@include file="includes/fields/client.jspf" %>--%>
<%--<tr>--%>
<%--<td><fmt:message key="paid" bundle="${bundle}"/></td>--%>
<%--<td><INPUT type=text name="paid"--%>
<%--value="${empty entity ? '' : entity.isPaid()}"/></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td><fmt:message key="total" bundle="${bundle}"/></td>--%>
<%--<td><INPUT type=text name="total"--%>
<%--value="${empty entity ? '' : entity.getTotal()}"/></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td><fmt:message key="description" bundle="${bundle}"/></td>--%>
<%--<td><INPUT type=text name="description"--%>
<%--value="${empty entity ? '' : entity.getDescription()}"/></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td colspan="2">--%>
<%--<INPUT type="submit" value="${empty entity ? 'Create' : 'Save'}">--%>
<%--</td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--</form>--%>


</body>
</html>