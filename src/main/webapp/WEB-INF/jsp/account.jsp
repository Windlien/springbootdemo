<%--
  Created by IntelliJ IDEA.
  User: alien
  Date: 2018/6/25
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table style="border: 0">
    <tbody>
    <c:if test="${!empty list}">
        <c:forEach items="${list}" var="u">
            <tr>
                <td>${u.accountId}</td>
                <td>${u.opTime}</td>
                <td>${u.dsTime}</td>
                <td>${u.bankCardType}</td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
</body>
</html>
