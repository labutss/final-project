<%--
  Created by IntelliJ IDEA.
  User: imarket.by
  Date: 09.01.2019
  Time: 1:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.content"/>
<html>
<head>
    <title>Admin main page</title>
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
<%@ include file="../../WEB-INF/adminbase.jspf"%>

<c:if test="${not empty message}">
    <div class="alert-danger">
        <h5>${message}</h5>
    </div>
</c:if>

<div class="container-fluid">
    <c:choose>
        <c:when test="${not empty accommodations}">
            <h5 class="font-weight-light">
                <fmt:message key="message.all.accommodations"/>
            </h5>
            <c:forEach var="accommodation" items="${accommodations}">
                <div class="card" style="background-color: #e3f2fd;">
                    <div class="card-header font-weight-bold">
                        <jsp:text>${accommodation.accommodationName}</jsp:text>
                        <form method="post" action="controller">
                            <input type="hidden" name="command" value="go-to-edit-accommodation-command">
                            <input type="hidden" name="accommodationId" value="${accommodation.entityId}">
                            <button type="submit" class="btn btn-primary" >
                                <fmt:message key="label.edit"/>
                            </button>
                        </form>

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
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="change-accommodation-availability-command">
                            <input type="hidden" name="accommodationId" value="${accommodation.entityId}">
                            <c:choose>
                                <c:when test="${accommodation.isAvailable == true}">
                                    <input type="hidden" name="newAvailable" value="false">
                                    <button type="submit" class="btn btn-danger" >
                                        <fmt:message key="label.make.unavailable"/>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="newAvailable" value="true">
                                    <button type="submit" class="btn btn-success" >
                                        <fmt:message key="label.make.available"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </div>
                </div>
                <br>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <br>
            <div class="alert alert-info">
                <h5>
                    <fmt:message key="label.no.accommodations"/>
                </h5>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
