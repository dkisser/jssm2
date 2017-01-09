<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <title>案卷一级分类</title>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
</head> 
<body>
<script type="text/javascript">
if("${isnew}"=="true"){//isnew 为true表示新增 为false表示更新
	$("#ajlxDm").numberbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
		prompt:"请输入3位有效数字",
		validType:'length[0,3]',
		invalidMessage:"请输入3位有效数字",
		formatter:function(value){
			if(value.length==1){
				return '00'+value;
			}else if(value.length == 2){
				return '0'+value;
			}else{
				return value;
			}
		}
	});
	$("#ajlxMc").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
	});
	$("#ajlxPym").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
	});
	$("#ajlxTybz").combobox({
		width:200,
		valueField: 'value',
		textField: 'label',
		panelHeight:100,
		editable:false,
		required:true,
		value:"启用",
		data: [{
			label: '启用',
			value: '0'
		},{
			label: '停用',
			value: '1'
		}],
	});
	$("#ajlxSubmit").click(function(){
		$("#fajlxInfoList").form('submit',{
			url:getContextPath()+"/catalog/ajlxInfo.do",
			onSubmit:function(param){
				var isValid = $(this).form('validate');
				if(isValid==false){
					$.messager.alert('提示','请按要求填写表单','info');
				}
				$.ajax({
						url:getContextPath() + "/catalog/checkajlxList.do",
						async:false,
						cache:false,
						type:"post",
						data:{"dm":$("#ajlxDm").textbox("getValue"),"mc":$("#ajlxMc").textbox("getValue")},
						dataType:"json",
						success:function(result){
							var refound = result.success;
							var found = 1;
							if(refound==found){
								$.messager.alert('提示','案卷类型代码或名称已存在','info');  
								isValid = 0;
							}
						}
					});
			
				param.action='save';
				param.flag='0';  //传个参数判断是否为新增还是编辑！
				return isValid;
			},
			success:function(data){
				$.messager.alert("提示",'新建成功!','info');
				$("#dlgAjlxListInfo").dialog("close");
			},
		});
	});
	$("#ajlxCancel").click(function(){
		$("#dlgAjlxListInfo").dialog("close");
	});
	//修改代码
}else{
	$("#ajlxDm").textbox({
		width:200,
		readonly:true,
		iconCls:"icon-lock",
		value:'${chuajlx.dm}',
	});
	$("#ajlxMc").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
		value:'${chuajlx.mc}',
	});
	$("#ajlxPym").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
		value:'${chuajlx.pym}',
	});
	$("#ajlxTybz").combobox({
		width:200,
		valueField: 'value',
		textField: 'label',
		panelHeight:100,
		editable:false,
		required:true,
		value:"启用",
		data: [{
			label: '启用',
			value: '0'
		},{
			label: '停用',
			value: '1'
		}],
	});
	
	$("#ajlxCancel").click(function(){
		$("#dlgAjlxListInfo").dialog("close");
	});
	$("#ajlxSubmit").click(function(){
		$("#fajlxInfoList").form('submit',{
			url:getContextPath()+"/catalog/ajlxInfo.do",
			onSubmit:function(param){
				var isValid = $(this).form('validate');
				if(isValid==false){
					$.messager.alert('提示','请按要求填写表单','info');
					
				}
				
				$.ajax({
						url:getContextPath() + "/catalog/checkajlxListUpdate.do",
						async:false,
						cache:false,
						type:"post",
						data:{"newmc":'${chuajlx.mc}',"mc":$("#ajlxMc").textbox("getValue")},
						dataType:"json",
						success:function(result){
							var refound = result.success;
							var found = 1;
							if(refound==found){
								$.messager.alert('提示','案卷类型名称已存在','info');  
								isValid = 0;
							}
						}
					});
			
				param.action='save';
				param.flag='1';  //传个参数判断是否为新增还是编辑！
				return isValid;
			},
			success:function(data){
				$.messager.alert("提示",'编辑成功!','info');
				$("#dlgAjlxListInfo").dialog("close");
			},
		});
	});
	
}
</script>
<div id = ajlxInfoMain>
<form id = "fajlxInfoList" method="post">
<table align="center" style="margin-top:30px;">
	<tr align = "right">
		<td>案卷类型代码 : <input id = "ajlxDm" name = "dm"/></td>
		<td style="color:#FF0000">*</td>
	</tr>
	<tr align = "right">
		<td height="10px"></td>
	</tr>
	<tr align = "right">
		<td>案卷类型名称 : <input id = "ajlxMc" name = "mc"/></td>
		<td style="color:#FF0000">*</td>
	</tr>
	<tr align = "right">
		<td height="10px"></td>
	</tr>
	<tr align = "right">
		<td >拼 音 码 : <input id = "ajlxPym" name = "pym"/></td>
		<td style="color:#FF0000">*</td>
	</tr>
	<tr align = "right">
		<td height="10px"></td>
	</tr>
	<tr align = "right">
		<td >状  态 : <input id = "ajlxTybz" name = "tybz"/></td>
		<td style="color:#FF0000">*</td>
	</tr>
	<tr align = "right">
		<td height="20px"></td>
	</tr>
	<tr align = "center">
		<td ><a href = "#" id = "ajlxSubmit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确认</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href = "#" id = "ajlxCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a></td>
		
	</tr>
</table>
</form>
</div>
</body>
</html>
