<%@ page contentType="text/html;charset=UTF-8"  %>
<%--引入Jquery--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.5.2/jquery.min.js"></script>
<%--引入EasyUI--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<%--引入EasyUI的中文国际化js，让EasyUI支持中文--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
<%--引入EasyUI的样式文件--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-easyui-1.5.2/themes/default/easyui.css" type="text/css"/>
<!-- 引入EasyUI的图标样式文件-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/jquery-easyui-1.5.2/themes/icon.css" type="text/css"/>
<html>
<script type="text/javascript">
  <%--对表格的展示有两种方式，一种在js控制，一种直接在table标签控制--%>
  $(document).ready(function(){
    $("#dg").datagrid({
      title:'查询用户',
      url:'${pageContext.request.contextPath}/user/getUser',
      rownumbers:true,
      pagination:true,
      fitColumns:true,
      columns:[[
        {field:'id',title:'ID',width:'200',align:'center'},
        {field:'username',title:'姓名',width:'80',align:'center'},
        {field:'age',title:'年龄',width:'80',align:'center'}
      ]]
    })
  })
  function doSearch(){
    $('#dg').datagrid('load',{
      username: $('#username').val(),
      age: $('#age').val()
    });
  }
</script>
<head>
    <title>EasyUI 查询用户</title>
</head>
<body>
<h2>查询用户</h2>
<p>The DataGrid is created from markup, no JavaScript code needed.</p>
<div style="margin:20px 0;"></div>

<table id="dg" class="easyui-datagrid" title="Basic DataGrid" style="width:700px;height:250px"
       data-options="singleSelect:true,collapsible:true,toolbar:'#tb',url:'',method:'get'">
  <%--<thead>--%>
    <%--<tr>--%>
      <%--<th data-options="field:'id',width:120">ID</th>--%>
      <%--<th data-options="field:'username',width:100">姓名</th>--%>
      <%--<th data-options="field:'age',width:80,align:'right'">年龄</th>--%>
    <%--</tr>--%>
  <%--</thead>--%>
</table>
<div id="tb" style="padding:3px">
  <span>姓名:</span>
  <input id="username" style="line-height:26px;border:1px solid #ccc">
  <span>年龄:</span>
  <input id="age" style="line-height:26px;border:1px solid #ccc">
  <a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">Search</a>
</div>

</body>
</html>
