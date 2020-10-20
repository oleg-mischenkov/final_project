<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 01.10.2020
  Time: 0:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.mischenkov.controller.CommonControllerValues" %>
<%@ page import="com.mischenkov.controller.admin.UsersCommand" %>
<%@ page import="static com.mischenkov.controller.admin.UsersCommand.REQ_ATR_PATTERN_ERROR_MSG" %>
<c:set var="USER_MAP" value="<%=UsersCommand.REQ_ATR_USER_MAP%>"/>
<c:set var="START_POSITION" value="<%=UsersCommand.REQ_ATR_START_POSITION%>"/>
<c:set var="COUNT" value="<%=UsersCommand.REQ_ATR_COUNT%>"/>
<c:set var="PAGINATION_URLS" value="<%=UsersCommand.REQ_ATR_PAGINATION_URLS%>"/>
<c:set var="ERROR_FIELD" value="<%=UsersCommand.REQ_ATR_PATTERN_ERROR_FIELD%>"/>

<jsp:include page="/current-language"/>
<c:set var="LANGUAGE" value="<%=CommonControllerValues.CURRENT_LANGUAGE%>"/>

<fmt:setLocale value="${requestScope.get(LANGUAGE).iso639_1}"/>
<fmt:bundle basename="prop" prefix="main.">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin: Users</title>

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
                <h3><fmt:message key="admin_user_user_management"/></h3>
                <a class="btn btn-info" href="" data-toggle="modal" data-target="#staticBackdrop" id="addUserButton">
                    <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-person-plus" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M8 5a2 2 0 1 1-4 0 2 2 0 0 1 4 0zM6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6 5c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10zM13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"/>
                    </svg><fmt:message key="admin_btn_add_user"/></a>
            </div>

            <div>
                <table class="table table-hover table-striped border-bottom">
                    <thead class="thead-dark">
                        <th scope="col">#</th>
                        <th scope="col"><fmt:message key="register_login"/></th>
                        <th scope="col"><fmt:message key="register_first_name"/></th>
                        <th scope="col"><fmt:message key="register_last_name"/></th>
                        <th scope="col"><fmt:message key="admin_gender"/></th>
                        <th scope="col"><fmt:message key="admin_registration"/></th>
                        <th scope="col"><fmt:message key="admin_role"/></th>
                        <th scope="col"><fmt:message key="admin_active"/></th>
                    </thead>
                    <tbody>
                        <c:forEach var="element" items="${requestScope.get(USER_MAP)}" varStatus="num">
                            <tr <c:if test="${element.key.active == false}">class="table-danger"</c:if>>
                                <td>
                                    ${num.index + requestScope.get(START_POSITION)}
                                </td>
                                <td>
                                    <c:out value="${element.key.login}"/>
                                </td>
                                <td>
                                    <c:out value="${element.key.firstName}"/>
                                </td>
                                <td>
                                    <c:out value="${element.key.lastName}"/>
                                </td>
                                <td>
                                    <c:out value="${element.key.gender}"/>
                                </td>
                                <td>
                                    <c:out value="${element.key.createTime}"/>
                                </td>
                                <td>
                                    <c:out value="${element.value}"/>
                                </td>
                                <td>
                                    <div id="${element.key.id}" class="custom-control custom-switch">
                                        <input type="checkbox" class="custom-control-input"
                                               <c:if test="${element.key.active == true}">checked</c:if> id="customSwitch1-${num.index + requestScope.get(START_POSITION) + 100}">
                                        <label class="custom-control-label" for="customSwitch1-${num.index + requestScope.get(START_POSITION) + 100}"></label>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <%
                    StringBuilder stringBuilder = new StringBuilder();
                    int currentPosition = (int) request.getAttribute(UsersCommand.REQ_ATR_START_POSITION)
                            / (int) request.getAttribute(UsersCommand.REQ_ATR_COUNT);
                    String urlPattern = "admin?command=users";
                    String notActivePosition = "<li class=\"page-item\"><a class=\"page-link\" href=\"%s\">%s</a></li>";
                    String activePosition = "<li class=\"page-item active\" aria-current=\"page\">\n" +
                            "      <a class=\"page-link\" href=\"%s\">%s<span class=\"sr-only\">(current)</span></a>\n" +
                            "    </li>";

                    String[] pageUrls = (String[]) request.getAttribute(UsersCommand.REQ_ATR_PAGINATION_URLS);

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

        </main> <!-- site content END.-->

    </div>

</div> <!--container end.//-->


<!-- Modal -->
<jsp:include page="../pattern/admin-modal-user-add.jsp"/>


<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<jsp:include page="../pattern/bootstrap_js.jsp"/>

<script type="text/javascript">
    jQuery(document).ready(function ($) {
        $(".custom-control-input").click(function(){
            var finalUrl;

            var inputPosition;
            var userId;
            if($(this).attr("checked") == "checked") {
                inputPosition = 0;
            } else {
                inputPosition = 1;
            }

            userId = $(this).parent().attr('id');

            var pathName = $(location).attr('href');

            var findUser = pathName.indexOf("&user=") == -1 ? pathName.length : pathName.indexOf("&user=");
            var findActive = pathName.indexOf("&active=") == -1 ? pathName.length : pathName.indexOf("&active=");
            var findMin = findUser > findActive ? findActive : findUser;

            if (pathName.length > findMin) {
                pathName = pathName.slice(0, findMin);
            }

            var finalUrl = pathName + "&user=" + userId + "&active=" + inputPosition;
            window.location.href = finalUrl;
        });
    });
</script>

<script type="text/javascript">
    <%
        boolean errorFlag = false;
        if ( request.getAttribute(REQ_ATR_PATTERN_ERROR_MSG) != null ) {
            errorFlag = true;
        }
    %>
    <%=
        "const errorFlag = " + errorFlag + ";"
    %>
    $(document).ready(function(){
        if(errorFlag) {
            $('#staticBackdrop').modal('show');
        }
    });
</script>

</body>
</html>
</fmt:bundle>