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
    <INPUT type="hidden" name="command" value="INVOICES"/>
    <INPUT type="hidden" name="do" value="update"/>
    <table class="edit">
        <tr>
            <th><fmt:message key="field" bundle="${bundle}"/></th>
            <th><fmt:message key="value" bundle="${bundle}"/></th>
        </tr>
        <%@include file="includes/fields/id.jspf" %>
        <%@include file="includes/fields/client.jspf" %>
        <tr>
            <td><fmt:message key="paid" bundle="${bundle}"/></td>
            <td><INPUT type="checkbox" name="paid" ${empty entity ? '' : entity.isPaid()? 'checked': ''}/></td>
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
<c:if test="${not empty entity}">
    <div align="center">
        <form action="${pageContext.request.contextPath}/Controller" method="post">
            <INPUT type="hidden" name="command" value="PRINTINVOICE"/>
            <INPUT type="hidden" name="id" value="${entity.getId()}"/>
            <INPUT type="submit" value="Print">
        </form>
    </div>
</c:if>



</body>
</html>
