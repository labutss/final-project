<%--
  Created by IntelliJ IDEA.
  User: imarket.by
  Date: 10.01.2019
  Time: 0:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.content"/>
<html>
<head>
    <title>Request</title>
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
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
    <a class="navbar-brand" href="#"><fmt:message key="label.beauty.salon"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="controller?command=logout-command"><fmt:message key="label.logout"/></a>
            </li>
        </ul>
    </div>
</nav>
<br>
<div class="container-fluid">
    <form action="controller" name="addRequest" method="post">
        <input type="hidden" name="command" value="add-request-command">
        <script>
            $( function() {
                $( "#datepicker" ).datepicker();
            } );
        </script>
        <div class="form-group font-weight-light">
            <label><fmt:message key="label.date.expected"/>:</label>
            <input type="text" required="required" autocomplete="off" name="date" placeholder="mm/dd/yyyy" id="datepicker" value="${date}">
        </div>
        <br>
        <c:if test="${not empty message}">
            <div class="alert-danger">
                <h5>
                    <c:choose>
                        <c:when test="${message == 'invalid date'}">
                            <fmt:message key="message.invalid.date"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="message.no.accommodations"/>
                        </c:otherwise>
                    </c:choose>
                </h5>
            </div>
        </c:if>
        <div class="form-group font-weight-light">
            <label><fmt:message key="label.choose.accommodations"/>:</label>
        </div>
        <c:choose>
            <c:when test="${not empty availableAccommodations}">
                <c:forEach var="accommodation" items="${availableAccommodations}">
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" name="accommodationId" value="${accommodation.entityId}" class="custom-control-input" id="${accommodation.entityId}">
                        <label class="custom-control-label" for="${accommodation.entityId}">${accommodation.accommodationName}</label>
                    </div>
                    <br>
                </c:forEach>
                <button type="submit" class="btn btn-primary">
                    <fmt:message key="label.send.request"/>
                </button>
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
        <br>
    </form>
</div>
</body>
</html>
