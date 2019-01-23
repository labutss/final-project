<%--
  Created by IntelliJ IDEA.
  User: imarket.by
  Date: 07.01.2019
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="ru_RU"/>
<fmt:setBundle basename="resources.content"/>
<html>
<head>
    <title>Login</title>
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
</nav>
<br>
<div class="container-fluid">
    <form name="loginForm" action="/controller" method="post">
        <input type="hidden" name="command" value="login-command">
    <div class="form-group font-weight-light">
        <label><fmt:message key="label.username"/>:</label>
        <input type="text" required="required" class="form-control" name="username" placeholder=<fmt:message key="label.username"/>>
    </div>
    <div class="form-group font-weight-light">
        <label><fmt:message key="label.password"/>:</label>
        <input type="password" required="required" class="form-control" name="password" placeholder=<fmt:message key="label.password"/>>
    </div>
    <div class="form-check">
        <button type="submit" class="btn btn-primary">
            <fmt:message key="label.log_in"/>
        </button>
    </div>
    </form>
</div>
<c:if test="${not empty message}">
    <div class="alert-danger">
        <h5>
            <fmt:message key="message.error.login"/>
        </h5>
    </div>
</c:if>
</body>
</html>
