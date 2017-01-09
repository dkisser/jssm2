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
<meta charset="utf-8">
<title>案卷目录</title>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
</head>
<body>
	<script type="text/javascript">
		$("#ajmlId").textbox({
			width : 300,
			required : true,
			value : "${id}",
		});
		$("#ajmlList").textbox({
			width : 300,
			required : true,
			height : 120,
			prompt:"注意：导入目录数和存在目录数总数不得超过99条!",
			multiline : true,
		});

		var pcdm = '${pcdm}';
		$("#ajmlSubmit").click(function() {
			$("#fAjmlInfoList").form('submit', {
				url : getContextPath() + "/catalog/importAjuanMl.do",
				onSubmit : function(param) {
					var isValid = $(this).form('validate');
					if (isValid == false) {
						$.messager.alert('提示', '导入目录信息为空！', 'info');
						return isValid;
					}
					//判断输入文本框是否有空行
					var text = $("#ajmlList").textbox('getValue');
					var lines = text.split('\n');
				    for(var i= 0; i<lines.length;i++){
				        var line = lines[i].replace(/\s+/g,"");  
				        if(line==""){
				           $.messager.alert('提示', '导入目录信息有空行，不符合要求！！', 'info');
				           return false;
				        }
				    }
					param.pcdm = pcdm;
					return isValid;
				},
				success : function(data) {
					var result = JSON.parse(data);
					if(result.success == 0){
						$.messager.alert("提示", '导入数据过多，请导入符合要求的数据！', 'warning');
					}else{
						$.messager.alert("提示", '导入数据成功', 'info');
						$("#dlgAjmlListInfo").dialog("close");	
					}
				},
			});
		});
		$("#ajmlCancel").click(function() {
			$("#dlgAjmlListInfo").dialog("close");
		});
	</script>
	<div id=ajmlInfoMain>
		<form id="fAjmlInfoList">
			<table align="center" style="margin-top:30px;">
				<tr align="right">
					<td>开始序号 : <input id="ajmlId" name="id" /></td>
				</tr>
				<tr align="right">
					<td>导入目录列表 : <input id="ajmlList" name="mlList" /></td>
				</tr>
				<tr align="right">
					<td height="20px"></td>
				</tr>
				<tr align="center">
					<td><a id="ajmlSubmit" class="easyui-linkbutton"
						data-options="iconCls:'icon-ok'">确认</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
						id="ajmlCancel" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel'">取消</a></td>

				</tr>
			</table>
		</form>
	</div>

</body>
</html>