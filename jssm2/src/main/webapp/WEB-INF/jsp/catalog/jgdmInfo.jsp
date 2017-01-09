<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
</head>
<body>
<script type="text/javascript">
	$(function(){
		//alert(${isNew});
	if(${isNew}){
		$("#mc").textbox({
			width:200,
			required:true,
			missingMessage:'该输入项为必输项!',
			iconCls:"icon-mini-edit",
		});
		$("#sjdm").combobox({ 
			width:200,
			required:true,
			editable:false,
			missingMessage:'该输入项为必输项!',
		    url:getContextPath()+'/catalog/jgdmSjmc.do',    
		    valueField:'id',    
		    textField:'text'   
		}); 
		$("#tybz").combobox({
			width:200,
			value:'0',
			editable:false,
			valueField: 'id',
			textField: 'value',
			data: [{
				id: '0',
				value: '启用'
			},{
				id: '1',
				value: '停用'
			}]
		});
		$("#remark").textbox({
			multiline:true,
				width:200,
				height:50,
		});
		$("#cancel").bind('click', function(){
			 $("#dgAdd").dialog('close');
		});
		$("#ok").bind('click', function(){    
			$("#form").form('submit', {    
			    url:getContextPath()+"/catalog/addjgdm.do", 
			    onSubmit: function(){ 
			    	var msg='';
			        if($(this).form('validate')==false){
			        	var mc=$("#mc").textbox('getText');
			        	var sjdm=$("#sjdm").combobox('getText');
			        	if(mc==''||mc==null){
			        		msg='机构名称不能为空!';
			        	}
			        	if(sjdm==''||sjdm==null){
			        		msg+='上级机构不能为空！';
			        	}
			        	$.messager.alert('提示',msg);  
			        	return false;
			        }
			        $.messager.progress(); 
			        var isValid=1;
			        $.ajax({
						url:getContextPath() + "/catalog/checkMc.do",
						async:false,
						cache:false,
						type:"post",
						data:{"mc":$("#mc").textbox('getText')},
						dataType:"json",
						success:function(result){
							if(result==1){
								$.messager.progress('close'); 
								$.messager.alert('提示','机构名称已存在！');  
								isValid = 0;
							}
						}
					});
			        
			        if(isValid==0){
			        	return false;
			        }
			        return true;
			    },    
			    success:function(data){
			    	$.messager.progress('close'); 
			        if(data>0){
			        	$.messager.alert('提示','添加成功！'); 
			        	$("#jgdmDg").datagrid('reload');
			        }else{
			        	$.messager.alert('提示','添加失败！');  
			        }
			        $("#dgAdd").dialog('close');
			    }    
			});   
	    }); 
		//编辑表格
	   }else{
		   $("#mc").textbox({
				width:200,
				required:true,
				missingMessage:'该输入项为必输项!',
				iconCls:"icon-mini-edit",
			});
			$("#sjdm").combobox({ 
				width:200,
				required:true,
				editable:false,
				missingMessage:'该输入项为必输项!',
			    url:getContextPath()+'/catalog/jgdmSjmc.do',    
			    valueField:'id',    
			    textField:'text'   
			}); 
			$("#tybz").combobox({
				width:200,
				value:'0',
				editable:false,
				valueField: 'id',
				textField: 'value',
				data: [{
					id: '0',
					value: '启用'
				},{
					id: '1',
					value: '停用'
				}]
			});
			$("#remark").textbox({
				multiline:true,
					width:200,
					height:50,
			});
			
			var row=$("#jgdmDg").datagrid('getSelected');
			$("#form").form('load',{
				mc:row.mc,
				sjdm:row.sjdm,
				tybz:row.tybz,
				remark:row.remark
			});

			$("#cancel").bind('click', function(){
				 $("#dgEdit").dialog('close');
			});
			
			$("#ok").bind('click', function(){    
				$("#form").form('submit', {    
				    url:getContextPath()+"/catalog/updagejgdm.do",
				    queryParams:{"dm":row.dm},
				    onSubmit: function(){ 
				    	var msg='';
				        var mc=$("#mc").textbox('getText');
				        var sjdm=$("#sjdm").combobox('getText');
				        if($(this).form('validate')==false){
				        	if(mc==''||mc==null){
				        		msg='机构名称不能为空!';
				        	}
				        	if(sjdm==''||sjdm==null){
				        		msg+='上级机构不能为空！';
				        	}
				        	$.messager.alert('提示',msg);  
				        	return false;
				        }
				        $.messager.progress(); 
				        var isValid=1;
			if(mc!=row.mc){//机构名称没改变不做检查
				        $.ajax({
							url:getContextPath() + "/catalog/checkMc.do",
							async:false,
							cache:false,
							type:"post",
							data:{"mc":mc},
							dataType:"json",
							success:function(result){
								if(result==1){
									$.messager.progress('close'); 
									$.messager.alert('提示','机构名称已存在！');  
									isValid = 0;
								}
							}
						});
				     }  
				        if(isValid==0){
				        	return false;
				        }
				        return true;
				    },    
				    success:function(data){
				    	$.messager.progress('close'); 
				        if(data>0){
				        	$.messager.alert('提示','修改成功！');  
				        	$("#jgdmDg").datagrid('reload');
				        }else{
				        	$.messager.alert('提示','修改失败！');  
				        }
				        $("#dgEdit").dialog('close');
				    }    
				});   
		    }); 
	   }	
	});
</script>
<form id="form"  method='POST'>
	<table align="center" style="margin-top:20px;">
	<tr align = "right">
		<td>机构名称 : <input id = "mc" name = "mc"/></td>
		<td style="color:#FF0000">*</td>
	</tr>
	<tr align = "right">
		<td height="10px"></td>
	</tr>
	<tr align = "right">
		<td>上级机构 : <input id = "sjdm" name = "sjdm"/></td>
		<td style="color:#FF0000">*</td>
	</tr>
	<tr align = "right">
		<td height="10px"></td>
	</tr>
	<tr align = "right">
		<td >停用标志 : <input id = "tybz" name = "tybz"/></td>
		<td style="color:#FF0000">*</td>
	</tr>
	<tr align = "right">
		<td height="10px"></td>
	</tr>
	<tr align = "right">
		<td >备&nbsp;&nbsp;&nbsp;&nbsp;注 : <input id = "remark" name = "remark"/></td>
	</tr>
	<tr align = "right">
		<td height="20px"></td>
	</tr>
	<tr align = "center">
		<td ><a href = "#" id = "ok" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确认</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href = "#" id = "cancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a></td>
		
	</tr>
</table>
</form>	
</body>
</html>