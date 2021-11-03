<%-- 
    Document   : invalid
    Created on : Jan 19, 2021, 11:28:08 AM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Invalid Page</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${requestScope.MSG != null}">
                <h1>${requestScope.MSG}</h1>
            </c:when>
            <c:otherwise>
                <h1 style="text-align: center; margin-top: 140px;">ERROR! Try again!!</h1>
            </c:otherwise>
        </c:choose>
    </body>
</html>
