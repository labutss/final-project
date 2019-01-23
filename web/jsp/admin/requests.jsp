<%--
  Created by IntelliJ IDEA.
  User: imarket.by
  Date: 13.01.2019
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.content"/>
<html>
<head>
    <title>Requests</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <link rel="stylesheet" href="../../css/style.css">
    <link rel="stylesheet" href="../../css/jquery-ui.css">
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/jquery-3.3.1.js"></script>
    <script src="../../js/bootstrap.bundle.js"></script>
    <script src="../../js/jquery-1.12.4.js"></script>
    <script src="../../js/jquery-ui.js"></script>
</head>
<body>
<script>
    if(window.history.replaceState){
        window.history.replaceState(null, null,window.location.href);
    }
</script>
<%@ include file="../../WEB-INF/header.jspf"%>
<br>
<%@ include file="../../WEB-INF/adminbase.jspf"%>
<c:if test="${not empty wrongDateMessage}">
    <div class="alert-danger">
        <h5>
            <fmt:message key="message.invalid.date"/>
        </h5>
    </div>
</c:if>
<script>
    $( function() {
        $( "#datepicker" ).datepicker();
    } );
</script>
<div class="container-fluid">
    <h5 class="font-weight-light">
        <c:choose>
            <c:when test="${message == 'all'}">
                <fmt:message key="message.all.requests"/>:
            </c:when>
            <c:when test="${message == 'new'}">
                <fmt:message key="message.new.requests"/>:
            </c:when>
            <c:otherwise>
                <fmt:message key="message.clients.requests"/> ${message}:
            </c:otherwise>
        </c:choose>
    </h5>
    <c:choose>
        <c:when test="${not empty requests}">
            <c:forEach var="request" items="${requests}">
                <c:if test="${request.requestStatus == 0}">
                    <%@ include file="../../WEB-INF/adminrequest0.jspf"%>
                </c:if>
                <c:if test="${request.requestStatus == 1}">
                    <%@ include file="../../WEB-INF/adminrequest1.jspf"%>
                </c:if>
                <c:if test="${request.requestStatus == 2}">
                    <%@ include file="../../WEB-INF/adminrequest2.jspf"%>
                </c:if>
                <br>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <br>
            <div class="alert alert-info">
                <h5>
                    <fmt:message key="label.no.available.requests"/>
                </h5>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
