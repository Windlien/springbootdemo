<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">

</script>
<body>
    <table border="1">
        <tbody>
            <tr>
                <th>姓名</th>
                <th>年龄</th>
            </tr>
            <c:if test="${!empty users}">
                <c:forEach items="${users}" var="u">
                <tr>
                    <td>${u.username}</td>
                    <td>${u.age}</td>
                </tr>
                </c:forEach>
            </c:if>
        </tbody>
    </table>

</body>
</html>