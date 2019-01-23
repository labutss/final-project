<%--
  Created by IntelliJ IDEA.
  User: imarket.by
  Date: 07.01.2019
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="ru_RU"/>
<fmt:setBundle basename="resources.content"/>

<html>
<head>
    <title>First page</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<script>
    if(window.history.replaceState){
        window.history.replaceState(null, null,window.location.href);
    }
</script>
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
    <a class="navbar-brand"><fmt:message key="label.beauty.salon"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-end" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="/jsp/login.jsp"><fmt:message key="label.login"/></a>
            <a class="nav-item nav-link" href="/jsp/registration.jsp"><fmt:message key="label.registration" /></a>
        </div>
    </div>
</nav>

<br>
<h4 class="font-weight-normal text-center"><fmt:message key="message.hello"/> </h4>

<div class="container-fluid">
    <ctg:available-accommodations/>
</div>
</body>
</html>
