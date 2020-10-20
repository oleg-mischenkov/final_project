<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 22-Sep-20
  Time: 12:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.mischenkov.controller.CommonControllerValues" %>
<jsp:include page="/current-language"/>
<c:set var="LANGUAGE" value="<%=CommonControllerValues.CURRENT_LANGUAGE%>"/>


<fmt:setLocale value="${requestScope.get(LANGUAGE).iso639_1}"/>
<fmt:bundle basename="prop" prefix="main.">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>User Login</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="static/css/login.css">
    <link rel="stylesheet" href="static/css/lang.css">
</head>
<body>

<header>
    <jsp:include page="WEB-INF/view/pattern/header.jsp"/>
</header>



    <div class="wrapper">
        <form class="form-signin" action="login" method="post">

            <h2 class="form-signin-heading"><fmt:message key="please_login"/></h2>
            <input type="text" class="form-control <c:if test="${fail == 'false'}">is-invalid</c:if>"
                   name="email" placeholder="Email Address" required="" autofocus="" <c:if test="${fail == 'false'}">value="${email}"</c:if> />
            <input type="password" class="form-control <c:if test="${fail == 'false'}">is-invalid</c:if>"
                   name="password" placeholder="Password" required="" <c:if test="${fail == 'false'}">value="${password}"</c:if> />

            <button class="btn btn-primary btn-block" type="submit"><fmt:message key="button_login"/></button>

            <div class="register">
                <a href="registration"><fmt:message key="link_register_now"/></a>
            </div>

        </form>

        <c:if test="${fail == 'false'}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="lp_incorrect"/>
            </div>
        </c:if>
    </div>

    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <jsp:include page="WEB-INF/view/pattern/bootstrap_js.jsp"/>
</body>
</html>
</fmt:bundle>