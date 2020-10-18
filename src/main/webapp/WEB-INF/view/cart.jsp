<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 15.10.2020
  Time: 0:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.mischenkov.session.SessionVariable" %>
<%@page import="com.mischenkov.controller.CommonControllerValues" %>
<c:set var="WALLET" value="<%=SessionVariable.SESSION_WALLET_ATTR%>"/>
<c:set var="BASKET" value="<%=SessionVariable.SESSION_BASKET_ATTR%>"/>

<jsp:include page="/current-language"/>
<c:set var="LANGUAGE" value="<%=CommonControllerValues.CURRENT_LANGUAGE%>"/>

<fmt:setLocale value="${LANGUAGE}"/>
<fmt:bundle basename="prop" prefix="main.">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Cart</title>

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
                        <h3><fmt:message key="ct_cart"/></h3>
                    </div>

                    <div class="row">

                        <div class="col-6">
                            <div class="row">
                                <div class="col-6"><b><fmt:message key="ct_av_m"/></b></div>
                                <div class="col-6">${sessionScope.get(WALLET).getMoneyInDouble()}</div>
                            </div>
                        </div>

                        <div class="col-6">
                            <div class="row">
                                <div class="col-6"><b><fmt:message key="ct_bm"/></b></div>
                                <div class="col-6">${sessionScope.get(WALLET).getBlockedMoneyInDouble()}</div>
                            </div>
                        </div>

                        <c:if test="${sessionScope.get(WALLET).money < sessionScope.get(BASKET).totalMoneyPerWeek(4)}">
                            <div class="col-12 alert alert-warning mt-4">
                                <fmt:message key="ct_low_money_msg"/> <a href="account"><fmt:message key="ct_tua"/></a>.
                            </div>
                        </c:if>

                        <table class="col-12 table table-sm table-striped table-hover mt-4">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col"><fmt:message key="ct_t_title"/></th>
                                    <th scope="col"><fmt:message key="ct_cost"/></th>
                                    <th scope="col"><fmt:message key="ct_period"/></th>
                                    <th scope="col"></th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="element" items="${sessionScope.get(BASKET).getAll()}" varStatus="num">
                                <tr>
                                    <th scope="row">${num.index + 1}</th>
                                    <td>${element.title}</td>
                                    <td>${element.getPricePerDay()}</td>
                                    <td>${element.getPricePerWeeks(4)}</td>
                                    <td style="text-align: end;">
                                        <form action="basked?delete=${element.id}" method="POST">
                                            <button type="submit" class="btn btn-danger btn-sm px-1 pt-0 pb-0">
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
                    </div>
                    <div class="row bg-info text-white pl-2">
                        <div class="col-3">
                            <b><fmt:message key="ct_total"/></b>
                        </div>
                        <div class="col-9" style="text-align: end;">
                                ${sessionScope.get(BASKET).totalMoneyPerWeekInDouble(4)} <fmt:message key="money_currency_weeks"/>
                        </div>
                    </div>

                    <div class="row">
                        <form action="cart" method="POST">
                            <button type="submit" class="btn btn-dark mt-2" <c:if test="${sessionScope.get(WALLET).money < sessionScope.get(BASKET).totalMoneyPerWeek(4)}">disabled</c:if> >
                                <fmt:message key="buy"/>
                            </button>
                        </form>
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