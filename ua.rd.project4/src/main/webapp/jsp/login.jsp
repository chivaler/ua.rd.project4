<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Authorization</title>
</head>
<body>
<c:set var="lang" value="${empty lang ? 'uk_UA' : sessionScope.lang}"
       scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization/messages" var="bundle"/>


<form action="${pageContext.request.contextPath}/Controller" method="post">
    <input type="hidden" name="command" value="LOCALE"/>
    <select name="language">
        <option selected value="ENG">ENG</option>
        <option selected value="UKR">UKR</option>
    </select>
    <table>
        <tr>
            <td colspan=2 align="center"><br> <input type="submit"
                                                     value=<fmt:message key="chooseLanguage" bundle="${bundle}"/>></td>
        </tr>
    </table>
</form>
<div align="center">
    <h2>
        <fmt:message key="authorizationForm" bundle="${bundle}"/>
    </h2>

    <c:if test="${requestScope.error eq true}">
        <font color="red"><fmt:message key="WrongLoginOrPassword"
                                       bundle="${bundle}"/> </font>
    </c:if>

    <p>
        <fmt:message key="enterLoginPassword" bundle="${bundle}"/>
        <br>
    </p>
    <form action="${pageContext.request.contextPath}/Controller" method="post">
        <INPUT type="hidden" name="command" value="LOGIN"/>
        <table cellspacing="4">
            <tr>
                <td><fmt:message key="Login" bundle="${bundle}"/></td>
                <td><input type="text" name="login"></td>
            </tr>
            <tr>
                <td><fmt:message key="Password" bundle="${bundle}"/></td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><br> <input type="submit"
                                                           value=<fmt:message key="signIn" bundle="${bundle}"/>></td>
            </tr>
        </table>
        <p>
            <a href="<c:url value="/ClientRegistForm.jsp"/>"><fmt:message
                    key="register" bundle="${bundle}"/></a> <br>
        </p>
        <p>
            <a href="${pageContext.request.contextPath}/Controller?command=SHOW_ALL_APARTMENTS"><fmt:message
                    key="continueAsGuest" bundle="${bundle}"/> </a>
        </p>
    </form>

</div>
</body>
</html>