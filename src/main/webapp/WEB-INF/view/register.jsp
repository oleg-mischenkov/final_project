<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 27.09.2020
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.mischenkov.controller.CommonControllerValues" %>
<%@page import="com.mischenkov.controller.RegisterController" %>
<c:set var="LOGIN" value="<%=RegisterController.REQ_PARAM_LOGIN%>"/>
<c:set var="FIRST_NAME" value="<%=RegisterController.REQ_PARAM_FIRST_NAME%>"/>
<c:set var="LAST_NAME" value="<%=RegisterController.REQ_PARAM_LAST_NAME%>"/>
<c:set var="EMAIL" value="<%=RegisterController.REQ_PARAM_EMAIL%>"/>

<jsp:include page="/current-language"/>
<c:set var="LANGUAGE" value="<%=CommonControllerValues.CURRENT_LANGUAGE%>"/>

<fmt:setLocale value="${requestScope.get(LANGUAGE).iso639_1}"/>
<fmt:bundle basename="prop" prefix="main.">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Register</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="static/css/main.css">
    <link rel="stylesheet" type="text/css" href="static/css/registration.css">
    <link rel="stylesheet" type="text/css" href="static/css/lang.css">

</head>
<body>
<header>
    <jsp:include page="pattern/header.jsp"/>
</header>


<div class="content-side">
    <div class="container">

        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mt-2"><fmt:message key="register_sign_up"/></h5>
                    </div>
                    <article class="card-body">
                        <form action="registration" method="post">

                            <div class="form-row">
                                <div class="col form-group">
                                    <label><fmt:message key="register_login"/> </label>
                                    <input type="text" class="form-control
                                            <c:if test="${requestScope.containsKey(LOGIN)}">is-invalid</c:if>"
                                            <c:if test="${requestScope.containsKey(LOGIN)}">value="${requestScope.get(LOGIN)}"</c:if> name="login">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="col form-group">
                                    <label><fmt:message key="register_first_name"/> </label>
                                    <input type="text" class="form-control
                                            <c:if test="${requestScope.containsKey(FIRST_NAME)}">is-invalid</c:if>"
                                            <c:if test="${requestScope.containsKey(FIRST_NAME)}">value="${requestScope.get(FIRST_NAME)}" </c:if> name="first-name">
                                </div> <!-- form-group end.// -->
                                <div class="col form-group">
                                    <label><fmt:message key="register_last_name"/> </label>
                                    <input type="text" class="form-control
                                            <c:if test="${requestScope.containsKey(LAST_NAME)}">is-invalid</c:if>"
                                            <c:if test="${requestScope.containsKey(LAST_NAME)}">value="${requestScope.get(LAST_NAME)}"</c:if> name="last-name">
                                </div> <!-- form-group end.// -->
                            </div> <!-- form-row end.// -->

                            <div class="form-group">
                                <label><fmt:message key="register_email_address"/> </label>
                                <input type="email" class="form-control
                                        <c:if test="${requestScope.containsKey(EMAIL)}">is-invalid</c:if>"
                                        <c:if test="${requestScope.containsKey(EMAIL)}">value="${requestScope.get(EMAIL)}" </c:if> name="email">
                                <small class="form-text text-muted"><fmt:message key="register_promice"/></small>
                            </div> <!-- form-group end.// -->
                            <div class="form-group">
                                <label class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" value="m" checked="checked">
                                    <span class="form-check-label"> <fmt:message key="register_male"/> </span>
                                </label>
                                <label class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" value="f">
                                    <span class="form-check-label">  <fmt:message key="register_female"/> </span>
                                </label>

                            </div> <!-- form-group end.// -->

                            <div class="form-group">
                                <label><fmt:message key="register_create_password"/></label>
                                <input class="form-control" type="password" name="password">
                            </div> <!-- form-group end.// -->

                            <div class="form-group">
                                <button type="submit" class="btn btn-primary btn-block"> <fmt:message key="register_register"/> </button>
                            </div> <!-- form-group// -->
                            <small class="text-muted"><fmt:message key="register_by_clicking"/></small>
                        </form>
                    </article> <!-- card-body end .// -->
                    <div class="border-top card-body text-center"><fmt:message key="register_have_account"/> <a href="login"><fmt:message key="log_in"/></a></div>
                </div> <!-- card.// -->
            </div> <!-- col.//-->

        </div> <!-- row.//-->

    </div> <!--container end.//-->
</div>


<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<jsp:include page="pattern/bootstrap_js.jsp"/>
</body>
</html>
</fmt:bundle>