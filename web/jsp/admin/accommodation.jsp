<%--
  Created by IntelliJ IDEA.
  User: imarket.by
  Date: 09.01.2019
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.content"/>
<html>
<head>
    <title>Accommodation</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/jquery-3.3.1.js"></script>
    <script src="../../js/bootstrap.bundle.js"></script>
</head>
<body>
<%@ include file="../../WEB-INF/header.jspf"%>
<br>
<script>
    if(window.history.replaceState){
        window.history.replaceState(null, null,window.location.href);
    }
</script>
<div class="container-fluid">
    <form action="controller" method="post">
        <input type="hidden" name="command" value="${command}">
        <input type="hidden" value="${accommodation}" name="accommodation">
        <input type="hidden" value="${accommodation.entityId}" name="accommodationId">

        <h4 class="font-weight-normal text-center">
            <c:choose>
                <c:when test="${command == 'add-accommodation-command'}">
                    <fmt:message key="label.new.accommodation"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="label.edit.accommodation"/>
                </c:otherwise>
            </c:choose>
        </h4>
        <c:if test="${not empty message}">
            <div class="alert-danger">
                <h5>
                    <fmt:message key="message.wrong.accommodation.data"/>
                </h5>
            </div>
        </c:if>
        <div class="form-group font-weight-light">
            <label><fmt:message key="label.accommodation.name"/>:</label>
            <input type="text" required="required" autocomplete="off" pattern="[A-Za-z-\s]{4,30}" class="form-control" name="accommodationName" value="${accommodation.accommodationName}" placeholder="<fmt:message key="label.accommodation.name"/>">
        </div>
        <div class="form-group font-weight-light">
            <label><fmt:message key="label.accommodation.description"/>:</label>
            <input type="text" required="required" autocomplete="off" pattern="[\d\w\s-,.!:;()]{10,100}" class="form-control" name="accommodationDescription" value="${accommodation.accommodationDescription}" placeholder="<fmt:message key="label.accommodation.description"/>">
        </div>
        <div class="form-group font-weight-light">
            <label><fmt:message key="label.accommodation.cost"/>:</label>
            <input type="text" required="required" autocomplete="off" <%--pattern="^([1-9]{1}[0-9]{0,5}(\\.[0-9]{0,2})?|0(\\.[1-9]{1}([0-9]{1})?)|0(\\.0[1-9]{1}))$"--%> class="form-control" name="accommodationCost" value="${accommodation.accommodationCost}" placeholder="<fmt:message key="label.accommodation.cost"/>">
        </div>
        <div class="form-check">
            <button type="submit" class="btn btn-primary">
                <c:choose>
                    <c:when test="${command == 'add-accommodation-command'}">
                        <fmt:message key="label.add.accommodation"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="label.edit"/>
                    </c:otherwise>
                </c:choose>
            </button>
        </div>
    </form>
</div>
</body>
</html>
