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
    <title>案卷分类</title>
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
	$("#ajflDm").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
		prompt:"请输入字符数小于10位的字符",
		validType:'length[0,10]',
		invalidMessage:"请输入正确的信息",
	});
	$("#ajflMc").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
		
	});
	$("#ajflPym").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
	});
	$("#ajflYjfl").combobox({
		editable:false,
		required:true,
		width:200,
		panelHeight:120,
		valueField: 'id',
		textField: 'text',
		url:getContextPath()+"/catalog/findAjflCombox.do",
	});
	$("#ajflTybz").combobox({
		editable:false,
		required:true,
		width:200,
		valueField: 'label',
		textField: 'value',
		panelHeight:70,
		value:'启用',
		data: [{
			label: '启用',
			value: '启用'
		},{
			label: '停用',
			value: '停用'
		}]
	});
	$("#ajflSubmit").click(function(){
		$("#fAjflInfoList").form('submit',{
			url:getContextPath()+"/catalog/ajflInfo.do",
			onSubmit:function(param){
				var isValid = $(this).form('validate');
				if(isValid==false){
					$.messager.alert('提示','请按要求填写表单','info');
					
				}
				
				$.ajax({
						url:getContextPath() + "/catalog/checkAjflList.do",
						async:false,
						cache:false,
						type:"post",
						data:{"dm":$("#ajflDm").textbox("getValue"),"mc":$("#ajflMc").textbox("getValue")},
						dataType:"json",
						success:function(result){
							var refound = result.success;
							var found = 1;
							if(refound==found){
								$.messager.alert('提示','案卷分类代码或名称已存在','info');  
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
				$("#dlgAjflListInfo").dialog("close");
			},
		});
	});
	$("#ajflCancel").click(function(){
		$("#dlgAjflListInfo").dialog("close");
	});
}else{
	$("#ajflDm").textbox({
		width:200,
		readonly:true,
		iconCls:"icon-lock",
		value:'${chuajfl.dm}',
	});
	$("#ajflMc").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
		value:'${chuajfl.mc}',
		
	});
	$("#ajflPym").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
		value:'${chuajfl.pym}',
	});
	$("#ajflYjfl").combobox({
		editable:false,
		required:true,
		width:200,
		panelHeight:120,
		valueField: 'id',
		textField: 'text',
		url:getContextPath()+"/catalog/findAjflCombox.do",
		value:'${chuajfl.yjfl}',
	});
	$("#ajflTybz").combobox({
		editable:false,
		required:true,
		width:200,
		valueField: 'label',
		textField: 'value',
		panelHeight:70,
		value:'${chuajfl.tybz==0?'启用':'停用'}',
		data: [{
			label: '启用',
			value: '启用'
		},{
			label: '停用',
			value: '停用'
		}]
	});
	$("#ajflSubmit").click(function(){
		$("#fAjflInfoList").form('submit',{
			url:getContextPath()+"/catalog/ajflInfo.do",
			onSubmit:function(param){
				var isValid = $(this).form('validate');
				if(isValid==false){
					$.messager.alert('提示','请按要求填写表单','info');
					
				}
				
// 				$.ajax({
// 						url:getContextPath() + "/catalog/checkAjflListUpdate.do",
// 						async:false,
// 						cache:false,
// 						type:"post",
// 						data:{"newmc":'${chuajfl.mc}',"mc":$("#ajflMc").textbox("getValue")},
// 						dataType:"json",
// 						success:function(result){
// 							var refound = result.success;
// 							var found = 1;
// 							if(refound==found){
// 								$.messager.alert('提示','案卷分类名称已存在','info');  
// 								isValid = 0;
// 							}
// 						}
// 					});
			
				param.action='save';
 				param.flag='1';  //传个参数判断是否为新增还是编辑！
				return isValid;
			},
			success:function(data){
				$.messager.alert("提示",'编辑成功!','info');
				$("#dlgAjflListInfo").dialog("close");
			},
		});
	});
	$("#ajflCancel").click(function(){
		$("#dlgAjflListInfo").dialog("close");
	});

}
</script>
<div id = ajflInfoMain>
<form id = "fAjflInfoList">
<table align="center" style="margin-top:40px;">
	<tr align = "right">
		<td>分类代码 : <input id = "ajflDm" name = "dm"/></td>
	</tr>
	<tr align = "right">
		<td>分类名称 : <input id = "ajflMc" name = "mc"/></td>
	</tr>
	<tr align = "right">
		<td >拼 音 码 : <input id = "ajflPym" name = "pym"/></td>
	</tr>
	<tr align = "right">
		<td >一级分类 : <input id = "ajflYjfl" name = "yjfl"/></td>
	</tr>
	<tr align = "right">
		<td >状  态 : <input id = "ajflTybz" name = "tybz"/></td>
	</tr>
	<tr align = "right">
		<td height="20px"></td>
	</tr>
	<tr align = "center">
		<td ><a href = "#" id = "ajflSubmit" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确认</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href = "#" id = "ajflCancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a></td>
		
	</tr>
</table>
</form>
</div>

</body>
</html>

