<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 13.10.2020
  Time: 1:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.mischenkov.controller.CommonControllerValues" %>
<%@page import="com.mischenkov.session.SessionVariable" %>
<%@page import="com.mischenkov.controller.AccountController" %>
<c:set var="USER" value="<%=SessionVariable.SESSION_USER_ATTR%>"/>
<c:set var="WALLET" value="<%=SessionVariable.SESSION_WALLET_ATTR%>"/>
<c:set var="ERROR_MSG" value="<%=AccountController.REQ_ATTR_PATTERN_ERROR_MSG%>"/>
<c:set var="ORDER" value="<%=SessionVariable.SESSION_USER_ORDER%>"/>

<jsp:include page="/current-language"/>
<c:set var="LANGUAGE" value="<%=CommonControllerValues.CURRENT_LANGUAGE%>"/>

<fmt:setLocale value="${requestScope.get(LANGUAGE).iso639_1}"/>
<fmt:bundle basename="prop" prefix="main.">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Personal Account</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="static/css/main.css">
    <link rel="stylesheet" href="static/css/lang.css">
</head>
<body>



<header>
    <jsp:include page="pattern/header.jsp"/>
</header>


<div class="container pt-4">

    <div class="row">
        <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12">

            <jsp:include page="/left-menu"/>

        </div>
        <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12">
            <div class="card">
                <div class="card-body">

                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h3><fmt:message key="ap_account_page"/></h3>
                    </div>

                    <div class="row">

                        <div class="col-2"><fmt:message key="ap_login"/></div>
                        <div class="col-10">${sessionScope.get(USER).login} (${sessionScope.get(USER).gender})</div>
                        <div class="col-2"><fmt:message key="ap_name"/></div>
                        <div class="col-10">${sessionScope.get(USER).firstName} ${sessionScope.get(USER).lastName}</div>
                        <div class="col-2"><fmt:message key="ap_mail"/></div>
                        <div class="col-10">${sessionScope.get(USER).email}</div>
                        <div class="col-12 border-bottom pt-3 mb-4"> </div>
                        <div class="col-12"><b><fmt:message key="ap_wallet"/></b></div>
                        <div class="col-2"><fmt:message key="money"/></div><div class="col-10">${sessionScope.get(WALLET).money / 100}</div>
                        <div class="col-2"><b><fmt:message key="ap_blocked"/></b></div><div class="col-10"><b>(${sessionScope.get(WALLET).blockedMoney / 100})</b></div>
                    </div>

                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h5><fmt:message key="ap_top_up"/></h5>
                    </div>

                    <c:if test="${requestScope.get(ERROR_MSG) != null}">
                        <div class="alert alert-danger" role="alert">
                            ${requestScope.get(ERROR_MSG)}
                        </div>
                    </c:if>

                    <div class="row">

                        <div class="col-4 card bg-light ml-3 px-0" style="border-radius: 5%;">
                            <form class="card-body" action="account" method="POST">
                                <div class="form-group">
                                    <label for="cardCode" style="font-variant: small-caps;"><fmt:message key="ap_enter_card"/></label>
                                    <input type="text" class="form-control" style="background-color: #ffe;" id="cardCode" aria-describedby="cardHelp" placeholder="000000" name="card">
                                    <small id="cardHelp" class="form-text text-muted">Enter 6 characters from your card</small>
                                </div>
                                <button type="submit" class="btn btn-info"><fmt:message key="ap_apply"/></button>
                            </form>
                        </div>

                        <div class="col-1">
                        </div>

                        <div class="col-6">
                            <c:if test="${sessionScope.get(ORDER).itemList.size() > 0}">
                                <table class="table table-sm table-striped table-borderless table-hover">
                                    <tbody>
                                    <c:forEach var="element" items="${sessionScope.get(ORDER).itemList}" varStatus="num">
                                        <tr>
                                            <td><b>${num.index + 1}</b></td>
                                            <td>${element.title}</td>
                                            <td>${element.getPricePerWeeks(4)}</td>
                                            <td style="text-align: end;">
                                                <form action="account?delete=${element.id}" method="POST">
                                                    <button type="submit" class="btn btn-danger btn-sm px-1 pt-0 pb-0" <c:if test="${sessionScope.get(WALLET).blockedMoney > 0}">disabled</c:if> >
                                                        <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-trash-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                            <path fill-rule="evenodd" d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7z"/>
                                                        </svg>
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>

                                    </c:forEach>
                                    </tbody>
                                </table>

                                <div class="row bg-info text-white pl-2">
                                    <div class="col-3">
                                        <b>total:</b>
                                    </div>
                                    <div class="col-9" style="text-align: end;">
                                            ${sessionScope.get(ORDER).getTotalInDouble()}
                                    </div>
                                </div>

                                <c:if test="${sessionScope.get(WALLET).blockedMoney < 1}">
                                    <div class="col-12 px-0 pt-2">
                                        <form class="form" action="account?reorder=1" method="POST">
                                            <button type="submit" class="btn btn-info"><fmt:message key="ap_reorder"/></button>
                                        </form>
                                    </div>
                                </c:if>

                            </c:if>
                        </div>

                    </div>

                </div>
            </div>
        </div>
    </div>


</div>


<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<jsp:include page="pattern/bootstrap_js.jsp"/>
</body>
</html>
</fmt:bundle>