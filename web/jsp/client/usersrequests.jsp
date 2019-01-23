<%--
  Created by IntelliJ IDEA.
  User: imarket.by
  Date: 13.01.2019
  Time: 0:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.content"/>
<html>
<head>
    <title>Users requests</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/jquery-3.3.1.js"></script>
    <script src="../../js/bootstrap.bundle.js"></script>
</head>
<body>
<script>
    if(window.history.replaceState){
        window.history.replaceState(null, null,window.location.href);
    }
</script>
<%@ include file="../../WEB-INF/header.jspf"%>
<br>
<%@ include file="../../WEB-INF/clientbase.jspf"%>
<c:if test="${not empty moneyMessage}">
    <div class="alert-danger">
        <h5>
            <fmt:message key="message.not.enough.money"/>
        </h5>
    </div>
</c:if>
<div class="container-fluid">
    <c:choose>
        <c:when test="${not empty requests}">
            <h5 class="font-weight-light"><fmt:message key="label.your.requests"/>:</h5>
            <c:forEach var="request" items="${requests}">
                <c:if test="${request.requestStatus == 0}">
                    <%@ include file="../../WEB-INF/clientrequest0.jspf"%>
                </c:if>
                <c:if test="${request.requestStatus == 1}">
                    <%@ include file="../../WEB-INF/clientrequest1.jspf"%>
                </c:if>
                <c:if test="${request.requestStatus == 2}">
                    <%@ include file="../../WEB-INF/clientrequest2.jspf"%>
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
