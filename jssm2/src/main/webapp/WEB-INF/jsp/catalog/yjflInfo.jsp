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
if("${isnew}"=="true"){
	$("#yjflDm").numberbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
		prompt:"请输入3位有效字符",
		validType:'length[0,3]',
		invalidMessage:"请输入3位有效字符",
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
	$("#yjflMc").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
	});
	$("#yjflPym").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
	});
	$("#yjflTybz").combobox({
		width:200,
		valueField: 'label',
		textField: 'value',
		panelHeight:100,
		editable:false,
		required:true,
		value:"启用",
		data: [{
			label: '启用',
			value: '启用'
		},{
			label: '停用   ',
			value: '停用'
		}],
	});
	$("#yjflSubmit").click(function(){
		$("#fYjflInfoList").form('submit',{
			url:getContextPath()+"/catalog/yjflInfo.do",
			onSubmit:function(param){
				var isValid = $(this).form('validate');
				if(isValid==false){
					$.messager.alert('提示','请按要求填写表单','info');
					
				}
				
				$.ajax({
						url:getContextPath() + "/catalog/checkYjflList.do",
						async:false,
						cache:false,
						type:"post",
						data:{"dm":$("#yjflDm").textbox("getValue"),"mc":$("#yjflMc").textbox("getValue")},
						dataType:"json",
						success:function(result){
							var refound = result.success;
							var found = 1;
							if(refound==found){
								$.messager.alert('提示','案卷一级分类代码或名称已存在','info');  
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
				$("#dlgYjflListInfo").dialog("close");
			},
		});
	});
	$("#yjflCancel").click(function(){
		$("#dlgYjflListInfo").dialog("close");
	});
}else{
		$("#yjflDm").textbox({
		width:200,
		readonly:true,
		iconCls:"icon-lock",
		prompt:"请输入字符数小于3位的字符",
		validType:'length[0,3]',
		invalidMessage:"请输入正确的信息",
		value:'${chuyjfl.dm}',
	});
	$("#yjflMc").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
		value:'${chuyjfl.mc}',
	});
	$("#yjflPym").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
		value:'${chuyjfl.pym}',
	});
	$("#yjflTybz").combobox({
		width:200,
		editable:false,
		valueField: 'label',
		textField: 'value',
		panelHeight:100,
		required:true,
		value:'${chuyjfl.tybz==0?'启用':'停用'}',
		data: [{
			label: '启用',
			value: '启用'
		},{
			label: '停用   ',
			value: '停用'
		}],
	});
	
	$("#yjflCancel").click(function(){
		$("#dlgYjflListInfo").dialog("close");
	});
	$("#yjflSubmit").click(function(){
		$("#fYjflInfoList").form('submit',{
			url:getContextPath()+"/catalog/yjflInfo.do",
			onSubmit:function(param){
				var isValid = $(this).form('validate');
				if(isValid==false){
					$.messager.alert('提示','请按要求填写表单','info');
					
				}
				
				$.ajax({
						url:getContextPath() + "/catalog/checkYjflListUpdate.do",
						async:false,
						cache:false,
						type:"post",
						data:{"newmc":'${chuyjfl.mc}',"mc":$("#yjflMc").textbox("getValue")},
						dataType:"json",
						success:function(result){
							var refound = result.success;
							var found = 1;
							if(refound==found){
								$.messager.alert('提示','案卷一级分类名称已存在','info');  
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
				$("#dlgYjflListInfo").dialog("close");
			},
		});
	});
	
}
</script>
<div id = yjflInfoMain>
<form id = "fYjflInfoList" method="POST">
<table align="center" style="margin-top:30px;">
	<tr align = "right">
		<td>一级分类代码 : <input id = "yjflDm" name = "dm"/></td>
		<td style="color:#FF0000">*</td>
	</tr>
	<tr align = "right">
		<td height="10px"></td>
	</tr>
	<tr align = "right">
		<td>一级分类名称 : <input id = "yjflMc" name = "mc"/></td>
		<td style="color:#FF0000">*</td>
	</tr>
	<tr align = "right">
		<td height="10px"></td>
	</tr>
	<tr align = "right">
		<td >拼 音 码 : <input id = "yjflPym" name = "pym"/></td>
		<td style="color:#FF0000">*</td>
	</tr>
	<tr align = "right">
		<td height="10px"></td>
	</tr>
	<tr align = "right">
		<td >状  态 : <input id = "yjflTybz" name = "tybz"/></td>
		<td style="color:#FF0000">*</td>
	</tr>
	<tr align = "right">
		<td height="20px"></td>
	</tr>
	<tr align = "center">
		<td ><a href = "#" id = "yjflSubmit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确认</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href = "#" id = "yjflCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a></td>
		
	</tr>
</table>
</form>
</div>
</body>
</html>

