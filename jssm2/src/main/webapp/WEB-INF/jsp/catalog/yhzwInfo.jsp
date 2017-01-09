<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
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
		$(function() {
			if (${isNew}) {
				$.extend($.fn.validatebox.defaults.rules, {
					Maxnum : {
						validator : function(value) {
							return value.length <= 3;
						},
						message : '有效数字为三位'
					}
				});
				$('#dm').numberbox({
					iconCls : "icon-mini-edit",
					iconAlign : "right",
					prompt : '请输入3为有效数字',
					validType : 'Maxnum',
					required : true,
					width : 200,
					formatter : function(value) {
						if (value.length == 1) {
							return '00' + value;
						} else if (value.length == 2) {
							return '0' + value;
						} else {
							return value;
						}
					},
				});

				$('#mc').textbox({
					iconCls : "icon-mini-edit",
					iconAlign : "right",
					required : true,
					width : 200,
				});
				
				$("#yhzwTybz").combobox({
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

				$('input[name="remark"]').textbox({
					multiline : true,
					width : 200,
					height : 50,
				});

				$('#Save').linkbutton({
					iconCls : 'icon-save',
				});

				$('#Cancel').linkbutton({
					iconCls : 'icon-cancel',
				});

			$('#Cancel').bind('click', function() {
				$("#dlgRolelistInfo").dialog('close');
			}),

			$('#Save').bind('click',function() {
			$.messager.progress();
			$('#yhzwff').form('submit',{
								url : getContextPath()+ '/catalog/addyhzwinfo.do',
								onSubmit : function() {
									var isValid = $(this).form('validate');
									if (!isValid) {
										$.messager.progress('close');
										$.messager.alert('提示','请按照要求填写表单','info');
										return isValid; // 返回false终止表单提交
									}
									if (checkid == ''|| checkname == '') {
										$.messager.alert('提示','请按照要求填写表单','info');
										return false; // 返回false终止表单提交
									}
									var checkid = $('#dm').numberbox('getValue');
									var checkname = $('#mc').textbox('getText');
									$.messager.progress('close');

									var checkpass = 0;
									$.ajax({url : getContextPath()+ '/catalog/checkInfo.do',
												async : false,
												cache : false,
												type : 'POST',
												dataType : "json",
												data : {
													checkid : checkid,
													checkname : checkname
												},
												success : function(result) {
													var reId = result.id;
													var reName = result.name;
													if (reId >= 1|| reName >= 1) {
														var msg = '';
														if (reId == 0) {
															msg = '该职务名称已被注册';
														} else if (reName == 0) {
															msg = '该职务编号已经存在';
														} else {
															msg = '职务名已被注册,且职务编号已经存在';
														}
														$.messager.alert('提示',msg,'info');
													} else {
														checkpass = 1;
													}
												}
											});
									if (checkpass == 1) {
										return true;
									}
									return false;
								},
								success : function(data) {
									if (data == '') {
										$.messager.alert('提示','新增成功','info');
										$("#dlgRolelistInfo").dialog('close');
										$('#yhzwListdg').datagrid('reload');
									} else {
										$.messager.progress('close');
										$.messager.alert('提示','新增失败','info');
									}
								}
							});

	});

} else {
		//修改信息
		$('#dm').textbox({
			iconCls : "icon-lock",
			iconAlign : "right",
			editable : false,
			required : true,
			width : 200,
		});

		$('#mc').textbox({
			iconCls : "icon-more",
			iconAlign : "right",
			required : true,
			width : 200,
		});
		
		$("#yhzwTybz").combobox({
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

		$('input[name="remark"]').textbox({
			multiline : true,
			height : 50,
			width : 200,
		});

		$('#Save').linkbutton({
			iconCls : 'icon-save',
		});

		$('#Cancel').linkbutton({
			iconCls : 'icon-cancel',
		});

		$('#Cancel').bind('click', function() {
			$("#dlgRolelistInfo").dialog('close');
		});

		//获取选中的列加载到form中
		var row = $('#yhzwListdg').datagrid('getSelected');
		$('#yhzwff').form('load', {
			dm : row.dm,
			mc : row.mc,
			tybz : row.tybz,
			remark : row.remark,
		});

	$('#Save').bind('click',function() {
		$.messager.progress();
		$('#yhzwff').form('submit',{
					url : getContextPath()+ '/catalog/updateyhzwinfo.do',
					onSubmit : function() {
						var checkname = $('#mc').textbox('getText');
						var checkid = $('#dm').numberbox('getValue');
						$.messager.progress('close');
						if (checkname == '') {
							$.messager.alert('提示','用户名不能为空','info');
							return false;
						}
						var checkpass = 0;
						if (row.mc == checkname) {
							checkpass = 1;
						} else {
			$.ajax({
						url : getContextPath()+ '/catalog/checkInfo.do',
						async : false,
						cache : false,
						type : 'POST',
						dataType : "json",
						data : {
							checkid : checkid,
							checkname : checkname,
						},
						success : function(result) {
							var reName = result.name;
							if (reName == 0) {
								checkpass = 1;//用户不存在时通过表单验证
							}
						}

					});
						}
						if (checkpass == 1) {
							return true;
						}
						$.messager.alert('提示','该职务名称已经存在','info');
						return false;

					},

					success : function(data) {
						if (data == '') {
							$.messager.alert('提示','修改成功','info');
							$("#dlgRolelistInfo").dialog('close');
							$('#yhzwListdg').datagrid('reload');
						} else {
							$.messager.progress('close');
							$.messager.alert('提示','修改失败','info');
						}

					}
			});

	});

			}

		});
	</script>
	<form id="yhzwff" method="post">
		<table align="center" style="margin-top:20px;">
			<tr>
				<td>职务代码:</td>
				<td><input type="text" name="dm" id="dm"></td>
				<td style="color:#FF0000">*</td>
			</tr>
			<tr align="right">
				<td height="10px"></td>
			</tr>
			<tr>
				<td>职务名称:</td>
				<td><input type="text" name="mc" id="mc"></td>
				<td style="color:#FF0000">*</td>
			</tr>
			<tr align="right">
				<td height="10px"></td>
			</tr>
			<tr>
				<td>停用标志:</td>
				<td><input id = "yhzwTybz" name = "tybz"/></td><td style="color:#FF0000">*</td>
			</tr>
			<tr align="right">
				<td height="10px"></td>
			</tr>
			<tr>
				<td align='right'>备注:</td>
				<td><input type="text" name="remark" value=""></td>
			</tr>
		</table>
		<table align="center">
			<tr>
				<td height="20px"></td>
			</tr>
			<tr>
				<td align="center"><a id="Save" href="#">保存</a></td>
				<td width="50px"></td>
				<td align="center"><a id="Cancel" href="#">取消</a></td>
				<td></td>
			</tr>
		</table>
	</form>

</body>
</html>
