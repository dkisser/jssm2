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
<title>卷宗归档</title>


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
</style>
</head>
<body>
<div id="dlgGzgdInfo">
    <form id = "fromDlgGzgdInfo">
	<table style="padding-left:20px;margin-top:5px;">
		<tr>
			<td align="right">卷宗分类：</td>
			<td><input id="gdJzfl" name="yjfl" /></td>
		</tr>
		<tr>
			<td align="right">归档日期：</td>
			<td><input id="gdGdrq" name="gdrq" /></td>
		</tr>
		<tr>
			<td align="right">归档号数：</td>
			<td><input id="gdGdhs" name="gdhs" /><span id = "gdGdhsWarning" style="fontSize:12px;color:red;padding-left:10px;"></span></td>
		</tr>
		<tr>
			<td align="right">安全级别：</td>
			<td><input id="gdAqjb" name="jbdm" /></td>
		</tr>
		<tr>
			<td align="right">归档期限：</td>
			<td><input id="gdGdqx" name="qxdm" /></td>
		</tr>
		<tr>
			<td align="right">卷号：</td>
			<td><input id="gdJh" name="jh" /><span id = "gdJhWarning" style="fontSize:12px;color:red;align:right;padding-left:10px;"></td>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<td><input id="gdRemark" name="remrak" /></td>
		</tr>
		<tr height=5></tr>
		<tr>
			<td align="center" colspan="2"><a href="javascript: void(0);" id = "gdFileBtn">归档</a></td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">
	$("#dlgGzgdInfo").dialog({
		title : '${jjzId}' + '卷宗归档',
		width : 512,
		height : 300,
		inline : true,
	});
	$('#gdJzfl').combobox({
		width : 200,
		url : getContextPath() + '/ajuan/getGdJzfl.do',
		valueField : 'id',
		textField : 'text',
		value:'民事',
		editable:false
	});
	var currdate = new Date();
	$('#gdGdrq').datebox({
		width : 200,
		value : 'currdate.toLocaleDateString()',
		editable:false,
		onChange:function(){
// 			$.ajax({
// 				url: getContextPath()+"/ajuan/getMaxGdhsByGdrq.do",
// 				type: "POST",
// 				dataType: "JSON",
// 				data: {'gdrq':$('#gdGdrq').textbox("getValue")},
// 				success:function(MaxGdhs){
// 					if(MaxGdhs==null){
// 						$('#gdGdhs').textbox('setValue',1);
// 					}else{
// 						$('#gdGdhs').textbox('setValue',MaxGdhs);
// 					}
// 				}
				
// 			});
			
		}
	});
	
	$('#gdGdhs').numberbox({
		width : 200,
		value:'${gdhs}',
		required:true,
		validType:'length[1,5]',
		prompt:'请输入5位以内的数字',
		invalidMessage:'请输入5位以内的数字',
		onChange:function(){
// 			$.ajax({
// 				url: getContextPath()+"/ajuan/checkGdhsByGdrq.do",
// 				type: "POST",
// 				dataType: "JSON",
// 				data: {'gdrq':$('#gdGdrq').textbox("getValue"),'gdhs':$('#gdGdhs').textbox("getText")},
// 				success:function(checkGdhs){
// 					if(checkGdhs=="false"){
// 						$("#gdGdhsWarning").text("归档号数已存在于其他卷宗！");  
// 					}
// 					if(checkGdhs=="success"){
// 						$("#gdGdhsWarning").text("");
// 					}
// 				}
// 			});
		}
	});

	$('#gdAqjb').combobox({
		width : 200,
		url : getContextPath() + '/query/query_aqjbInfo.do',
		valueField : 'id',
		textField : 'text',
		panelHeight:100,
		value:'普通',
		editable:false
	});
	$('#gdGdqx').combobox({
		width : 200,
		url : getContextPath() + '/query/query_bgqxInfo.do',
		valueField : 'id',
		textField : 'text',
		panelHeight:100,
		value:'20年',
		editable:false
	});
	$('#gdJh').numberbox({
		width : 200,
		value:'${gdjh}',
		required:true,
		validType:'length[1,3]',
		prompt:'请输入3位以内的数字',
		onChange:function(){
// 			$.ajax({
// 				url: getContextPath()+"/ajuan/checkJhbyId.do",
// 				type: "POST",
// 				dataType: "JSON",
// 				data: {'jh':$('#gdJh').textbox("getValue")},
// 				success:function(checkGdJh){
// 					if(checkGdJh=="false"){
// 						$("#gdJhWarning").text("当前卷宗下已存在输入的卷号！");
// 					}
// 					if(checkGdJh=="success"){
// 						$("#gdJhWarning").text("");
// 					}
// 				}
// 			});		
		}
	});
	
	$('#gdRemark').textbox({
		width : 380,
		height : 55,
		multiline : true,
	});

	$("#gdFileBtn").linkbutton({
		iconCls:'icon-edit',
	});
	$("#gdFileBtn").click(function(){
		$.messager.confirm('确认','您确认想要将该卷宗归档吗？',function(r){ 
		    if (r){
		    	$.messager.progress(); 
		    	$("#fromDlgGzgdInfo").form('submit', {    
				    url:getContextPath()+"/ajuan/gdInfoFile.do", 
				    onSubmit: function(param){    
				    	var validate = $(this).form("validate");
				    	if(validate==false){
				    		$.messager.progress('close');
				    		$.messager.alert('警告','归档号数和卷号不能为空！','info');
				    		return false;
				    	}
						param.ewm = '${jzewm}';
						return validate;	
				    },    
				    success:function(saveInfo){
				    	$.messager.progress('close');
				        if(saveInfo==1){
							var jzBrowseDia =  $("<div id=\"gdzlBrowseDia\"></div>");
							jzBrowseDia.appendTo("body");
							$("#gdzlBrowseDia").dialog({
								width:1024,
								title:'卷宗浏览',
								href:getContextPath() + "/ajuan/jzBrowse.do?hasdlg=no&jzEwm="+'${jzewm}', 
								height:600,
								closed:false,
								cache:false,
								shadow:true,	
								resizable:false,	
								modal:true,
								inline:true,	
								onClose: function(){
									jzBrowseDia.remove();
									$("#query_Datagrid").datagrid('reload');
								},
							});
				        	$('#dlgGzgdInfo').dialog('close');
				        }
				        if(saveInfo!=1){
				        	$.messager.alert("提示","数据库更新错误",'info');
				        }  
				    },    
				});
			}    
		}); 
		
	});
</script>

</body>
</html>