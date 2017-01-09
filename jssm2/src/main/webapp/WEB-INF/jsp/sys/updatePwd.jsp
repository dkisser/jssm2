<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'updatePwd.jsp' starting page</title>

<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>

</head>

<body>
	<form id="updatePwdForm">
		<table align="center" style="border-collapse:separate;border-spacing:10px;">
			<tr>
				<td align="right">用户名：</td>
				<td><input type="text" class="easyui-textbox" data-options="iconCls:'icon-man',readonly:true" size="20" value=${loginInfo.name } /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">登录名：</td>
				<td><input id="uname" name="username" type="text" class="easyui-textbox" data-options="iconCls:'icon-man',readonly:true" size="20" value=${loginInfo.uname } /></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">原密码：</td>
				<td><input id="oldpwd" name="oldpwd" type="password"  size="20" /></td>
				<td id="OlpwdAlert" style="color:#FF0000">*</td>
			</tr>
			<tr>
				<td align="right">新密码：</td>
				<td><input id="newpwd" name="newpwd" type="password" size="20" /></td>
				<td id="NewpwdAlert" style="color:#FF0000">*</td>
			</tr>
			<tr>
				<td align="right">确认新密码：</td>
				<td><input id="renewpwd" type="password" size="20" /></td>
				<td id="NewpwdAlert1" style="color:#FF0000">*</td>
			</tr>
		</table>
		<br>
		<br>
		<table align="center">
			<tr>
				<td align="center"><a id="saveBtn_updatePwd" href="#">确认</a></td>
				<td width="50%"></td>
				<td><a id="cancel_updatePwd" href="#">取消</a></td>
			</tr>
		</table>
	</form>

	<script type="text/javascript">
		$.extend($.fn.validatebox.defaults.rules, {
			equals : {
				validator : function(value, param) {
					return value == $(param[0]).val();
				},
				message : '两次输入的密码不一致，请重新输入！'
			}
		});
		$("#oldpwd").textbox({
			required : true,
			iconCls:'icon-more'
		});

		$("#newpwd").textbox({
			required : true,
			validType : 'length[5,12]',
			iconCls:'icon-more'
		});

		$("#renewpwd").textbox({
			required : true,
			validType : [ 'length[5,12]', "equals['#newpwd']" ],
			delay : 300,
			iconCls:'icon-more'
		});

		$("#saveBtn_updatePwd").linkbutton({
			iconCls : 'icon-save'
		});

		$("#cancel_updatePwd").linkbutton({
			iconCls : 'icon-cancel'
		});

		$("#cancel_updatePwd").click(function() {
			$("#pwddialog").dialog("close");
		});

		$("#saveBtn_updatePwd").click(function() {
			$.messager.progress();
			$("#updatePwdForm").form("submit", {
				url : getContextPath() + "/user/updatePwd.do", //表单提交地址
				onSubmit : function(param) {
					var isvalidate = $(this).form("validate");
					if (!isvalidate) {
						$.messager.progress('close');
						$.messager.alert("提示", "请按照要求填写表单！", "info");
						return false;
					}
					return isvalidate;
				},
				success : function(result) {
					$.messager.progress('close');
					var message = "error";
					switch (result) {
					case '1': {
						$("#pwddialog").dialog("close");
						message = "修改成功！";
						break;
					}
					case '2': {
						message = "原密码错误！";
						break;
					}
					case '3': {
						message = "新密码不能原密码相同！";
						break;
					}
					case '4': {
						message = "修改失败！";
						break;
					}
					default: {
						break;
					}
					}
					$.messager.alert("提示", message, "info");

				}
			});
		});
	</script>
</body>
</html>
