<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.1.min.js"></script>
</head>
<body>
<%--星星跟随效果:星星.js和span--%>
<script src="${pageContext.request.contextPath}/js/星星.js"></script>
<span class="js-cursor-container"></span>
<div>
    <h1>上传用户</h1>
</div>
<br/>
<form action="${pageContext.request.contextPath}/file/upload" method="post" enctype="multipart/form-data">
    <%--multiple="multiple"  加上这个就可以选择多个文件--%>
    选择文件：<input type="file" name="file1" multiple="multiple"><br/><br/>
    选择文件：<input type="file" name="file2"><br/><br/>
    选择文件：<input type="file" name="file3"><br/><br/>
    <input type="submit" value="上传">
</form>
</body>
</html>