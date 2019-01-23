<%--
  Created by IntelliJ IDEA.
  User: imarket.by
  Date: 08.01.2019
  Time: 0:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.content"/>
<html>
<head>
    <title>Error page</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<script>
    if(window.history.replaceState){
        window.history.replaceState(null, null,window.location.href);
    }
</script>
    <br>
    <div class="container-fluid">
        <div class="alert-danger">
            <h5>
                <c:if test="${not empty pageContext.errorData.requestURI }">
                    Request from ${pageContext.errorData.requestURI} is failed
                    <br/>
                    Servlet name or type: ${pageContext.errorData.servletName}
                    <br/>
                    Status code: ${pageContext.errorData.statusCode}
                    <br/>
                    Exception: ${pageContext.errorData.throwable}
                    <br/>
                </c:if>
                <fmt:message key="message.something.went.wrong"/>
            </h5>
        </div>
        <form action="controller" name="goBack" method="post">
            <input type="hidden" name="command" value="go-to-previous-page-command">
            <button type="submit" class="btn btn-danger" >
                <fmt:message key="label.go.back"/>
            </button>
        </form>

    </div>
</body>
</html>
