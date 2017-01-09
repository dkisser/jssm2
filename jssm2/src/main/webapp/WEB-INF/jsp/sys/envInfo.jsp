<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>编辑环境参数页面</title>
    
	<meta charset="UTF-8">
    
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
<style type="text/css">

	body {
		margin-left: 3px;
		margin-top: 0px;
		margin-right: 3px;
		margin-bottom: 0px;
	}
</style>
  </head>
  
  <body>
	<div id="EnvMain">
	<form id="envInfoForm" method="post">
		<table width="100%" border="0" style="border-spacing:10px;">
			<tr height="15"></tr>
			<tr><td width="15%"></td><td align="right">参数名：</td><td><input id="envKey" name="envKey" style="width: 200px;"/></td><td></td></tr>
			<tr><td></td><td align="right">参数值：</td><td><input type="text"  id="envValue" name="envValue" style="width: 200px;"/></td><td></td></tr>
		</table>
		<br><br>
	<table border="0">
			<tr>
			<td width='140px'></td>
			<td width='100px' align="center"><a id="saveBtn_env" href="#" >保存</a>  </td>
			<td width='100px' align="center"><a id="cancelBtn_env" href="#" >取消</a>  </td>
			<td></td>
		</tr>
	</table>
	</form>
	</div>
	
	<script type="text/javascript">
		$("#envKey").textbox({
			iconCls: 'icon-lock',  
			iconAlign: 'right',
			readonly: true,
			value: "${editJEnv.envKey}"
		});
		
		$("#envValue").textbox({
			iconCls: "icon-more",
			iconAlign: 'right',
			multiline: true,
			required: true,
			width: 200,
			height: 100,
			value: "${editJEnv.envValue}"
		});
		
		$("#saveBtn_env").linkbutton({
			iconCls: "icon-save"
		});
		
		$("#cancelBtn_env").linkbutton({
			iconCls: "icon-cancel"
		});
		
		$("#cancelBtn_env").click(function(){
			$("#envInfoDiv").dialog("close");
		});
		
		$("#saveBtn_env").click(function(){
			$.messager.progress();
			var envId="${editJEnv.id}";
			var envValue=$("#envValue").val();
			$("#envInfoForm").form("submit",{
				url: getContextPath()+"/sys/updateEnvInfo.do",
				
				onSubmit: function(param){
					param.envId=envId;
					if(envValue.length>100){
						$.messager.progress('close');
						$.messager.alert('提示','参数值字符长度不能超过100！','info');
						return false;
					}
					var isValid = $(this).form('validate');
					if (!isValid){
						$.messager.progress('close');
						$.messager.alert('提示','请按照要求填写表单','info');  
					}
					return isValid;
				},
				
				success: function(){
					$.messager.progress("close");
					$("#envInfoDiv").dialog("close");
					$.messager.alert("提示","修改成功！","info");
				},
				
			});
		});
		
		//$("#envInfoForm").form("load","${editJEnv}");
	</script>
  </body>
</html>
