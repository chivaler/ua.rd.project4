<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%--<%@ taglib prefix="m" uri="/WEB-INF/mytld.tld"%>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--<% response.sendRedirect( "Controller?command=CLIENTS" ); %>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin Home Page</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/Controller?command=CARS">CARS</a>
<a href="${pageContext.request.contextPath}/Controller?command=USERS">USERS</a>
<a href="${pageContext.request.contextPath}/Controller?command=INVOICES">INVOICES</a>
<a href="${pageContext.request.contextPath}/Controller?command=CLIENTS">CLIENTS</a>
<a href="${pageContext.request.contextPath}/Controller?command=CARFLOWS">CARFLOWS</a>
<a href="${pageContext.request.contextPath}/Controller?command=CARREQUESTS">CARREQUESTS</a>
</body>
</html>