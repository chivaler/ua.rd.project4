<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="localization/messages" var="bundle"/>
<html>
<head>
    <title>User add/update</title>
    <link rel="stylesheet" type="text/css" href="/style.css">
</head>
<body>
<c:if test="${not empty result}">
    <div id="result"><c:out value="${result}"/></div>
</c:if>
<c:if test="${not empty error}">
    <div id="error"><c:out value="${error}"/></div>
</c:if>

<form action="/Controller" method="post">
    <INPUT type="hidden" name="command" value="USERS"/>
    <INPUT type="hidden" name="do" value="update"/>
    <table class="edit">
        <c:if test="${not empty entity}">
            <c:if test="${not empty entity.getId()}">
                <tr>
                    <td>ID</td>
                    <td><INPUT type=text class="readonly" readonly="readonly" name="id" value="${empty entity ? '' : entity.getId()}"/>
                    </td>
                </tr>
            </c:if>
        </c:if>
        <tr>
            <td>isAdmin</td>
            <td><INPUT type=checkbox name="isAdmin" ${empty entity ? '' : entity.isAdmin()? 'checked': ''}/></td>
        </tr>
        <tr>
            <td>login</td>
            <td><INPUT type=text name="login" value="${empty entity ? '' : entity.getLogin()}"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><INPUT type=password name="pass" value=""/></td>
        </tr>
        <c:if test="${not empty entity}">
            <tr>
                <td>HashedPassword</td>
                <td><INPUT type=text class="readonly" readonly="readonly" name="password"
                           value="${empty entity ? '' : entity.getPassword()}"/>
                </td>
            </tr>
        </c:if>
        <tr>
            <td>idClient</td>
            <td><INPUT type=text name="client"
                       value="${empty entity ? '' : entity.getClient() != null ? entity.getClient().getId() : ''}"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><INPUT type="submit" value="Save">
        </tr>
    </table>
</form>


</body>
</html>
