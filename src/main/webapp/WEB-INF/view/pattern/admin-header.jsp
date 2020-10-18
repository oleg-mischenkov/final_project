<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 30.09.2020
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmr" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.mischenkov.cookie.CookieCommon" %>
<%@page import="com.mischenkov.listener.ContextVariable" %>
<%@page import="com.mischenkov.session.SessionVariable" %>
<c:set var="PERMISSION" value="<%=SessionVariable.SESSION_USER_PERMISSION%>"/>
<c:set var="CURRENT_LANGS" value="<%=ContextVariable.CONTEXT_CURRENT_LANGS%>"/>
<c:set var="COOKIE_LANGUAGE" value="<%=CookieCommon.COOKIE_LANGUAGE%>"/>
<c:set var="LANGUAGE" value="${cookie.get(COOKIE_LANGUAGE).value}"/>

<fmt:setLocale value="${LANGUAGE}"/>
<fmt:bundle basename="prop" prefix="main.">
<nav class="navbar navbar-dark bg-dark fixed-top">

    <a class="navbar-brand" href="admin?command=index">Final Project: ADMIN</a>

    <ul class="nav justify-content-end">
        <li class=" nav-item">
            <div class="nav-link pb-0">
                <form action="language" method="post" id="lang">
                    <select class="custom-select custom-select-sm pr-3 pl-1" name="lang">
                        <c:forEach var="element" items='${applicationScope.get(CURRENT_LANGS)}'>
                            <c:set var="lang" value="${element.iso639_1}"/>
                            <option value="${lang}" <c:if test="${LANGUAGE.equals(lang)}">selected</c:if>><c:out value="${lang}"/></option>
                        </c:forEach>
                    </select>
                </form>
                <script type="text/javascript">
                    const cookie_language = "${LANGUAGE}";
                </script>
            </div>
        </li>
        <li class="navbar-nav active">
            <a class="nav-link" href="logout"><fmr:message key="log_out"/></a>
        </li>
    </ul>

</nav>
</fmt:bundle>