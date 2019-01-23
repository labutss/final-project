<%--
  Created by IntelliJ IDEA.
  User: imarket.by
  Date: 12.01.2019
  Time: 0:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.content"/>
<html>
<head>
    <title>All users view</title>
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

<div class="container-fluid">
    <h5 class="font-weight-light"><fmt:message key="label.view.users"/>:</h5>
    <c:choose>
        <c:when test="${not empty users}">
            <c:forEach var="user" items="${users}">
                <div class="d-flex bd-highlight text-center">
                    <div class="p-md-2 flex-fill border-0">${user.login}</div>
                    <c:choose>
                        <c:when test="${user.userStatus == 0}">
                            <div class="page-link flex-fill border-0">
                                <fmt:message key="label.admin"/>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="page-link flex-fill border-0">
                                <a href="controller?command=admin-view-clients-requests-command&userId=${user.entityId}&username=${user.login}">
                                    <fmt:message key="label.view.users.requests"/>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <br>
            <div class="alert alert-info">
                <h5>
                    <fmt:message key="label.no.users"/>
                </h5>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
