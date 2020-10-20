<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 30.09.2020
  Time: 1:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin</title>

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

            </main> <!-- site content END.-->

        </div>

    </div> <!--container end.//-->

<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<jsp:include page="../pattern/bootstrap_js.jsp"/>
</body>
</html>
