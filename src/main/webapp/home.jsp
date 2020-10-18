<%@ page contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@taglib prefix="mytag" uri="/WEB-INF/ltd/custom.tld" %>

<%@ page import="com.mischenkov.controller.MainController" %>
<c:set var="SERVICES" value="<%=MainController.REQ_ATTR_SERVICES%>"/>

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

    <style>
        .general-menu a{
            font-weight: bold;
            color: white;
        }
        .general-menu a:hover{
            text-decoration: none;
        }
        .general-menu .card {
            transition: .2s linear;
        }
        .general-menu .card:hover {
            box-shadow: inset 1px -1px 0px 18px #02657b5e;
        }
    </style>

</head>
<body>

<header>
    <jsp:include page="WEB-INF/view/pattern/header.jsp"/>
</header>


<div class="container pt-4">

    <div class="row">
        <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12">

            <jsp:include page="left-menu"/>

        </div>
        <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12">
            <div class="card">
                <div class="card-body">

                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h3>General Page</h3>
                    </div>

                    <div class="row">

                        <mytag:plates services="${requestScope.get(SERVICES)}"/>

                    </div>

                </div>
            </div>
        </div>
    </div>


</div>

<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<jsp:include page="WEB-INF/view/pattern/bootstrap_js.jsp"/>
</body>
</html>