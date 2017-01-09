<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<title>用户信息</title>
<link rel="stylesheet" type="text/css"href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript"src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript"src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
</head>
<body>
	<form id="fRoleInfomain" method="post">
		<div id="lRoleInfoMain" class="easyui-layout"style="width:497px;height:262px;">
			<div data-options="region:'south'" style="height:35px;">

				<table>
					<tr>
						<td width='106px'></td>
						<td width='100px' align="center"><a id="lbRolenfoSave"
							href="#">保存</a></td>
						<td width='60px'></td>
						<td width='100px' align="center"><a id="lbRoleinfoCancel"
							href="#">取消</a></td>
						<td></td>
					</tr>
				</table>
			</div>
			<div
				data-options="region:'west',iconCls:'icon-man',title:'角色信息',collapsible:false"
				style="width:240px;">

				<table>
					<tr height='20px'>
						<td></td>
					</tr>
					<tr>
						<td></td width='10px'>
						<td align='right'>角色编号：</td>
						<td><input id="tbRoleinfoId" name="role_id" type="text"
							style="width:120px"></td>
						<td style="color:#FF0000">*</td>
					</tr>
					<tr height='20px'>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td align='right'>名 称：</td>
						<td><input id="tbRoleinfoName" name="role_name"
							style="width:120px"></td>
						<td style="color:#FF0000">*</td>
					</tr>
					<tr height='20px'>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td align='right'>状 态：</td>
						<td><input id="tbRoleinfoStatus" name="status"
							style="width:120px"></td>
						<td style="color:#FF0000">*</td>
					</tr>
				</table>
			</div>
			<div
				data-options="region:'east',iconCls:'icon-more',title:'权限分配',collapsible:false"
				style="width:257px;">
				<ur id="trRolePrivilege"></ur>
			</div>
		</div>
	</form>
			<script type="text/javascript">
	$(function() {
		if (${flag}) {
			//flag==true的时候显示增加对话框
				$('#tbRoleinfoId').numberbox({
				required : true,
				iconCls : 'icon-more',
			});
			$('#tbRoleinfoName').textbox({
				required : true,
				
			});
			$('#tbRoleinfoStatus').combobox({
				height : '100%',
				valueField : 'id',
				textField : 'text',
				required : true,
				panelHeight : "auto", //自适应高度  
				data : [ {
					id : '1',
					text : '激活'
				}, {
					id : '0',
					text : '未激活'
				} ],
				value : '1',

			});
			//加载权限分配树
			$('#trRolePrivilege').tree({
								url : getContextPath()+ '/role/RoleMeunPower.do',
								checkbox : true,
								lines : true,
							});

			$("#lbRolenfoSave").linkbutton({
				iconCls : 'icon-save'
			});
			//添加保存按钮
			$('#lbRolenfoSave').bind('click',function() {
								$.messager.progress();
										var checkname = $('#tbRoleinfoName').textbox('getText');
										var checkid=$('#tbRoleinfoId').textbox('getText');
								$("#fRoleInfomain").form('submit',{
													url : getContextPath()+ "/role/addRoleInfo.do",
													onSubmit : function(param) {
														var isValid = $(this).form('validate');
														if (!isValid) {
															$.messager.progress('close');
															$.messager.alert('提示','请按照要求填写表单','info');
															return isValid; // 返回false终止表单提交
														}
														
														var checkpass = 1;
														var nodes = $('#trRolePrivilege').tree('getChecked');
														if(nodes == ""){
															$.messager.progress('close');
															$.messager.alert('提示','请至少选择一项权限','info');  
															return false;
														}												
														

														//用AJAX来判断角色名是否已经注册
														$.ajax({
																	url : getContextPath()+ "/role/checkRoleInfo.do",
																	async : false,
																	cache : false,
																	type : "post",
																	contentType:"application/x-www-form-urlencoded;charset=UTF-8",
																	data:{"checkname":checkname,"checkid":checkid},
																	dataType : "json",
																	success : function(result) {
																		//alert(result.isId+"|"+result.isName);
																		var reId = result.isId;
																		var reName=result.isName;
																		if (reId == 1||reName==1) {
																				var msg='';
																				if(reId==0){
																					msg='角色名已被注册';
																				}else if(reName==0){
																					msg='该编号已经存在';
																				}else{
																					msg='角色名已被注册,且编号已经存在';
																				}
																			$.messager.alert('提示',msg,'info');
																			checkpass = 0;

																		}
																	}
																});
														

														if (checkpass == 0) {
															$.messager.progress('close');
															return false;
														}
														
														
														var checknodes = $('#trRolePrivilege').tree('getChecked');
														var str_id="";  
														for (var i = 0; i < checknodes.length; i++) {  
															var nodesid = checknodes[i].id; 
														    str_id += nodesid;
														  	if(i!=checknodes.length-1){
														  	 	str_id +=";";  
														    }
														}
														//一定要将两部分加到一起。
														var OneNode = $('#trRolePrivilege').tree('getChecked','indeterminate');
														if(OneNode != ""){
															str_id+=";";
														}
														for (var i = 0; i < OneNode.length; i++) {  
															var nodesid = OneNode[i].id; 
														    str_id += nodesid;
														    if(i!=OneNode.length-1){
														  	 	str_id +=";";  
														    }
														}													
														param.str_id=str_id;
														
													},
													//表单提交成功后
													success : function(data) {
														if(data==''){
																$.messager.progress('close');
																$.messager.alert('提示','新增成功','info');
																$.messager.progress('close');
																$("#dlgRolelistInfo").dialog('close');
																//获取页数增加完后跳到最后一页查看  这里也相当于一次刷新操作
																var grid = $('#tbrolelist');
																grid.datagrid('reload');
															}else{
																$.messager.progress('close');
																$.messager.alert('提示','新增失败','info');
															}
													}
												});

							});

			$("#lbRoleinfoCancel").linkbutton({
				iconCls : 'icon-cancel'
			});
			//取消按钮
			$("#lbRoleinfoCancel").click(function(){    
					  $("#dlgRolelistInfo").dialog('close');
					}); 
			

		//修改角色信息的代码
		} else {
			$('#tbRoleinfoId').numberbox({
				required : true,
				editable:false,
				iconCls : 'icon-lock',
			});
			$('#tbRoleinfoName').textbox({
				required : true,
				
			});
			$('#tbRoleinfoStatus').combobox({
				height : '100%',
				valueField : 'id',
				textField : 'text',
				required : true,
				panelHeight : "auto", //自适应高度  
				data : [ {
					id : '1',
					text : '激活'
				}, {
					id : '0',
					text : '未激活'
				} ],
				value : '1',

			});
			
			var row=$('#tbrolelist').datagrid('getSelected');				
			$("#fRoleInfomain").form('load',{
					role_id:row.role_id,
					role_name:row.role_name,
					status:row.status,
					
			});
			
			//加载树
			$('#trRolePrivilege').tree({
								url : getContextPath()+ '/role/RoleMeunPower.do?role_id='+row.role_id,
								checkbox : true,
								lines : true,
							});

			$("#lbRolenfoSave").linkbutton({
				iconCls : 'icon-save'
			});

			$('#lbRolenfoSave').bind('click',function() {
								$.messager.progress();
										var checkname = $('#tbRoleinfoName').textbox('getText');
										var checkid=$('#tbRoleinfoId').textbox('getText');
								$("#fRoleInfomain").form('submit',{
													url : getContextPath()+ "/role/updateRoleinfo.do",
													onSubmit : function(param) {
														var checkpass = 1;
														//权限为必选
														var nodes = $('#trRolePrivilege').tree('getChecked');
																if(nodes == ""){
																	$.messager.progress('close');
																	$.messager.alert('提示','请至少选择一项权限','info');  
																	$("#divRoleinfoLoading").css("display","none");
																	return false;
																}												
														
												if(row.role_name!=checkname){
														//用AJAX来判断角色名是否已经注册
														$.ajax({
																	url : getContextPath()+ "/role/checkRoleInfo.do",
																	async : false,
																	cache : false,
																	type : "post",
																	contentType:"application/x-www-form-urlencoded;charset=UTF-8",
																	data:{"checkname":checkname,},
																	dataType : "json",
																	success : function(result) {
																		//alert(result.isId+"|"+result.isName);
																		var reName=result.isName;
																		if (reName==1) {
																			$.messager.progress('close');
																			$.messager.alert('提示','用户名已经存在','info');
																			checkpass=0;
																		}
																	}
																});
														}else{
															
															checkpass = 1;
														}		
														

														if (checkpass == 0) {
															$.messager.progress('close');
															return false;
														}

														var isValid = $(this).form('validate');
														if (!isValid) {
															$.messager.progress('close');
															$.messager.alert('提示','请按照要求填写表单','info');
														}
														var checknodes = $('#trRolePrivilege').tree('getChecked');
														var str_id="";  
														for (var i = 0; i < checknodes.length; i++) {  
															var nodesid = checknodes[i].id; 
														    str_id += nodesid;
														  	if(i!=checknodes.length-1){
														  	 	str_id +=";";  
														    }
														}
														//一定要将两部分加到一起。
														var OneNode = $('#trRolePrivilege').tree('getChecked','indeterminate');
														if(OneNode != ""){
															str_id+=";";
														}
														for (var i = 0; i < OneNode.length; i++) {  
															var nodesid = OneNode[i].id; 
														    str_id += nodesid;
														    if(i!=OneNode.length-1){
														  	 	str_id +=";";  
														    }
														}
														param.str_id=str_id;
														return isValid; // 返回false终止表单提交
													},
													
													success : function(data) {
														if(data==''){
															$.messager.progress('close');
															$.messager.alert('提示','修改成功','info');  
															$.messager.progress('close');
															$("#dlgRolelistInfo").dialog('close');
															//获取页数增加完后跳到最后一页查看  这里也相当于一次刷新操作
															var grid = $('#tbrolelist');
															var options = grid.datagrid('getPager').data("pagination").options;
															var total = options.total;
															var max = Math.ceil(total/ options.pageSize);
															var index = Math.ceil(total% options.pageSize);
															$('#tbrolelist').datagrid('gotoPage',max);
															$('#tbrolelist').datagrid('selectRow',index);
														}else{
															$.messager.progress('close');
															$.messager.alert('提示','修改失败','info');
														}
													}
												});

							});

			$("#lbRoleinfoCancel").linkbutton({
				iconCls : 'icon-cancel'
			});
			
			$("#lbRoleinfoCancel").click(function(){    
					  $("#dlgRolelistInfo").dialog('close');
					}); 

		}

	});
	</script>	
</body>
</html>
