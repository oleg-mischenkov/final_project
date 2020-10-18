<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 10.10.2020
  Time: 2:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@taglib prefix="mytag" uri="custom" %>

<%@ page import="com.mischenkov.session.SessionVariable" %>
<%@ page import="com.mischenkov.controller.ServiceController" %>
<%@page import="com.mischenkov.cookie.CookieCommon" %>
<%@ page import="com.mischenkov.entity.Service" %>
<c:set var="CURRENT_SERVICE" value="<%=ServiceController.REQ_ATTR_CURRENT_SERVICE%>"/>
<c:set var="CURRENT_TARIFFS" value="<%=ServiceController.REQ_ATTR_CURRENT_TARIFFS%>"/>
<c:set var="START_POSITION" value="<%=ServiceController.REQ_ATR_START_POSITION%>"/>
<c:set var="COUNT" value="<%=ServiceController.REQ_ATR_COUNT%>"/>
<c:set var="COOKIE_LANGUAGE" value="<%=CookieCommon.COOKIE_LANGUAGE%>"/>
<c:set var="LANGUAGE" value="${cookie.get(COOKIE_LANGUAGE).value}"/>
<c:set var="BASKET" value="<%=SessionVariable.SESSION_BASKET_ATTR%>"/>
<c:set var="USER" value="<%=SessionVariable.SESSION_USER_ATTR%>"/>
<c:set var="ORDER" value="<%=SessionVariable.SESSION_USER_ORDER%>"/>

<fmt:setLocale value="${LANGUAGE}"/>
<fmt:bundle basename="prop" prefix="main.">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Main Page</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="static/css/main.css">
    <link rel="stylesheet" href="static/css/lang.css">

    <style type="text/css">
        .my_vard_outer {
            position: relative;
        }
        .my_vard_inner {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            font-weight: bold;
            width: 100%;
            text-align: center;
        }
    </style>

</head>
<body>

<header>
    <jsp:include page="pattern/header.jsp"/>
</header>


<div class="container pt-4">

    <div class="row">

        <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 pb-4">

            <jsp:include page="/left-menu"/>

        </div>

        <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12">

            <div class="card">
                <div class="card-body">

                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h3>${requestScope.get(CURRENT_SERVICE).title}</h3>
                    </div>

                    <div class="card mb-4" style="border-left: 5px solid orange;">
                        <div class="card-body">
                            ${requestScope.get(CURRENT_SERVICE).shortDescription}
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-9"></div>
                        <div class="col-3">

                            <c:set var="natural"><fmt:message key="sort_natural"/></c:set>
                            <c:set var="priceHigh"><fmt:message key="sort_price_high"/></c:set>
                            <c:set var="priceLow"><fmt:message key="sort_price_low"/></c:set>
                            <c:set var="titleA"><fmt:message key="sort_title_a"/></c:set>
                            <c:set var="titleZ"><fmt:message key="sort_title_z"/></c:set>

                            <mytag:sort
                                    id="sort"
                                    service="${requestScope.get(CURRENT_SERVICE)}"
                                    optionNatural="${natural}"
                                    optionPriceHigh="${priceHigh}"
                                    optionPriceLow="${priceLow}"
                                    optionTitleA="${titleA}"
                                    optionTitleZ="${titleZ}"
                            />

                        </div>
                    </div>

                    <hr>

                    <c:forEach var="element" items="${requestScope.get(CURRENT_TARIFFS)}">
                        <div class="card mt-2">
                            <div class="row no-gutters">
                                <div class="col-4 my_vard_outer bg-secondary text-white">
                                    <div class="my_vard_inner">${element.title}</div>
                                </div>
                                <div class="col-8">
                                    <div class="row pl-3">
                                        <div class="col-12 card-body pt-1 pb-1">
                                            <p class="card-text">
                                                    <h5>${element.shortDescription}</h5>
                                            </p>
                                            <p class="card-text">
                                                <h5 class="badge badge-success pt-2 pb-2" style="font-size: 120%">${element.getPricePerDay()} <span style="font-size: 65%"><fmt:message key="money_currency_day"/></span></h5>
                                                <c:if test="${sessionScope.get(USER) != null}">
                                                    <form action="basked?tariff=${element.id}" method="POST">
                                                        <button type="submit" class="btn btn-dark mt-2"
                                                                <c:if test="${sessionScope.get(BASKET).isHasTariff(element.id)
                                                                || sessionScope.get(ORDER).isHasItem(element.id)}">disabled</c:if> >To Order</button>
                                                    </form>
                                                </c:if>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>


                    <div class="row mt-5">

                        <div class="col-12">

                            <%
                                StringBuilder stringBuilder = new StringBuilder();
                                int currentPosition = (int) request.getAttribute(ServiceController.REQ_ATR_START_POSITION)
                                        / (int) request.getAttribute(ServiceController.REQ_ATR_COUNT);
                                String urlPattern = "service?service=" +
                                        ((Service) request.getAttribute( ServiceController.REQ_ATTR_CURRENT_SERVICE )).getId();
                                String notActivePosition = "<li class=\"page-item\"><a class=\"page-link\" href=\"%s\">%s</a></li>";
                                String activePosition = "<li class=\"page-item active\" aria-current=\"page\">\n" +
                                        "      <a class=\"page-link\" href=\"%s\">%s<span class=\"sr-only\">(current)</span></a>\n" +
                                        "    </li>";

                                String[] pageUrls = (String[]) request.getAttribute(ServiceController.REQ_ATR_PAGINATION_URLS);

                                stringBuilder
                                        .append("<nav")
                                        .append("<ul class=\"pagination justify-content-center\">");

                                for (int i = 0; i < pageUrls.length; i++) {

                                    if ( (currentPosition + 1) == (i + 1) ) {
                                        stringBuilder.append( String.format( activePosition,
                                                urlPattern.concat(pageUrls[i]),
                                                (i + 1) + "")
                                        );
                                    } else {
                                        stringBuilder.append( String.format( notActivePosition,
                                                urlPattern.concat(pageUrls[i]),
                                                (i + 1) + "")
                                        );
                                    }
                                }

                                stringBuilder
                                        .append("</ul>")
                                        .append("</nav>");

                            %>

                            <%=
                            stringBuilder
                            %>

                        </div>

                        <div class="col-12">
                            <div class="card mb-4 mt-4" style="border-left: 5px solid #7a57ff;">
                                <div class="card-body">
                                    ${requestScope.get(CURRENT_SERVICE).description}
                                </div>
                            </div>
                        </div>

                    </div>

                </div>
            </div>

        </div>
    </div>


</div>

<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<jsp:include page="pattern/bootstrap_js.jsp"/>
<script>
    $(document).ready(function(){

        var sortOption = $("#sort > select option");

        sortOption.each(function () {
            var value = $(this).attr("value");
            var pathName = $(location).attr('href');
            if ( pathName.indexOf(value) != -1 ) {
                $(this).attr("selected", "");
            }
        });

        $("#sort > select").on("change",  (function() {
            $(this).children(":selected").trigger("click");
        }));

        $("#sort > select option").click(function(){
            var currentOptionValue = $(this).attr("value");
            var pathName = $(location).attr('href');

            var findSortField = pathName.indexOf("&sort-field=") == -1
                ? pathName.length
                : pathName.indexOf("&sort-field=");
            var findSortDirection = pathName.indexOf("&sort-direction=") == -1
                ? pathName.length
                : pathName.indexOf("&sort-direction=");

            var findMin = findSortField > findSortDirection ? findSortDirection : findSortField;

            if ( pathName.length > findMin ) {
                pathName = pathName.slice(0, findMin);
            }

            var finalUrl = pathName + currentOptionValue;
            console.log(finalUrl);
            window.location.href = finalUrl;
        });
    });
</script>
</body>
</html>
</fmt:bundle>