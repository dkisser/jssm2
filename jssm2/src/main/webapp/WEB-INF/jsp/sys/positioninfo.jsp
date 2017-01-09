<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>职位信息</title>
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
#winPosinfo {
	margin-top: 50px;
}
</style>

</head>

<body>


<div id="winPosinfo">
	<div id="dlgPosInfo">
	<br>
	<form id="fPosInfomain" method="post">
	<table border="0" style="border-spacing:5px;">
		<tr height="20"></tr>
		<tr><td width='90px'></td ><td align='right'>职位编号：</td><td><input id="tbPosinfopid"  name="id"  type="text" style="width:200px"></td><td id="tbpidAlert" style="color:#FF0000">*</td></tr>
		<tr><td></td><td align='right'>职位名称：</td><td><input id="tbPosinfopname" type="text" name="name"  style="width:200px"></td><td id="tbpnameAlert" style="color:#FF0000">*</td></tr>
	</table>
	<br><br>
	<table border="0">
			<tr>
			<td width='140px'></td>
			<td width='100px' align="center"><a id="lbPosinfoSave" href="#" >保存</a>  </td>
			<td width='100px' align="center"><a id="lbPosinfoCancel" href="#" >取消</a>  </td>
			<td></td>
		</tr>
	</table>
	</form>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		if("${isnew}"=="true" ){			
			//下面是新增页面的javascript代码
			var isright=0;
			
			$("#tbPosinfopid").textbox({      
				iconCls:'icon-more',  
			    iconAlign:'right',   
			    required: true,      
			});
			
			//为职位编号文本框添加失去焦点事件
			$("#tbPosinfopid").textbox("textbox").bind("blur",function(){	//当文本框失去焦点时触发
	
				var pid=$("#tbPosinfopid").val();
				if(pid ==''){
					return false;
				}
				$.ajax({
					url:getContextPath() + "/position/checkpid.do?pid="+pid,
					async:true,		//是否异步请求
					cache:false,	//是否缓存
					type:"post",	//请求方式
					dataType:"json",	//返回数据类型
					success:function(result){
						if(result.success==0){
							$("#tbpidAlert").text("职位编号已存在！");
							isright++;
						}
					}
				});
			});
			
			$("#tbPosinfopid").textbox("textbox").bind("focus",function(){
				$("#tbpidAlert").text("*");
				if(isright>0){
					isright--;
				}
			});
			
			
			$("#tbPosinfopname").textbox({    
				  iconCls:'icon-man',
			      iconAlign:'right',       
			      required: true, 
			});
			
			//为职位名称文本框添加失去焦点事件
			$("#tbPosinfopname").textbox("textbox").bind("blur",function(){	//当文本框失去焦点时触发
	
				var pname=$("#tbPosinfopname").val();
				if(pname ==''){
					return false;
				}
				$.ajax({
					url:getContextPath() + "/position/checkpname.do",
					async:true,		//是否异步请求
					cache:false,	//是否缓存
					type:"post",	//请求方式
					data:{isnew:"${isnew}",pname:pname},	//向服务器传递参数
					dataType:"json",	//返回数据类型
					success:function(result){
						if(result.success==0){
							$("#tbpnameAlert").text("职位名称已存在！");
							isright++;
						}
					}
				});
			});
			
			$("#tbPosinfopname").textbox("textbox").bind("focus",function(){
				$("#tbpnameAlert").text("*");
				if(isright>0){
					isright--;
				}
			});
			
			
	
			$("#lbPosinfoSave").linkbutton({    
			    iconCls: 'icon-save'   
			});  
			
			
			//以上都属于样式设计
			
			//以下是按钮触发事件设计
			$("#lbPosinfoSave").click(function(){ 
				$.messager.progress();
				$("#fPosInfomain").form('submit',{
					url:getContextPath() + "/position/savepositionInfo.do",
					onSubmit: function(param){
						if(isright>0){
							$.messager.progress('close');
							$.messager.alert('提示','请按照要求填写表单','info');  
							isright=0;
							return false;
						}				
						param.action='save';
						param.savenew='true';
						var isValid = $(this).form('validate');
						if (!isValid){
							$.messager.progress('close');
							$.messager.alert('提示','请按照要求填写表单','info');  
						}
						return isValid;	// 返回false终止表单提交
					},
					success: function(){
					 	$.messager.progress('close');
					 	$("#dlgPositionDiv").dialog('close');  
						$.messager.alert('提示','新增成功','info');  
						//$('#center').panel('refresh',getContextPath() + "/user/userListUI.do");     突然发现这个刷新没有用，而且刷新了就不能跳转了
						
						//重新加载数据表格
						$("#tbPositionList").datagrid('reload');
					}
				});
			});
			 
	 		$("#lbPosinfoCancel").linkbutton({    
			    iconCls: 'icon-cancel'   
			});  
			
			$("#lbPosinfoCancel").click(function(){    
			  $("#dlgPositionDiv").dialog('close');
			}); 
			
			
	}else{
			//下面是编辑页面的javascript代码
			var isright=0;		
			
			$("#tbPosinfopid").textbox({      
				iconCls:'icon-lock',  
			    iconAlign:'right',   
			    required: true,   
			    value: '${editpos.id}' ,
			    readonly: true  
			});
			
			
			$("#tbPosinfopname").textbox({    
				  iconCls:'icon-man',
			      iconAlign:'right',       
			      required: true, 
			      value: '${editpos.name}'
			});
			
			//为职位名称文本框添加失去焦点事件
			$("#tbPosinfopname").textbox("textbox").bind("blur",function(){	//当文本框失去焦点时触发
	
				var pname=$("#tbPosinfopname").val();
				if(pname==''){
					return false;
				}
				$.ajax({
					url:getContextPath() + "/position/checkpname.do",
					async:true,		//是否异步请求
					cache:false,	//是否缓存
					type:"post",	//请求方式
					data:{isnew:"${isnew}",oldpname:'${editpos.name}',pname:pname},	//向服务器传递参数
					dataType:"json",	//返回数据类型
					success:function(result){
						if(result.success==0){
							$("#tbpnameAlert").text("职位名称已存在！");
							isright++;
						}
					}
				});
			});
			
			$("#tbPosinfopname").textbox("textbox").bind("focus",function(){
				$("#tbpnameAlert").text("*");
				if(isright>0){
					isright--;
				}
			});
			
			
	
			$("#lbPosinfoSave").linkbutton({    
			    iconCls: 'icon-save'   
			});  
			
			
			//以上都属于样式设计
			
			//以下是按钮触发事件设计
			$("#lbPosinfoSave").click(function(){ 
				$.messager.progress();
				$("#fPosInfomain").form('submit',{
					url:getContextPath() + "/position/savepositionInfo.do",
					onSubmit: function(param){
						if(isright>0){
							$.messager.progress('close');
							$.messager.alert('提示','请按照要求填写表单','info');  
							isright=0;
							return false;
						}				
						param.action='save';
						param.savenew='false';
						var isValid = $(this).form('validate');
						if (!isValid){
							$.messager.progress('close');
							$.messager.alert('提示','请按照要求填写表单','info');  
						}
						return isValid;	// 返回false终止表单提交
					},
					success: function(){
					 	$.messager.progress('close');
					 	$("#dlgPositionDiv").dialog('close');  
						$.messager.alert('提示','修改成功','info');  
						//$('#center').panel('refresh',getContextPath() + "/user/userListUI.do");     突然发现这个刷新没有用，而且刷新了就不能跳转了
						
					}
				});
			});
			 
	 		$("#lbPosinfoCancel").linkbutton({    
			    iconCls: 'icon-cancel'   
			});  
			
			$("#lbPosinfoCancel").click(function(){    
			  $("#dlgPositionDiv").dialog('close');
			}); 

	}	
	
	});
	
			
			
	

</script>
</body>
</html>
