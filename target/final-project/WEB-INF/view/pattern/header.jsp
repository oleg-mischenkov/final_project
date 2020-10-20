<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 27.09.2020
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.mischenkov.listener.ContextVariable" %>
<%@page import="com.mischenkov.session.SessionVariable" %>
<%@page import="com.mischenkov.controller.CommonControllerValues" %>

<c:set var="PERMISSION" value="<%=SessionVariable.SESSION_USER_PERMISSION%>"/>
<c:set var="USER" value="<%=SessionVariable.SESSION_USER_ATTR%>"/>
<c:set var="CURRENT_LANGS" value="<%=ContextVariable.CONTEXT_CURRENT_LANGS%>"/>
<c:set var="BASKED" value="<%=SessionVariable.SESSION_BASKET_ATTR%>"/>

<jsp:include page="/current-language"/>
<c:set var="LANGUAGE" value="<%=CommonControllerValues.CURRENT_LANGUAGE%>"/>

<fmt:setLocale value="${requestScope.get(LANGUAGE).iso639_1}"/>
<fmt:bundle basename="prop" prefix="main.">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">

        <a class="navbar-brand" href="index.jsp">Final Project</a>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">

            <ul class="navbar-nav mr-auto">

            </ul>

            <div class="navbar-nav my-lg-0 mr-3">

                <form action="language" method="post" id="lang">
                    <select class="custom-select custom-select-sm pr-3 pl-1" name="lang">
                    <c:forEach var="element" items='${applicationScope.get(CURRENT_LANGS)}'>
                        <c:set var="lang" value="${element.iso639_1}"/>
                        <option value="${lang}" <c:if test="${requestScope.get(LANGUAGE).iso639_1.equals(lang)}">selected</c:if>><c:out value="${lang}"/></option>
                    </c:forEach>
                    </select>
                </form>
                    <script type="text/javascript">
                        const cookie_language = "${requestScope.get(LANGUAGE).iso639_1}";
                    </script>
            </div>
            <div class="navbar-nav my-2 my-lg-0">
                <c:choose>
                    <c:when test="${sessionScope.get(USER) == null}">
                        <a class="nav-link" href="login"><fmt:message key="log_in"/> </a>
                    </c:when>
                    <c:when test="${sessionScope.get(USER) != null}">
                        <div class="dropdown">
                            <a class="nav-link" href="#" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${sessionScope.get(USER).login}</a>

                            <div class="dropdown-menu dropdown-menu-right custom-menu " aria-labelledby="dropdownMenuButton">
                                <c:if test="${sessionScope.get(PERMISSION).cardAccess}">
                                    <a class="dropdown-item" href="account"><fmt:message key="menu_personal_account"/> </a>
                                </c:if>
                                <c:if test="${sessionScope.get(PERMISSION).adminAccess}">
                                    <a class="dropdown-item" href="admin?command=index"><fmt:message key="menu_admin_panel"/> </a>
                                </c:if>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="logout"><fmt:message key="log_out"/></a>
                            </div>
                        </div>
                    </c:when>
                </c:choose>
                <c:if test="${sessionScope.get(BASKED).size() > 0}">
                    <div class="dropdown">

                        <a class="nav-link pt-1 pb-1 ml-1 mr-1" href="#" id="dropdownMenuBasked" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <svg width="24px" height="24px" viewBox="0 0 16 16" class="bi bi-cart4 text-light" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd" d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5zM3.14 5l.5 2H5V5H3.14zM6 5v2h2V5H6zm3 0v2h2V5H9zm3 0v2h1.36l.5-2H12zm1.11 3H12v2h.61l.5-2zM11 8H9v2h2V8zM8 8H6v2h2V8zM5 8H3.89l.5 2H5V8zm0 5a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0zm9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0z"/>
                            </svg>
                            <span class="badge badge-danger">${sessionScope.get(BASKED).size()}</span>
                        </a>


                        <div class="dropdown-menu dropdown-menu-right px-3 shadow" aria-labelledby="dropdownMenuBasked">
                            <table class="table table-hover table-borderless table-sm table-striped mt-3" style="min-width: 300px;">
                                <tbody>
                                        <c:forEach var="element" items="${sessionScope.get(BASKED).getAll()}" varStatus="num">
                                            <tr>
                                                <td>${num.index + 1}</td>
                                                <td>${element.title}</td>
                                                <td style="text-align: end">${element.getPricePerDay()}</td>
                                                <td style="text-align: end">${element.getPricePerWeeks(4)}</td>
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
                            <div class="cell-12 bg-info text-white pl-2">
                                <div class="cell-3">
                                    <b>total:</b>
                                </div>
                                <div class="cell-9" style="text-align: center;">
                                        ${sessionScope.get(BASKED).totalMoneyPerWeekInDouble(4)} <fmt:message key="money_currency_weeks"/>
                                </div>
                            </div>
                            <div class="cell-12 mt-2" style="text-align: center;">
                                <h6><a href="cart" class="text-info"><fmt:message key="go_to_cart"/></a></h6>
                            </div>
                        </div>
                    </div>

                </c:if>
            </div>

        </div>

    </div>
</nav>
</fmt:bundle>