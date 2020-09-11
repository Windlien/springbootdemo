<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<script  src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script >
	$().ready(
			function () {
				//缩小图片暂时没有更好办法
				var newImage = new Image();
				newImage.src = $("#img").attr("src");
				$("#img").attr("width",newImage.width*0.1);
			}
	)
	function addUser(){
		$("#addUser").css("color","red");
		setTimeout("o()",1500);
	}
	function o(){
		window.open("${pageContext.request.contextPath}/user/user")
	}
	function upload(){
		window.open("${pageContext.request.contextPath}/file/file")
	}
	function getAllUser(){
		window.open("${pageContext.request.contextPath}/user/getAllUser")
	}
	function getUserUI(){
		window.open("${pageContext.request.contextPath}/user/getUserUI")
	}
	function spider(){
		$.ajax({
			url:"${pageContext.request.contextPath}/spider",
			success:function (data) {
				alert('success');
            }
		})
	}
	function find() {
		window.open("${pageContext.request.contextPath}/find");
    }
</script>
<body>
	<div>
		<h1 style="color: bisque">WELCOME TO SpringBoot SpringMVC4.7 Hibernate4</h1>
	</div>
	<br/>

	<ul style="width: 10%; cursor: pointer">
		<li><a href="../../img/ad.jpg">src下的图片</a></li>
		<li><a href="db/BSYS/gray01.jpg" target="_blank">预览<img src="${pageContext.request.contextPath}/img/1.jpg"  style="width:100px;height:100px" ></a></li>
		<li id="addUser" onclick="addUser()">增加用户</li>
		<li id="getAllUser" onclick="getAllUser()">查询用户</li>
		<li id="getUserUI" onclick="getUserUI()">EasyUI查询用户</li>
		<li id="upload" onclick="upload()">上传文件</li>
		<li id="spider" onclick="spider()"  >爬虫-银监局</li>
		<li id="find" onclick="find()"  >找出数据</li>
	</ul>
	<div>
		<img id="img" src="${pageContext.request.contextPath}/img/1.jpg" >
	</div>
</body>
</html>