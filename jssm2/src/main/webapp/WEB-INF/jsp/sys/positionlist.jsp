<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'positionlist.jsp' starting page</title>
    
    <meta charset="UTF-8">
    
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  </head>
  
  <body>
	<div id="dlgPositionMain">
	<!--  <div>内容测试内容测试内容测试内容测试</div>-->
	<table id ="tbPositionList"></table>
	<div id="divPositiontb" style="padding:5px;">
		<table style="padding-bottom:5px;padding-left:5px">
			<tr>
				<td><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" style="color:#6666FF" onclick="objPosition.add()">新增</a></td>
				<td width="50%"></td>
				<td><img src="${pageContext.servletContext.contextPath }/js/easyui/themes/icons/search.png"
					 style="border: 0;vertical-align: middle;" />&nbsp;&nbsp;模糊查找:</td>
				<td><input id="SearchPositionList" name="pname" ></td>
			</tr>	
		</table>
	</div>
</div>

<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/jssm/position.js"></script>
  </body>
</html>
