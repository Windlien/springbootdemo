<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript">
	function addUser(){
		var form = document.forms[0];
		form.action="${pageContext.request.contextPath}/user/addUser";
		form.method="post";
		form.submit();
	}
	function add(){
		var username = $("#username").attr("value");
		var age =$("#age").attr("value");
		var user = {username:username,age:age};
		$.ajax({
			url:"${pageContext.request.contextPath}/user/addUser",
			type:"post",
			data:user,
			success:function(data){     //data也可以写成其他任意字母    ajax里边也是json格式的对
				alert("username--->"+data.username+" age--->"+data.age);
				$("#u").text(data.username);
				$("#a").html(data.age);
//					$("#a").attr("value",data.age)
				alert("保存成功");
			},
			error:function(){
				alert("111")
			}
		});
	}
</script>
<body>
	<div>
		<h1>添加用户</h1>
	</div>
	<br/>
<form action="">
	姓名：<input type="text" id="username" name="username">
	年龄：<input type="text" id="age" name="age">
	      <%--<input type="button" value="添加" onclick="addUser()" >--%>
	<input type="button" value="添加" onclick="add()" >
	<br/>
	<span id="u" ></span>
	<br/>
	<span id="a" ></span>
</form>
</body>
</html>