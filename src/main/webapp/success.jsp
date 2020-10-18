<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 28.09.2020
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.mischenkov.session.SessionVariable" %>
<c:set var="INFO_MSG" value="<%=SessionVariable.SESSION_INFO_MSG%>"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Register Success</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="static/css/main.css">
    <link rel="stylesheet" href="static/css/registration.css">
    <link rel="stylesheet" href="static/css/lang.css">
</head>
<body>
<header>
    <jsp:include page="WEB-INF/view/pattern/header.jsp"/>
</header>


<div class="content-side">
    <div class="container">

        <div class="alert alert-success" role="alert">
            "${sessionScope.get(INFO_MSG)}" <h5 id="time" class="float-right text-danger"></h5>
            <%
                session.setAttribute(SessionVariable.SESSION_INFO_MSG, null);
            %>
        </div>

    </div>
</div>

<script>
    var i = 10;
    function time(){
        document.getElementById("time").innerHTML = i;
        i--;
        if (i < 0) location.href = "index.jsp";
    }
    time();
    setInterval(time, 1000);
</script>

<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<jsp:include page="WEB-INF/view/pattern/bootstrap_js.jsp"/>
</body>
</html>
