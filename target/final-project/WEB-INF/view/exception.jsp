<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 17.10.2020
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.mischenkov.controller.ExceptionController" %>
<c:set var="EXCEPTION_TRACE" value="<%=ExceptionController.REQ_PARAM_EXCEPTION_STACK_TRACE%>"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>500</title>

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
                        <h4>500</h4>
                    </div>

                    <div class="col">
                        ${requestScope.get(EXCEPTION_TRACE)}
                    </div>

                </div>
            </div>
        </div>


    </div>


    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <jsp:include page="pattern/bootstrap_js.jsp"/>
</body>
</html>