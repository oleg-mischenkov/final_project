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
<%@ page import="com.mischenkov.controller.admin.TariffsCommand" %>
<c:set var="REQ_ATTR_SERVICES" value="<%=TariffsCommand.REQ_ATTR_SERVICES%>"/>
<c:set var="REQ_ATTR_TARIFFS" value="<%=TariffsCommand.REQ_ATTR_TARIFFS%>"/>
<c:set var="REQ_ATTR_SELECTED_SERVICE" value="<%=TariffsCommand.REQ_ATTR_SELECTED_SERVICE%>"/>

<jsp:include page="/current-language"/>
<c:set var="LANGUAGE" value="<%=CommonControllerValues.CURRENT_LANGUAGE%>"/>

<fmt:setLocale value="${requestScope.get(LANGUAGE).iso639_1}"/>
<fmt:bundle basename="prop" prefix="main.">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>tariffs</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="static/css/admin.css">
    <link rel="stylesheet" type="text/css" href="static/css/lang.css">

    <style type="text/css">
        #service-table td:nth-child(2):hover {
            cursor: pointer;
        }
        .bold-font {
            font-weight: bold;
            color: #2d2d2d;
        }
        .td-shadow {
            box-shadow: -0.7rem 0.6rem 3rem rgba(0,0,0,.15) !important;
        }
    </style>

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
                    <h3><fmt:message key="menu_tariff_management"/></h3>
                </div>

                <div class="row">
                    <div class="col-lg-6 pr-lg-0"><!-- panel 1 -->

                        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom pl-3">
                            <h5><fmt:message key="admin_services"/></h5>
                            <a class="btn btn-info" href="admin?command=addService"><span>+ </span> <fmt:message key="admin_service"/></a>
                        </div>

                        <table id="service-table" class="table table-borderless table-hover">
                            <thead style="background-color: #17a2b8">
                                <th scope="col" style="color: white;">#</th>
                                <th scope="col"></th>
                                <th scope="col"></th>
                                <th scope="col"></th>
                                <th scope="col"></th>
                            </thead>
                            <tbody>
                            <c:forEach var="element" items="${requestScope.get(REQ_ATTR_SERVICES)}" varStatus="num">
                                <tr class="border-right border-bottom <c:if test="${element.active == false}">table-danger</c:if> <c:if test="${requestScope.get(REQ_ATTR_SELECTED_SERVICE) == element.id}">border-left border-info border-right-0 bold-font</c:if>">
                                    <td class="align-middle">${num.index + 1}</td>
                                    <td class="align-middle" id="serv-cart-${element.id}">${element.title}</td>
                                    <td>
                                        <div class="row pb-1" style="font-size: 12px;">
                                            <div class="col-3 px-0" style="font-weight: bold"><fmt:message key="admin_table_start"/></div>
                                            <div class="col-9 px-0">${element.startDate}</div>
                                        </div>
                                        <div class="row" style="font-size: 12px;">
                                            <div class="col-3 px-0" style="font-weight: bold"><fmt:message key="admin_table_end"/></div>
                                            <div class="col-9 px-0">${element.endDate}</div>
                                        </div>
                                    </td>
                                    <td class="align-middle">
                                        <div id="serv-${element.id}" class="custom-control custom-switch">
                                            <input type="checkbox" class="custom-control-input serv-custom-control-input"
                                                   <c:if test="${element.active == true}">checked</c:if> id="customSwitch1-${num.index + 100}">
                                            <label class="custom-control-label" for="customSwitch1-${num.index + 100}"></label>
                                        </div>
                                    </td>
                                    <td class="align-top pt-0 pr-1" style="text-align: right;">
                                        <a href="admin?command=delete&service=${element.id}" class="btn btn-danger btn-sm px-1 pb-0 pt-0">
                                            <svg width="1em" height="1em" style="width: 12px; height: 12px;" viewBox="0 0 16 16" class="bi bi-trash mr-0" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                                <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                            </svg>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div> <!-- panel 1 end. -->
                    <div class="col-lg-6 pl-lg-0"><!-- panel 2 -->

                        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom pl-3">
                            <h5><fmt:message key="admin_tariffs"/></h5>
                            <a class="btn btn-dark" href="admin?command=addTariff&service=${requestScope.get(REQ_ATTR_SELECTED_SERVICE)}"><span>+ </span> <fmt:message key="admin_tariff"/></a>
                        </div>

                        <table class="table table-borderless table-hover table-striped">
                            <thead class="thead-dark">
                                <th scope="col">#</th>
                                <th scope="col"></th>
                                <th scope="col"></th>
                                <th scope="col"></th>
                                <th scope="col"></th>
                                <th scope="col"></th>
                            </thead>
                            <tbody>
                                <c:forEach var="element" items="${requestScope.get(REQ_ATTR_TARIFFS)}" varStatus="num">
                                    <tr class="border-right border-bottom <c:if test="${element.active == false}">table-danger</c:if>">
                                        <td class="align-middle">
                                                ${num.index + 1}
                                        </td>
                                        <td class="align-middle">
                                                ${element.title}
                                        </td>
                                        <td class="align-middle">
                                            <div class="row pb-1" style="font-size: 12px;">
                                                <div class="col-3 px-0" style="font-weight: bold"><fmt:message key="admin_table_start"/></div>
                                                <div class="col-9 px-0">${element.startDate}</div>
                                            </div>
                                            <div class="row" style="font-size: 12px;">
                                                <div class="col-3 px-0" style="font-weight: bold"><fmt:message key="admin_table_end"/></div>
                                                <div class="col-9 px-0">${element.endDate}</div>
                                            </div>
                                        </td>
                                        <td class="align-middle">
                                            <span class="badge badge-success px-2 mr-1" style="font-size: inherit;">${element.price / 100} </span><span style="font-size: 11px;">UAH/day</span>
                                        </td>
                                        <td class="align-middle">
                                            <div id="tar-${element.id}" class="custom-control custom-switch">
                                                <input type="checkbox" class="custom-control-input tar-custom-control-input"
                                                       <c:if test="${element.active == true}">checked</c:if> id="tar-customSwitch1-${num.index + 100}">
                                                <label class="custom-control-label" for="tar-customSwitch1-${num.index + 100}"></label>
                                            </div>
                                        </td>
                                        <td class="align-top pt-0 pr-1" style="text-align: right;">
                                            <a href="admin?command=delete&tariff=${element.id}" class="btn btn-danger btn-sm px-1 pb-0 pt-0">
                                                <svg width="1em" height="1em" style="width: 12px; height: 12px;" viewBox="0 0 16 16" class="bi bi-trash mr-0" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                    <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                                    <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                                </svg>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div><!-- panel 2 end. -->
                </div>

            </main> <!-- site content END.-->

        </div>

    </div> <!--container end.//-->


<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<jsp:include page="../pattern/bootstrap_js.jsp"/>
    <script type="text/javascript">
        /*Service jumper Service Table*/
        $(document).ready( function() {
            $(".serv-custom-control-input").click(function(){
                var inputPosition;

                if( $(this).attr("checked") == "checked" ) {
                    inputPosition = 0;
                } else {
                    inputPosition = 1;
                }

                var seviceId = $(this).parent().attr("id");
                seviceId = seviceId.slice(5);

                var pathName = $(location).attr('href');

                var findService = pathName.indexOf("&service=") == -1 ? pathName.length : pathName.indexOf("&service=");
                var findActive = pathName.indexOf("&active=") == -1 ? pathName.length : pathName.indexOf("&active=");
                var findTariff = pathName.indexOf("&tariff=") == -1 ? pathName.length : pathName.indexOf("&tariff=");

                var findMin = findService > findActive ? findActive : findService;
                findMin = findMin > findTariff ? findTariff : findMin;

                if (pathName.length > findMin) {
                    pathName = pathName.slice(0, findMin);
                }

                var finalUrl = pathName + "&service=" + seviceId + "&active=" + inputPosition;
                window.location.href = finalUrl;
            });
        });

        // service jumper for tariffs
        $(document).ready( function(){
            $(".tar-custom-control-input").click(function(){
                var inputPosition;

                console.log(".tar-custom-control-input");

                if( $(this).attr("checked") == "checked" ) {
                    inputPosition = 0;
                } else {
                    inputPosition = 1;
                }

                var seviceId = $(this).parent().attr("id");
                seviceId = seviceId.slice(4);

                var pathName = $(location).attr('href');

                var findService = pathName.indexOf("&service=") == -1 ? pathName.length : pathName.indexOf("&service=");
                var findActive = pathName.indexOf("&active=") == -1 ? pathName.length : pathName.indexOf("&active=");
                var findTariff = pathName.indexOf("&tariff=") == -1 ? pathName.length : pathName.indexOf("&tariff=");

                var findMin = findService > findActive ? findActive : findService;
                findMin = findMin > findTariff ? findTariff : findMin;

                if (pathName.length > findMin) {
                    pathName = pathName.slice(0, findMin);
                }

                var finalUrl = pathName + "&tariff=" + seviceId + "&active=" + inputPosition;
                window.location.href = finalUrl;
            });
        });

        // service-hover
        $(document).ready(function(){
            $("#service-table tr td:nth-child(2)").click(function(){
                var url = "admin?command=tariffs&serv-inf=";

                var serviceId = $(this).attr("id");
                serviceId = serviceId.slice(10);

                url += serviceId;
                window.location.href = url;
            });
        });
    </script>
</body>
</html>
</fmt:bundle>