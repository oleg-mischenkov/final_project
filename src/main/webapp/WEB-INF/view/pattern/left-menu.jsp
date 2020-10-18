<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 09.10.2020
  Time: 1:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="com.mischenkov.controller.MenuController" %>
<c:set var="MENU_SERVICES" value="<%=MenuController.REQ_ATTR_MENU_SERVICES%>"/>

<%@page import="com.mischenkov.controller.CommonControllerValues" %>
<jsp:include page="/current-language"/>
<c:set var="LANGUAGE" value="<%=CommonControllerValues.CURRENT_LANGUAGE%>"/>


<fmt:setLocale value="${requestScope.get(LANGUAGE).iso639_1}"/>
<fmt:bundle basename="prop" prefix="main.">
<div class="card" >
    <div class="card-header">
        <fmt:message key="menu_services"/>
    </div>
    <ul class="list-group list-group-flush">

        <c:forEach var="element" items="${requestScope.get(MENU_SERVICES)}">
            <li class="list-group-item">
                <a href="service?service=${element.id}"><c:out value="${element.title}"/></a>
            </li>
        </c:forEach>
        <li class="list-group-item">
            <a href="#" data-toggle="modal" data-target="#saveModal"><fmt:message key="menu_get_price"/> </a>
        </li>
    </ul>
</div>

<!-- Modal -->
<div class="modal fade" id="saveModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="download_tariffs"/> </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="download-form" action="price-list" method="POST">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="format" id="exampleRadios1" value="txt" checked>
                        <label class="form-check-label" for="exampleRadios1">
                            <fmt:message key="simple_txt"/>
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="format" id="exampleRadios2" value="xls">
                        <label class="form-check-label" for="exampleRadios2">
                            <fmt:message key="exel_file"/>
                        </label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="$('#download-form').submit(); $('#saveModal').modal('hide');" ><fmt:message key="button_download"/></button>
            </div>
        </div>
    </div>
</div>
</fmt:bundle>