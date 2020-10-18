<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 05.10.2020
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.mischenkov.controller.CommonControllerValues" %>
<%@ page import="com.mischenkov.controller.admin.AddTariffCommand" %>
<%@ page import="com.mischenkov.listener.ContextVariable" %>
<c:set var="CURRENT_LANGS" value="<%=ContextVariable.CONTEXT_CURRENT_LANGS%>"/>
<c:set var="REQ_ATR_PATTERN_ERROR_MSG" value="<%=AddTariffCommand.REQ_ATTR_PATTERN_ERROR_MSG%>"/>
<c:set var="SERVICE" value="<%=AddTariffCommand.REQ_PARAM_SERVICE%>"/>

<jsp:include page="/current-language"/>
<c:set var="LANGUAGE" value="<%=CommonControllerValues.CURRENT_LANGUAGE%>"/>

<fmt:setLocale value="${requestScope.get(LANGUAGE).iso639_1}"/>
<fmt:bundle basename="prop" prefix="main.">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Add Tariff</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="static/css/admin.css">
    <link rel="stylesheet" type="text/css" href="static/css/lang.css">

</head>
<body>

<header>
    <jsp:include page="../pattern/admin-header.jsp"/>
</header>

<div class="container-fluid">

    <div class="row">

        <jsp:include page="../pattern/admin-left-menu.jsp"/>

        <main class="col-md-9 ml-sm-auto col-lg-10 px-4 main"> <!-- site content -->

            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h3>Add Tariff</h3>
            </div>

            <c:if test="${requestScope.get(REQ_ATR_PATTERN_ERROR_MSG) != null }">
                <div class="alert alert-danger" role="alert">
                        ${requestScope.get(REQ_ATR_PATTERN_ERROR_MSG)}
                </div>
            </c:if>

            <%--form--%>

            <form class="row" action="admin?command=addTariff&service=${requestScope.get(SERVICE)}" method="POST">
                <div class="col-3">
                    <div class="form-group">
                        <label for="startDate"><fmt:message key="admin_add_t_start_date"/></label>
                        <input type="text" class="form-control" id="startDate" placeholder="YYYY-MM-DD" name="start-date">
                    </div>
                </div>
                <div class="col-3">
                    <div class="form-group">
                        <label for="endDate"><fmt:message key="admin_add_t_end_date"/></label>
                        <input type="text" class="form-control" id="endDate" placeholder="YYYY-MM-DD" name="end-date">
                    </div>
                </div>

                <div class="col-6">
                        <div class="form-group float-right">
                            <label class="pr-4" for="isActive"><fmt:message key="admin_is_active"/></label>
                            <div id="isActive" class="custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input serv-custom-control-input"
                                       <c:if test="${element.active == true}">checked</c:if> id="customSwitch1" name="active">
                                <label class="custom-control-label" for="customSwitch1"></label>
                            </div>
                        </div>
                </div>

                <div class="col-1 col-md-2 col-xs-2">
                    <div class="form-group">
                        <label for="tarPrice"><fmt:message key="price"/></label>
                        <input type="text" class="form-control" id="tarPrice" placeholder="55,00" name="price">
                    </div>
                </div>

                <div class="col-12 d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h5><fmt:message key="admin_content"/></h5>
                </div>

                <div class="col-12">

                    <div class="row">
                        <div class="col-1">
                            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">

                                <c:forEach var="element" items="${applicationScope.get(CURRENT_LANGS)}" varStatus="nav">
                                    <a class="nav-link <c:if test="${nav.index == 0}">active</c:if> " id="v-pills-${element.iso639_1}-tab" data-toggle="pill" href="#v-pills-${element.iso639_1}" role="tab" aria-controls="v-pills-${element.iso639_1}" <c:if test="${nav.index == 0}">aria-selected="true"</c:if><c:if test="${nav.index != 0}">aria-selected="false"</c:if> >${element.iso639_1}</a>
                                </c:forEach>


                            </div>
                        </div>
                        <div class="col-11">
                            <div class="tab-content" id="v-pills-tabContent">

                                <c:forEach var="element" items="${applicationScope.get(CURRENT_LANGS)}" varStatus="nav">
                                    <div class="tab-pane fade <c:if test="${nav.index == 0}">show active</c:if>" id="v-pills-${element.iso639_1}" role="tabpanel" aria-labelledby="v-pills-${element.iso639_1}-tab">

                                        <div class="row">

                                            <div class="col-6 col-sm-12">
                                                <div class="form-group">
                                                    <label for="title"><fmt:message key="admin_tariff_service_title"/></label>
                                                    <input type="text" class="form-control" id="title" placeholder="title" aria-describedby="titleHelp" name="title(${element.id})">
                                                    <small id="titleHelp" class="form-text text-muted"><fmt:message key="admin_tariff_service_max_45"/></small>
                                                </div>
                                            </div>

                                            <div class="col-11 col-sm-12">
                                                <div class="form-group">
                                                    <label for="shortDescription"><fmt:message key="admin_short_description"/></label>
                                                    <textarea class="form-control" id="shortDescription" rows="3" aria-describedby="shortHelp" name="short-description(${element.id})"></textarea>
                                                    <small id="shortHelp" class="form-text text-muted"><fmt:message key="admin_tariff_service_max_255"/></small>
                                                </div>
                                            </div>

                                            <div class="col-11 col-sm-12">
                                                <div class="form-group">
                                                    <label for="description"><fmt:message key="admin_service_description"/></label>
                                                    <textarea class="form-control" id="description" rows="7" aria-describedby="descriptionHelp" name="description(${element.id})"></textarea>
                                                </div>
                                            </div>

                                        </div>


                                    </div>
                                </c:forEach>


                            </div>
                        </div>
                    </div>

                </div>

                <div class="col-12">
                    <button type="submit" class="btn btn-primary float-right"><fmt:message key="button_save"/></button>
                </div>
            </form>

            <%--form end.--%>


        </main> <!-- site content END.-->

    </div>

</div> <!--container end.//-->


<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<jsp:include page="../pattern/bootstrap_js.jsp"/>
</body>
</html>
</fmt:bundle>