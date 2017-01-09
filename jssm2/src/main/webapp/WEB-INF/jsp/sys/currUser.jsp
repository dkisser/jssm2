<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>当前用户信息</title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>

<style type="text/css">
<!--
body {
	margin-right: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

</style>

</head>

<body>
	<table align="center" border="0" style="border-collapse:separate;border-spacing:15px;">
		<tr>
			<td align="right">姓名：</td>
			<td><input type="text" class="easyui-textbox" size="30" data-options="iconCls: 'icon-lock',readonly:true" style="text-align:center;" value=${vUser.name} /></td>
		</tr>
		<tr>
			<td align="right">登录名：</td>
			<td><input type="text" class="easyui-textbox" size="30" data-options="iconCls: 'icon-lock',readonly:true" style="text-align:center;" value=${vUser.uname} /></td>
		</tr>
<!-- 		<tr> -->
<!-- 			<td align="right">邮箱：</td> -->
<!-- 			<td><input type="text" class="easyui-textbox" size="30" data-options="iconCls: 'icon-lock',readonly:true" style="text-align:center;" value=${loginInfo.email} /></td> -->
<!-- 		</tr> -->
		<tr>
			<td align="right">角色：</td>
			<td><input type="text" class="easyui-textbox" size="30" data-options="iconCls: 'icon-lock',readonly:true" style="text-align:center;" value=${vUser.role} /></td>
		</tr>
		<tr>
			<td align="right">职位：</td>
			<td><input type="text" class="easyui-textbox" size="30" data-options="iconCls: 'icon-lock',readonly:true" style="text-align:center;" value=${vUser.zw} /></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><a class="easyui-linkbutton" iconCls="icon-no" onclick="javascript:$('#currUser').dialog('close')">关闭</td>
		</tr>
	</table>

</body>
</html>
