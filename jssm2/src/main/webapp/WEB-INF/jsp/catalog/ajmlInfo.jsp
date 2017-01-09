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
<style type="text/css">
	#ajmlRemark {
  font-size: 12px;
  width:200px;
    position: relative;
  border: 1px solid #95B8E7;
  background-color: #fff;
  vertical-align: middle;
  display: inline-block;
/*   overflow: hidden; */
/*   white-space: nowrap; */
  margin: 0;
  padding: 0;
  -moz-border-radius: 5px 5px 5px 5px;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
}

</style>
	<script type="text/javascript">
var pcdm ='${pcdm}';
if("${action}"=="add"){
	$.extend($.fn.validatebox.defaults.rules, {    
	    MaxLength: {    
	        validator: function(value,param){    
	            if(value.length>2){
	            	return false;
	            }    
	           return true;
	        },    
	        message: '请输1~2位有效数字'   
	    }    
	});
	$("#ajmlId").numberbox({
		required:true,
		width:200,
	 	validType:"MaxLength",
		iconCls:"icon-mini-edit",
		prompt:"请输1~2位有效数字",
	});
	$("#ajmlMc").textbox({
		width:200,
		required:true,
		iconCls:"icon-mini-edit",
		
	});
	$("#ajmlTybz").combobox({
		editable:false,
		required:true,
		width:200,
		panelHeight:120,
		valueField: 'label',
		textField: 'value',
		value:'启用',
		data: [{
			label: '启用',
			value: '启用'
		},{
			label: '停用',
			value: '停用'
		}],
	});

	$("#ajmlRemark").textbox({
		editable:true,
		width:200,
		height:40,
		multiline:true,
	});
	
	$("#ajmlSubmit").click(function(){
		$("#fAjmlInfoList").form('submit',{
			url:getContextPath()+"/catalog/addAjml.do",
			onSubmit:function(param){
				var isValid = $(this).form('validate');
				if(isValid==false){
					$.messager.alert('提示','请按要求填写表单','info');
					return isValid;
					
				}
				$.ajax({
						url:getContextPath() + "/catalog/checkAjmlList.do",
						async:false,
						cache:false,
						type:"post",
						data:{"id":$("#ajmlId").textbox("getValue"),"pcdm":pcdm},
						dataType:"json",
						success:function(data){
							var result = data.success;
							if(result!=1){
								$.messager.alert('提示','案卷目录序号已存在','info');  
								isValid = 0;
							}
						}
					});
				param.pcdm=pcdm;
				return isValid;
			},
			success:function(data){
				if(data==''||data==null){
					$.messager.alert("提示",'新增目录成功!','info');
					$("#dlgAjmlListInfo").dialog("close");
				}else{
					$.messager.alert("提示",'新增目录失败!','info');
					$("#dlgAjmlListInfo").dialog("close");
				}

			},
		});
	});
	$("#ajmlCancel").click(function(){
		$("#dlgAjmlListInfo").dialog("close");
	});
}else if("${action}"=="update"){
	$("#ajmlId").numberbox({
		readonly:true,
		iconCls: "icon-lock",
		iconAlign: "right",       
		width:200,
		value:'${ajml.id}',
	});
	$("#ajmlMc").textbox({
		width:200,
		required:true,
		value:'${ajml.mc}',
		iconCls:"icon-mini-edit",
		
	});
	$("#ajmlTybz").combobox({
		editable:false,
		required:true,
		width:200,
		panelHeight:120,
		valueField: 'label',
		textField: 'value',
		value:'${ajml.tybz}'==0?'启用':'停用',
		data: [{
			label: '启用',
			value: '启用'
		},{
			label: '停用',
			value: '停用'
		}],
		
	});

	$("#ajmlRemark").textbox({
		editable:true,
		width:200,
		height:40,
		value:'${ajml.remark}',
		multiline:true,
	});
	
	$("#ajmlSubmit").click(function(){
		$("#fAjmlInfoList").form('submit',{
			url:getContextPath()+"/catalog/updateAjml.do",
			onSubmit:function(param){
				var isValid = $(this).form('validate');
				if(isValid==false){
					$.messager.alert('提示','请按要求填写表单','info');
					return isValid;
					
				}
				param.pcdm=pcdm;
				return isValid;
			},
			success:function(data){
				if(data==''||data==null){
					$.messager.alert("提示",'编辑成功!','info');
					$("#dlgAjmlListInfo").dialog("close");
				}else{
					$.messager.alert("提示",'编辑失败!','info');
					$("#dlgAjmlListInfo").dialog("close");
				}
			},
		});
	});
	$("#ajmlCancel").click(function(){
		$("#dlgAjmlListInfo").dialog("close");
	});
	
	
}

</script>
	<div id=ajmlInfoMain>
		<form id="fAjmlInfoList" method="POST">
			<table align="center" style="margin-top:40px;">
				<tr align="right">
					<td>序号 : <input id="ajmlId" name="id" /><td style="color:#FF0000">*</td></td>
				</tr>
				<tr align="right">
					<td>文书名称 : <input id="ajmlMc" name="mc" /><td style="color:#FF0000">*</td></td>
				</tr>
				<tr align="right">
					<td>停用标志 : <input id="ajmlTybz" name="tybz" /><td style="color:#FF0000">*</td></td>
				</tr>
				<tr align="right">
					<td>备注 : <input id="ajmlRemark"  name="remark"/></td>
				</tr>
				<tr align="right">
					<td height="20px"></td>
				</tr>
				<tr align="center">
					<td><a href="#" id="ajmlSubmit" class="easyui-linkbutton"
						data-options="iconCls:'icon-ok'">确认</a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="#"
						id="ajmlCancel" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel'">取消</a></td>

				</tr>
			</table>
		</form>
	</div>

</body>
</html>

