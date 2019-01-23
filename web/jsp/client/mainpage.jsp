<%--
  Created by IntelliJ IDEA.
  User: imarket.by
  Date: 08.01.2019
  Time: 0:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.content"/>
<html>
<head>
    <title>Client main page</title>
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
<div class="container-fluid">
    <c:choose>
        <c:when test="${not empty availableAccommodations}">
            <h5 class="font-weight-light"><fmt:message key="label.available.accommodations"/> </h5>
            <c:forEach var="accommodation" items="${availableAccommodations}">
                <div class="card" style="background-color: #e3f2fd;">
                    <div class="card-header font-weight-bold">
                        <jsp:text>${accommodation.accommodationName}</jsp:text>
                    </div>
                    <div class="card-body">
                        <p class="card-text font-weight-normal">
                            <jsp:text>${accommodation.accommodationDescription}</jsp:text>
                        </p>
                    </div>
                    <div class="card-footer font-weight-bold">
                        <fmt:message key="label.accommodation.cost"/><jsp:text> ${accommodation.accommodationCost}$</jsp:text>
                        <br>
                        <fmt:message key="label.accommodation.mark"/><jsp:text> ${accommodation.accommodationMark}</jsp:text>
                    </div>
                </div>
                <br>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <br>
            <div class="alert alert-info">
                <h5>
                    <fmt:message key="label.no.available.accommodations"/>
                </h5>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
