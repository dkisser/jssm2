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
if ("${isADD}" == "true") {
	$("#cmbfydmip").combobox ({
		url: getContextPath() + "/catalog/getAllFymc.do",    
	    valueField: "id",    
	    textField: "text", 
	    onLoadSuccess: function () {
	    	var val =$(this).combobox("getData");
	    	$(this).combobox("select",val[0].id);
	    },
	});

	var myDate = new Date();
	$("#txbqyndip").textbox ({
		iconCls: "",
		iconAlign: "right",
		required: true,
		value: myDate.getFullYear(),
	});

	$("#cmblxip").combobox ({
		url: getContextPath() + "/catalog/getAllLxName.do",    
	    valueField: "id",    
	    textField: "text", 
	    onLoadSuccess: function () {
	    	var val =$(this).combobox("getData");
	    	$(this).combobox("select",val[0].id);
	    },

	});

	$("#cmbyjflip").combobox ({
		url: getContextPath() + "/catalog/getAllCAjuanYjfl.do",    
	    valueField: "id",    
	    textField: "text",
	    onLoadSuccess: function () {
	    	var val = $(this).combobox("getData");
	    	$(this).combobox("select",val[0].id);
	    	var url = getContextPath() + "/catalog/getSpzhByAJuanYjfl.do?dm="+ val[0].id;
	    	$("#cmbspzhip").combobox('reload', url);
	    },
	    onSelect: function (record) {
	    	//一旦选择,我们就要发送一个ajax请求,到后台得到与他二级联动的数据
	    	var url = getContextPath() + "/catalog/getSpzhByAJuanYjfl.do?dm="+ record.id; 
	    	$("#cmbspzhip").combobox('reload', url);
	    },
	});


	$("#cmbspzhip").combobox ({
		//这是与yjfl关联的combobox
		//当yjfl为空时,该处为空
		//当yjfl非空时,该出为在一级分类下查询得到的数据
	    valueField: "id",    
	    textField: "text",
	    required: true,
	    
	    //加载数据 完成后默认的选中第一条数据
	    onLoadSuccess: function () {
	    	var val=$(this).combobox("getData");
	    	if (val.length != 0) {
	    		$(this).combobox("select",val[0].text);
	    	} else {
	    		$(this).combobox ("clear");
	    	}
	    }

	});

	$("#txbpcmcip").textbox ({
		iconCls: "",
		iconAlign: "right",
		required: true,
	});

	//添加失去焦点事件
	$("input",$("#txbpcmcip").next("span")).blur(function(){
		var pcmc = $("#txbpcmcip").val();
		$.ajax ({
			url:getContextPath() + "/catalog/checkPcmc.do",
			type: "post",
			cache: false,
			async: false,
			data: {"pcmc":pcmc},
			dataType: "json",
			success: function (result) {
				var refound = result.success;
				var found = 0;
				if (refound == found ) {
					$.messager.alert("提示","该名称已经存在,请您换一个!","info");
				}
			},
			error:function(data){
				$.messager.show ({
					title: "Sorry",
					msg: "系统出错了,请您重新操作!",
					showType: "fade",
					timeout: 2000,
				}); 
			},
		});
	});

	$("#cmbtybzip").combobox ({
		required: true,
		valueField: "id",
		textField: "text",
		value: "0",
		data: [
			{
				id: "0",
				text: "启用",
			},
			{
				id: "1",
				text: "停用",
			},
			],
		
	});

	$("#txbremarkip").textbox ({
		iconCls: "",
		iconAlign: "right",
		multiline: true,
		height: 45,
	});

	$("#saveBtn_mlpc").linkbutton ({
		iconCls: "icon-ok",
		iconAlign: "left",
	});
	
	$("#saveBtn_mlpc").bind("click",function () {
		$.messager.progress(); 
		$("#dataForm").form ("submit",{
			url: getContextPath() + "/catalog/saveMlpc.do",
			onSubmit: function (param) {
				//先做一个唯一性检查pcmc
				var pcmc = $("#txbpcmcip").val();
				var checkpass = 1;
				$.ajax ({
					url: getContextPath() + "/catalog/checkPcmc.do?",
					cache: false,
					async: false,
					type: "post",
					data: {"pcmc":pcmc},
					dataType: "json",
					success: function (result) {
						var refound = result.success;
						var found =0;
						if (refound == found) {
							checkpass = 0;
							$.messager.progress("close");
							$.messager.alert("提示","批次名称已经存在,请您换一个!","info")
						}
					},
					error:function(data){
						$.messager.show ({
							title: "Sorry",
							msg: "系统出错了,请您重新操作!",
							showType: "fade",
							timeout: 2000,
						}); 
					},
					
				});
				if (checkpass == 0) {
					return false;
				}
				param.savenew = "true";
				var valid = $(this).form ("validate");
				if (!valid) {
					$.messager.progress("close");
					$.messager.alert("提示","请按要求填写表单!","info");
				}
				$.messager.progress("close");
				return valid;
			},
			success: function (data) {
				$.messager.alert("提示","恭喜,添加成功!","info");
				$("#dlgmlpcInfodiv").dialog("close");
				$("#dgmlpclistdiv").datagrid("reload");
			},
		}) ;
	});

	$("#cancelBtn_mlpc").linkbutton ({
		iconCls: "icon-no",
		iconAlign: "left",
	});


	$("#cancelBtn_mlpc").bind ("click",function () {
		$("#dlgmlpcInfodiv").dialog ("close");
	});

} else {
	$("#cmbfydmip").combobox ({

		url: getContextPath() + "/catalog/getAllFymc.do",    
	    valueField: "id",    
	    textField: "text", 
	    readonly: true,
		value: "${editMlpc.fydm}",
	});

	$("#txbqyndip").textbox ({
		iconCls: "icon-lock",
		iconAlign: "right",
		readonly: true,
		value: "${editMlpc.qynd}",
	});

	$("#cmblxip").combobox ({
		url: getContextPath() + "/catalog/getAllLxName.do",    
	    valueField: "id",    
	    textField: "text",
	    readonly: true,
		value: "${editMlpc.lx}",
	});


	$("#cmbyjflip").combobox ({
		url: getContextPath() + "/catalog/getAllCAjuanYjfl.do",    
	    valueField: "id",    
	    textField: "text",
	    readonly: true,
	    value: "${editMlpc.mc}",
	});


	$("#cmbspzhip").combobox ({
		url: getContextPath() + "",    
	    valueField: "id",    
	    textField: "text",
	    readonly: true,
	    value: "${editMlpc.zhdm}",
	});

	$("#txbpcmcip").textbox ({
		iconCls: "",
		iconAlign: "right",
		value: "${editMlpc.pcmc}",
	});
	//批次名称的唯一性检查(数据库有问题)
	var oldpcmc = $("#txbpcmcip").val();
	//添加失去焦点事件
	$("input",$("#txbpcmcip").next("span")).blur(function(){
		var pcmc = $("#txbpcmcip").val();
		if (oldpcmc != pcmc){
			$.ajax ({
			url:getContextPath() + "/catalog/checkPcmc.do",
			type: "post",
			cache: false,
			async: false,
			data: {"pcmc":pcmc},
			dataType: "json",
			success: function (result) {
				var refound = result.success;
				var found = 0;
				if (refound == found ) {
					$.messager.alert("提示","该名称已经存在,请您换一个!","info");
				}
			},
			error:function(data){
				$.messager.show ({
					title: "Sorry",
					msg: "系统出错了,请您重新操作!",
					showType: "fade",
					timeout: 2000,
				}); 
			},
		});
		}
	});

	$("#cmbtybzip").combobox ({
		required: true,
		valueField: "id",
		textField: "text",
		value: "${editMlpc.tybz}",
		data: [
			{
				id: "0",
				text: "启用",
			},
			{
				id: "1",
				text: "停用",
			},
			],
		
	});

	$("#txbremarkip").textbox ({
		iconCls: "",
		iconAlign: "right",
		multiline: true,
		height: 45,
		value: "${editMlpc.remark}",
	});


	$("#saveBtn_mlpc").linkbutton ({
		iconCls: "icon-ok",
		iconAlign: "left",
	});
	
	$("#saveBtn_mlpc").bind("click",function () {
		var oldpcmc = "${editMlpc.pcmc}"
		$.messager.progress();
		$("#dataForm").form ("submit",{
			url: getContextPath() + "/catalog/saveMlpc.do",
			onSubmit: function (param) {
				//在提交之前我们要对表单的pcmc再做一次校验
				var pcmc = $("#txbpcmcip").val();
				var checkpass = 1;
				if (oldpcmc != pcmc) {
					$.ajax({
					url: getContextPath() + "/catalog/checkPcmc.do",
					cache: false,
					async: false,
					type: "post",
					data: {"pcmc":pcmc},
					dataType: "json",
					success: function (result) {
						var refound = result.success;
						var found = 0;
						if (refound == found) {
							checkpass = 0;
							$.messager.progress("close");
							$.messager.alert("提示","批次名称已经存在,请您换一个!","info");
						}
					},
					error: function () {
						$.messager.show({
							title: "Sorry",
							msg: "系统出错了,请您重新操作!",
							showType: "fade",
							timeout: 2000,
						});
					},
				});
			}
				
				if (checkpass == 0) {
					return false;
				}
				param.savenew = "false";
				param.dm ="${editMlpc.dm}";
				var valid =  $(this).form("validate");
				if (!valid) {
					$.messager.progress("close");
					$.messager.alert("提示","请按要求填写表单!","info");
				}
				$.messager.progress("close");
				return valid;
			},
			success: function () {
				$.messager.alert("提示","恭喜,编辑成功!","info");
				$("#dlgmlpcInfodiv").dialog("close");
				//选中编辑的这一行
				$("#dgmlpclistdiv").datagrid("reload");
			},
		});
	} );

	$("#cancelBtn_mlpc").linkbutton ({
		iconCls: "icon-no",
		iconAlign: "left",
	});


	$("#cancelBtn_mlpc").bind ("click",function () {
		$("#dlgmlpcInfodiv").dialog ("close");
	});

}
</script>
  <div id="divmlpcInfodlg">
  	<div style="padding-top: 30px;"></div>
  	<form id="dataForm" method="POST">
  	<table id="DataFromtb">
  		<tr><td width="20px;"></td><td>启用法院:</td><td><input id="cmbfydmip" name="fydm" /></td><td style="color:#FF0000">*</td></tr>
  		<tr><td></td><td>启用年份:</td><td><input id="txbqyndip" name="qynd" /></td><td style="color:#FF0000">*</td><td>案卷类型:</td><td><input id="cmblxip" name="lx" /></td><td style="color:#FF0000">*</td></tr>
  		<tr><td></td><td>案卷一级分类:</td><td><input id="cmbyjflip" name="mc" /></td><td style="color:#FF0000">*</td><td>审判字号:</td><td><input id="cmbspzhip" name="zhdm" /></td><td style="color:#FF0000">*</td></tr>
  		<tr><td></td><td>批次名称:</td><td><input id="txbpcmcip" name="pcmc" /></td><td style="color:#FF0000">*</td></tr>
  		<tr><td></td><td>停用标志:</td><td><input id="cmbtybzip" name="tybz" /></td><td style="color:#FF0000">*</td></tr>
  		<tr><td></td><td>备注:</td><td><input id="txbremarkip" name="remark" /></td></tr>
  	</table>
  	</form>
  	<table style="padding-top: 10px;">
  		<tr><td width="140"></td><td width="100" align="center"><a id="saveBtn_mlpc" href="#">保存</a></td><td width="100" align="center"><a href="#" id="cancelBtn_mlpc">取消</a></td></tr>
  	</table>
  </div>
</body>
</html>
