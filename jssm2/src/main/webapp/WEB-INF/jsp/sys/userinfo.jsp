<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>当前用户信息</title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>

</head>

<body>
	<div id="dlguserInfodiv">
		<form id="dataForm" method="post">
			<!-- 这个地方的height不能放到tr中,放TR中没用 -->
			<table>
				<tr>
					<td height="10px"></td>
				</tr>
			</table>
			<table>
				<tr>
					<td width="120px"></td>
					<td style="text-align: 'right';">用户编号:</td>
					<td><input id="txbuserId" style="padding-left: 6px;width:160px;" name="userId" /></td>
					<td style="color:#FF0000">*</td>
				</tr>
				<tr>
					<td></td>
					<td style="text-align: 'right';">登&nbsp;录&nbsp;名&nbsp;:</td>
					<td><input id="txbuname" style="padding-left: 6px;width:160px;" name="uname" /></td>
					<td style="color:#FF0000">*</td>
				</tr>
				<tr>
					<td></td>
					<td style="text-align: 'right';">用户姓名:</td>
					<td><input id="txbname" style="padding-left: 6px;width:160px;" name="name" /></td>
					<td style="color:#FF0000">*</td>
				</tr>
				<tr>
					<td></td>
					<td style="text-align: 'right';">用户密码:</td>
					<td><input id="txbupw" style="padding-left: 6px;width:160px;" name="upw" /></td>
					<td style="color:#FF0000">*</td>
				</tr>
				<tr>
					<td></td>
					<td style="text-align: 'right';">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位:</td>
					<td><input id="cmbposition" name="zw" style="padding-left: 6px;width:160px;" /></td>
					<td style="color:#FF0000">*</td>
				</tr>
				<tr>
					<td></td>
					<td style="text-align: 'right';">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</td>
					<td><input id="txbjg" name="bm" style="padding-left: 6px;width:160px;" /></td>
					<td style="color:#FF0000">*</td>
				</tr>
				<tr>
					<td></td>
					<td style="text-align: 'right';">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色:</td>
					<td><input id="cmbtype" name="role" style="padding-left: 6px;width:160px;" /></td>
					<td style="color:#FF0000">*</td>
				</tr>
			</table>
			<table>
				<tr>
					<td height="10px"></td>
				</tr>
			</table>
			<table>
				<tr>
					<td width="140px"></td>
					<td width="100px" style="text-align: center"><a href="#" id="okBtn_user">确定</a></td>
					<td width="100px" style="text-align: center"><a href="#" id="cancelBtn_user">取消</a></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {    
		 passWordminLength: {    
		        validator: function(value, param){
		            return value.length >=6;    
		        },    
		        message: '密码至少为6位',  
		    }  
	    });
		$.extend($.fn.validatebox.defaults.rules, {    
		    maxLength: {    
		        validator: function(value, param){
		            return value.length <= param[0];    
		        },    
		        message: '请输入1~5位有效数字'   
		    },
		   
	   
		    userIdValidate:{
		    	validator: function(value){
		    		if(value.length>5){
		    			return false;
		    		}
		    		var flag = true;
		    		$.ajax({
						url : getContextPath() + "/user/checkuserId.do?userId=" + value,
						cache : false,
						async : false,
						type : "post",
						dataType : "json",
						success : function(result) {
							if(parseInt(result.success) == 0){
								flag = false;
							}
							if(parseInt(result.success) == 1){
								flag = true;
							}
						}
						
					});
		    		return flag;
		        },    
		        message: '用户编号已存在！' 
		    },
		    uNameValidate:{
		    	validator: function(value){
		    		var flag = true;
		    		$.ajax({
						url : getContextPath() + "/user/checkUname.do?uname=" + value,
						cache : false,
						async : false,
						type : "post",
						dataType : "json",
						success : function(result) {
							if(parseInt(result.success) == 0){
								flag = false;
							}
							if(parseInt(result.success) == 1){
								flag = true;
							}
						}
						
					});
		    		return flag;
		        },    
		        message: '用户名已存在！' 
		    }
		});
		
		if ("${isADD}" == "true") {
			//新增用户
			$("#txbuserId").numberbox({
				iconCls : "icon-mini-edit",
				iconAlign : "right",
				required : true,
				prompt:'请输入1~5位有效数字',
				validType:['maxLength[5]','userIdValidate'],
			});

			$("#txbuname").textbox({
				iconCls : "icon-more",
				iconAlign : "right",
				required : true,
				prompt:'请输入用户名',
				validType:'uNameValidate'
			});

			$("#txbname").textbox({
				iconCls : "icon-man",
				iconAlign : "right",
				prompt:'请输入真实姓名',
				required : true,
			});

			$("#txbupw").textbox({
				iconCls : "icon-key",
				iconAlign : "right",
				validType:"passWordminLength",
				prompt:'密码至少为6位',
				required : true,
				type : "password",

			});

			$("#cmbposition").combobox({
				url : getContextPath() + "/user/findYHZW.do",
				iconAlign : "right",
				editable:false,
				required : true,
				valueField : 'text',
				textField : 'text',
			});

			$("#cmbtype").combobox({
				url : getContextPath() + "/user/findAllRole.do",
				iconAlign : "right",
				valueField : 'text',
				textField : 'text',
				required : true,
				editable:false,
			});

			$("#txbjg").combobox({
				data : getJson(getContextPath() + '/user/findJG.do'),
				iconAlign : "right",
				required : true,
				editable:false,
				valueField : 'mc',
				textField : 'mc',
			});

			$("#okBtn_user").linkbutton({
				iconCls : "icon-ok",
			});

			$("#okBtn_user").click(function() {
				$.messager.progress(); // 显示进度条
				$("#dataForm").form("submit", {
					url : getContextPath() + "/user/addUserInfo.do",
					onSubmit : function(param) {
						var valid = $(this).form("validate");
						if (!valid) {
							$.messager.progress('close');
							$.messager.alert("提示", "请按照要求填写表单", "info");
						}
						return valid;
					},
					success : function(data) {
						$.messager.progress('close');
						if (data == null || data == '') {
							$.messager.alert('提示', '新增成功', 'info');
							$("#dlguserInfo").dialog("close");
							$("#dguserListtb").datagrid('reload');
						} else {
							$.messager.alert('提示', '新增失败', 'info');
						}
					},
				});

			});

			$("#cancelBtn_user").linkbutton({
				iconCls : "icon-no",
			});

			$("#cancelBtn_user").click(function() {

				$("#dlguserInfo").dialog("close");

			});
		} else {
			$("#txbuserId").textbox({
				iconCls : "icon-lock",
				iconAlign : "right",
				required : true,
				readonly : true,
				value : "${editUser.userId}",
			});

			$("#txbuname").textbox({

				iconCls : "icon-lock",
				iconAlign : "right",
				required : true,
				readonly : true,
				value : "${editUser.uname}",

			});

			$("#txbname").textbox({

				iconCls : "icon-man",
				iconAlign : "right",
				required : true,
				value : "${editUser.name}",

			});

			$("#txbupw").textbox({

				iconCls : "icon-key",
				iconAlign : "right",
				required : true,
				type : "password",
				value : "${editUser.upw}",

			});

			$("#cmbposition").combobox({
				data : getJson(getContextPath() + '/user/findYHZW.do'),
				iconAlign : "right",
				required : true,
				valueField : 'text',
				textField : 'text',
				value : "${vuser.zw}",
			});

			$("#cmbtype").combobox({
				url : getContextPath() + "/user/findAllRole.do",
				iconAlign : "right",
				required : true,
				valueField : 'text',
				textField : 'text',
				value : "${vuser.role}",
			});

			$("#txbjg").combobox({
				data : getJson(getContextPath() + '/user/findJG.do'),
				iconAlign : "right",
				required : true,
				valueField : 'mc',
				textField : 'mc',
				value : "${vuser.jg}",

			});
			$("#okBtn_user").linkbutton({
				iconCls : "icon-ok",
			});

			$("#okBtn_user").click(function() {
				$.messager.progress(); // 显示进度条
				$("#dataForm").form("submit", {
					url : getContextPath() + "/user/updateUserInfo.do",
					onSubmit : function(param) {
						var valid = $(this).form("validate");
						if (!valid) {
							$.messager.alert("提示", "请按照提示填写表单!", "info");
						}
						$.messager.progress('close');
						return valid;
					},
					success : function(data) {
						$.messager.progress('close');
						if (data == null || data == '') {
							$("#dlguserInfo").dialog("close");
							$.messager.alert("提示", "修改成功", "info");
							$("#dguserListtb").datagrid('reload');
						} else {
							$.messager.alert("提示", "修改失败", "info");
						}
					}

				});
			});

			$("#cancelBtn_user").linkbutton({
				iconCls : "icon-no",
			});

			$("#cancelBtn_user").click(function() {
				$("#dlguserInfo").dialog("close");
			});

		}
	</script>
</body>
</html>
