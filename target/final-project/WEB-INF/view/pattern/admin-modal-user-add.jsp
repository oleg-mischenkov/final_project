<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 02.10.2020
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="com.mischenkov.controller.admin.UsersCommand" %>
<%@page import="com.mischenkov.controller.CommonControllerValues" %>
<c:set var="PATTERN_ERROR_FIELD" value="<%=UsersCommand.REQ_ATR_PATTERN_ERROR_FIELD%>"/>
<c:set var="PATTERN_ERROR_MSG" value="<%=UsersCommand.REQ_ATR_PATTERN_ERROR_MSG%>"/>

<c:set var="LOGIN" value="<%=UsersCommand.REQ_PARAM_LOGIN%>"/>
<c:set var="FIRST_NAME" value="<%=UsersCommand.REQ_PARAM_FIRST_NAME%>"/>
<c:set var="LAST_NAME" value="<%=UsersCommand.REQ_PARAM_LAST_NAME%>"/>
<c:set var="EMAIL" value="<%=UsersCommand.REQ_PARAM_EMAIL%>"/>
<c:set var="PASSWORD" value="<%=UsersCommand.REQ_PARAM_PASSWORD%>"/>
<c:set var="USER_SAVE_ERROR" value="<%=UsersCommand.REQ_ATR_USER_SAVE_ERROR%>"/>

<jsp:include page="/current-language"/>
<c:set var="LANGUAGE" value="<%=CommonControllerValues.CURRENT_LANGUAGE%>"/>

<fmt:setLocale value="${requestScope.get(LANGUAGE).iso639_1}"/>
<fmt:bundle basename="prop" prefix="main.">
<div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel"><fmt:message key="upp_title"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <c:if test="${requestScope.get(USER_SAVE_ERROR) != null}">
                    <div class="alert alert-danger" role="alert">
                        ${requestScope.get(PATTERN_ERROR_MSG)}
                    </div>
                </c:if>

                <%--Form--%>

                    <form action="admin?command=users" method="post">

                        <div class="form-row">
                            <div class="col form-group">
                                <label><fmt:message key="button_login"/></label>
                                <input type="text" class="form-control
                                <c:choose>
                                    <c:when test="${requestScope.get(PATTERN_ERROR_FIELD) == LOGIN || requestScope.get(USER_SAVE_ERROR) != null }">
                                        is-invalid
                                    </c:when>
                                    <c:when test="${(requestScope.get(LOGIN) != null) && (requestScope.get(PATTERN_ERROR_FIELD) != LOGIN) && (requestScope.get(LOGIN) != '')}">
                                        is-valid
                                    </c:when>
                                </c:choose>"
                                 name="login"
                                       <c:if test="${requestScope.get(LOGIN) != null}">value="${requestScope.get(LOGIN)}"</c:if>>
                                <c:if test="${requestScope.get(PATTERN_ERROR_FIELD) == LOGIN}">
                                    <div class="invalid-feedback">
                                        ${requestScope.get(PATTERN_ERROR_MSG)}
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="col form-group">
                                <label><fmt:message key="register_first_name"/></label>
                                <input type="text" class="form-control
                                <c:choose>
                                    <c:when test="${requestScope.get(PATTERN_ERROR_FIELD) == FIRST_NAME}">
                                        is-invalid
                                    </c:when>
                                    <c:when test="${(requestScope.get(FIRST_NAME) != null) && (requestScope.get(PATTERN_ERROR_FIELD) != FIRST_NAME) && (requestScope.get(FIRST_NAME) != '') }">
                                        is-valid
                                    </c:when>
                                </c:choose>"
                                name="first-name"
                                       <c:if test="${requestScope.get(FIRST_NAME) != null}">value="${requestScope.get(FIRST_NAME)}"</c:if>>
                                <c:if test="${requestScope.get(PATTERN_ERROR_FIELD) == FIRST_NAME}">
                                    <div class="invalid-feedback">
                                            ${requestScope.get(PATTERN_ERROR_MSG)}
                                    </div>
                                </c:if>
                            </div> <!-- form-group end.// -->
                            <div class="col form-group">
                                <label><fmt:message key="register_last_name"/></label>
                                <input type="text" class="form-control
                                <c:choose>
                                    <c:when test="${requestScope.get(PATTERN_ERROR_FIELD) == LAST_NAME}">
                                        is-invalid
                                    </c:when>
                                    <c:when test="${(requestScope.get(LAST_NAME) != null) && (requestScope.get(PATTERN_ERROR_FIELD) != LAST_NAME) && (requestScope.get(LAST_NAME) != '') }">
                                        is-valid
                                    </c:when>
                                </c:choose>"
                                       name="last-name"
                                       <c:if test="${requestScope.get(LAST_NAME) != null}">value="${requestScope.get(LAST_NAME)}"</c:if>>
                                <c:if test="${requestScope.get(PATTERN_ERROR_FIELD) == FIRST_NAME}">
                                    <div class="invalid-feedback">
                                            ${requestScope.get(PATTERN_ERROR_MSG)}
                                    </div>
                                </c:if>
                            </div> <!-- form-group end.// -->
                        </div> <!-- form-row end.// -->

                        <div class="form-group">
                            <label><fmt:message key="register_email_address"/></label>
                            <input type="email" class="form-control
                            <c:choose>
                                    <c:when test="${requestScope.get(PATTERN_ERROR_FIELD) == EMAIL || requestScope.get(USER_SAVE_ERROR) != null}">
                                        is-invalid
                                    </c:when>
                                    <c:when test="${(requestScope.get(EMAIL) != null) && (requestScope.get(PATTERN_ERROR_FIELD) != EMAIL) && (requestScope.get(EMAIL) != '') }">
                                        is-valid
                                    </c:when>
                                </c:choose>"
                                   name="email"
                                   <c:if test="${requestScope.get(EMAIL) != null}">value="${requestScope.get(EMAIL)}"</c:if>>
                            <small class="form-text text-muted"><fmt:message key="register_promice"/></small>
                            <c:if test="${requestScope.get(PATTERN_ERROR_FIELD) == EMAIL}">
                                <div class="invalid-feedback">
                                        ${requestScope.get(PATTERN_ERROR_MSG)}
                                </div>
                            </c:if>
                        </div> <!-- form-group end.// -->
                        <div class="form-group">
                            <label class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="gender" value="m" checked="checked">
                                <span class="form-check-label"> <fmt:message key="register_male"/> </span>
                            </label>
                            <label class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="gender" value="f">
                                <span class="form-check-label"> <fmt:message key="register_female"/></span>
                            </label>

                        </div> <!-- form-group end.// -->

                        <div class="form-group">
                            <label><fmt:message key="register_create_password"/></label>
                            <input class="form-control
                            <c:choose>
                                    <c:when test="${requestScope.get(PATTERN_ERROR_FIELD) == PASSWORD}">
                                        is-invalid
                                    </c:when>
                                    <c:when test="${(requestScope.get(PASSWORD) != null) && (requestScope.get(PATTERN_ERROR_FIELD) != PASSWORD) && (requestScope.get(PASSWORD) != '') }">
                                        is-valid
                                    </c:when>
                                </c:choose>"
                                   type="password" name="password">
                            <c:if test="${requestScope.get(PATTERN_ERROR_FIELD) == PASSWORD}">
                                <div class="invalid-feedback">
                                        ${requestScope.get(PATTERN_ERROR_MSG)}
                                </div>
                            </c:if>
                        </div> <!-- form-group end.// -->

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block"><svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-person-plus" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd" d="M8 5a2 2 0 1 1-4 0 2 2 0 0 1 4 0zM6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6 5c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10zM13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"/>
                            </svg> <fmt:message key="admin_btn_add_user"/></button>
                        </div> <!-- form-group// -->
                    </form>

                <%--Form END--%>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="btn_close"/></button>
            </div>
        </div>
    </div>
</div>
</fmt:bundle>