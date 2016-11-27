<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Title</title>
    <style>
        table,th,td {
            border: 1px solid #333;
        }
    </style>
</head>
<body>
<body>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="localization/translate" var="bundle" />
<form action="./Controller" method="post">
<input type="hidden" name="command" value="LOGOUT" /> <input
type="submit" value="<fmt:message key="LogOut" bundle="${bundle}" />" />
</form>
<div align="center">
<h2>
<m:show name="${client.getLogin()}">
</m:show>

</h2>
<p>
<fmt:message key="TheListOfAvailableOperations" bundle="${bundle}" />
</p>
<table cellspacing="8">
<tr>
<td align="center"><a
href="./Controller?command=SHOW_ALL_APPLICATIONS"><fmt:message key="ShowAllApplications" bundle="${bundle}" />
</a></td>
</tr>
<tr>
<td align="center"><a
href="./Controller?command=SHOW_ALL_RESERVATIONS"><fmt:message key="ShowAllReservations" bundle="${bundle}" />
</a></td>
</tr>
<tr>
<td align="center"><a
href="./Controller?command=SHOW_ALL_APARTMENTS"><fmt:message
key="ShowAllApartments" bundle="${bundle}" /></a></td>
</tr>
<tr>
<td align="center"><a
href="./Controller?command=SHOW_ALL_CLIENTS"><fmt:message
key="ShowAllClients" bundle="${bundle}" />
</a></td>
</tr>
</table>

</div>

</body>
</html>
