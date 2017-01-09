<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>案号字编辑页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  	<style type="text/css">
	<!--
	body {
		margin-left: 3px;
		margin-top: 0px;
		margin-right: 3px;
		margin-bottom: 0px;
	}
	</style>
  </head>
  
  <body>
		<form  id="AhzInfoForm" action="" method="post">
  		<br>
  		<table align="center">
  			<tr><td align="right">案号字：</td><td width="20"></td><td><input id="ahz" name="ahz" type="text"/></td><td style="color:#FF0000">*</td></tr>
  			<tr><td align="right">一级分类：</td><td width="20"></td><td><input id="yjfl" name="yjfl" type="text"/><td style="color:#FF0000">*</td></tr>
  			<tr><td align="right">案件承办庭：</td><td width="20"></td><td><input id="cbts" name="cbts" type="text"/></td><td style="color:#FF0000">*</td></tr>
  			<tr><td align="right">停用标志：</td><td width="20"></td><td><input id = "tybz" name = "tybz"/></td><td style="color:#FF0000">*</td></tr>
  			<tr><td align="right">备注：</td><td width="20"></td><td><input id="remark" name="remark" type="text"/></td></tr>
  		</table>
  		<br>
  		<table align="center">
  			<tr><td width="20%"></td><td><a href="javascript:void(0);" id="okBtn_ahzInfo">确认</a></td><td width="20%"></td>
  			<td><a id="cancel_Btn_ahzInfo" href="javascript:void(0);">取消</a></td><td width="20%"></td>
  			</tr>
  		</table>
  	</form>
  	<script type="text/javascript">
  		$(function(){
				$('#yjfl').combobox({    
				    url: getContextPath() +"/catalog/findYJFLList.do",    
				    valueField:'id',    
				    textField:'text',
				    editable:false,
					required:true,
					width:200,
					onLoadSuccess:function(){
						var data = $('#yjfl').combobox('getData');
						 $("#yjfl ").combobox('select',data[0].id);
					}
				});  
				
				$('#cbts').combobox({    
				    url: getContextPath() +"/catalog/findCBTSList.do",    
				    valueField:'id',    
				    textField:'text',
				    width:200,
					editable:false,
					required:true,   
				}); 
			
				$("#tybz").combobox({
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
						label: '停用   ',
						value: '1'
					}],
				});
				
				$('#remark').textbox({
					  multiline: true,
					  width:200,
					  height: 60,
				});
				
				$('#okBtn_ahzInfo').linkbutton({
					iconCls: 'icon-ok',
				});
				
				$('#cancel_Btn_ahzInfo').linkbutton({
					iconCls: 'icon-cancel',
				});
				
				$('#cancel_Btn_ahzInfo').click(function(){
					$('#divAhzInfo').dialog("close");
				});
  			
  			if("${isnew}"=="true"){
  				$('#ahz').combobox({    
				    url: getContextPath() +"/catalog/findAhzList.do",    
				    valueField:'id',    
				    textField:'text',
				    panelHeight:'auto',
				    editable:false,
					required:true,  
					width:200,
				}); 
				
  				
  				$("#okBtn_ahzInfo").click(function(){
  					$.messager.progress();
					$("#AhzInfoForm").form('submit',{
						url:getContextPath()+"/catalog/saveAhzInfo.do",
						onSubmit:function(param){
							var isValid = $(this).form('validate');
							if(isValid==false){
								$.messager.alert('提示','请按要求填写表单','info');
								$.messager.progress('close');
							}
							
							var ahz=$('#ahz').combobox('getText');
							if(ahz=="暂无新案号字"){
								$.messager.alert('提示','暂无可新建案号字','info');
								$.messager.progress('close');
								return false;
							}
						
							param.action='save';
							param.isnew='${isnew}';  //传个参数判断是否为新增还是编辑！
							param.cbt=$('#cbts').combobox('getText');
							return isValid;
						},
						success:function(data){
							$.messager.progress('close');
							if(JSON.parse(data)=="success"){
								$.messager.alert("提示",'新建成功!','info');
							}
							else{
								$.messager.alert("提示",'新建失败!','info');
							}
							$("#divAhzInfo").dialog("close");
						},
					});
				});
	
  				
  			}
  			else{
  					$('#ahz').textbox({  
  						iconCls:'icon-lock', 
  					    iconAlign:'right' ,      
					    readonly: true,
					    width:200,
					}); 
  					

				var row=$("#ahzListTab").datagrid('getSelected');

					    $('#ahz').textbox('setValue',row.ahz);//设值
	  					$('#yjfl').combobox('setValue',row.yjfl);//设值
	  					$('#cbts').combobox('setValue',row.cbts);//设值
	  					$('#remark').text(row.remark);
	  					$("#tybz").combobox('setValue',row.tybz);
	  					
  					
  					$("#okBtn_ahzInfo").click(function(){
						$.messager.progress();
						$("#AhzInfoForm").form('submit',{
							url:getContextPath()+"/catalog/saveAhzInfo.do",
							onSubmit:function(param){
								var isValid = $(this).form('validate');
								if(isValid==false){
									$.messager.alert('提示','请按要求填写表单','info');
									$.messager.progress('close');
								}
							
								param.action='save';
								param.isnew='${isnew}';  //传个参数判断是否为新增还是编辑！
								param.cbt=$('#cbts').combobox('getText');
								param.dm = row.dm;
								return isValid;
							},
							success:function(data){
								$.messager.progress('close');
								if(JSON.parse(data)=="success"){
									$.messager.alert("提示",'修改成功!','info');
								}
								else{
									$.messager.alert("提示",'修改失败!','info');
								}
								$("#divAhzInfo").dialog("close");
							},
						});
					});	
  			}
  		});
  	</script>
  </body>
</html>
