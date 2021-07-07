<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#button").click(function(){
			var name = $("#username").attr("value");
			var age = $("#age").attr("value");
			var user = {"username":name,"age":age};
			$.ajax({
				url:"${pageContext.request.contextPath}/json/json2",
				type:"get",
				data:user,
				success:function(data){
//					$("#span").attr("value",data.username+"添加成功")
					$("#span").text(data.username+"添加成功");
				},
				error:function(){
					$("#span").attr("value","添加失败")
				}
			})
		})
	})
</script>
<body>
	<div>
		<h1>增加用户</h1>
		姓名：<input type="text" id="username" >
		<br/>
		年龄：<input type="text" id="age">
		<br/>&nbsp;&nbsp;
		<input type="button" id="button" value="添加">
		&nbsp;&nbsp;<span id="span"></span>
	</div>
</body>
</html>