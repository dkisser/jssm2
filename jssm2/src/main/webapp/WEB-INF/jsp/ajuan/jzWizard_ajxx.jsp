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
<title>卷宗整理导航--查找案件信息</title>


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

<div id="dlgAjxxListInfo">
	<form id="formAjxxInfo">
		<table align="center" style="margin-top:30px;">
			<tr>
				<td align="right">当事人：</td>
				<td><input id="dsr" name="dsr" /></td>
				<td><span style="color:red">&nbsp;* 模糊条件</span></td>
			</tr>
			<tr height=5></tr>
			<tr>
				<td align="right">案号：</td>
				<td><input id="ah" name="ah" /></td>
				<td><span style="color:red">&nbsp;* 精确条件</span></td>
			</tr>
			<tr height=5></tr>
			<tr>
				<td align="right">是否结案：</td>
				<td><input id="sfja" name="sfja" /></td>
			</tr>
			<tr height=5></tr>
			<tr>
				<td align="right">立案日期：</td>
				<td><input id="larq1" name="larq1" type="text" /><span>&nbsp;到&nbsp;</span><input
					id="larq2" name="larq2" type="text" /></td>
			</tr>
			<tr height=5></tr>
			<tr>
				<td align="right">结案日期：</td>
				<td><input id="jarq1" type="text" name="jarq1" /><span>&nbsp;到&nbsp;</span><input
					id="jarq2" type="text" name="jarq2" /></td>
			</tr>
			<tr height=20></tr>
			<tr>
				<td></td>
				<td align="center"><a id="findAjxx">查找案件</a></td>
			</tr>
		</table>
		</table>
	</form>
</div>
<script type="text/javascript">
	obj={
		findCaseInfo:function(jzewm){
				$('#center').panel('refresh', getContextPath()+ "/ajuan/ajxxList.do?jzewm="+ jzewm);
		}
	};
	var currJzType = "${currJzType}";
	var title = "";
	if(currJzType == "js"){
		title = "即时卷宗整理导航--查找案件信息";
	}else if(currJzType == "ls"){
		title = "历史卷宗整理导航--查找案件信息";
	}
	$("#dlgAjxxListInfo").dialog({
			title: title, 	
		    width: 512,    
		    height: 300,
		    inline:true,
		    closed: false,
		    modal: true,
	});
	$("#dsr").textbox({
		width : 220,
	});
	$('#dsr').textbox('textbox').focus();
	
	$("#ah").textbox({
		width : 220,
	});
	$("#sfja").combobox({
		width : 220,
		value : "未知",
		editable : false,
		panelHeight : 100,
		valueField : 'label',
		textField : 'value',
		data : [ {
			label : '未知',
			value : '未知'
		}, {
			label : '已结案',
			value : '已结案'
		}, {
			label : '未结案',
			value : '未结案'
		} ],
	});

	//获取当前日期
	var myDate = new Date();
	var today = myDate.toLocaleDateString();
	//获取前半部分事件
	var y = myDate.getFullYear() - 5;
	var m = myDate.getMonth() + 1;
	var d = myDate.getDate();

	$("#larq1").datebox({ //立案日期前半部分
		width : 100,
		value : y + "-" + m + "-" + d,
		editable:false,
		onSelect : function(date) {
			var a = $("#larq2").datebox("getValue");
			var currDate = new Date(a);
			if (currDate.getTime() < date.getTime()) {
				$.messager.alert('警告', '立案起始时间必须在结束时间之前', 'warning');
				$('#larq1').datebox('setValue', y + "-" + m + "-" + d);
			}
		}
	});
	$("#larq2").datebox({ //立案日期后半部分
		width : 100,
		value : today,
		editable:false,
		onSelect : function(date) {
			var a = $("#larq1").datebox("getValue");
			var preDate = new Date(a);
			if (preDate.getTime() > date.getTime()) {
				$.messager.alert('警告', '立案结束时间必须在起始时间之后', 'warning');
				$('#larq2').datebox('setValue', currDate);
			}
		}
	});
	
	$("#jarq1").datebox({ //结案日期前半部分
		width : 100,
		editable:false,
		onSelect : function(date) {
			var a = $("#jarq2").datebox("getValue");
			var currDate = new Date(a);
			if (currDate.getTime() < date.getTime()) {
				$.messager.alert('警告', '结案起始时间必须在结束时间之前', 'warning');
				$('#jarq1').datebox('setValue',currDate.toLocaleDateString());
			}
		}
	});
	$("#jarq2").datebox({ //结案日期后半部分
		width : 100,
		editable:false,
		onSelect : function(date) {
			var a = $("#jarq1").datebox("getValue");
			var preDate = new Date(a);
			if (preDate.getTime() > date.getTime()) {
				$.messager.alert('警告', '结案结束时间必须在起始时间之后', 'warning');
				$('#jarq2').datebox('setValue', preDate.toLocaleDateString());
			}
		}

	});
	$("#findAjxx").linkbutton({
		iconCls:"icon-search",
	});
	
	
	$("#findAjxx").click(
		function() {
			$.messager.progress();	// 显示进度条
			$("#formAjxxInfo").form(
					'submit',
					{
						url : getContextPath()
								+ "/ajuan/findAjxxInfo.do",
						onSubmit : function(param) {
						},
						success : function(info) {
							$.messager.progress('close');	// 如果提交成功则隐藏进度条
							if(info==null||info==""){
								obj.findCaseInfo('${jzewm}');
							}
					},
			});
	});
			
				
		
		$("#dlgAjxxListInfo").bind("keydown",function(e){
			var theEvent = e || window.event;    
			var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
			//code == 9   
			if (code == 13) {    
			    //回车执行查询
			    	$.messager.progress();	// 显示进度条
					$("#formAjxxInfo").form(
							'submit',
							{
								url : getContextPath()
										+ "/ajuan/findAjxxInfo.do",
								onSubmit : function(param) {
								},
								success : function(info) {
									$.messager.progress('close');	// 如果提交成功则隐藏进度条
									if(info==null||info==""){
										obj.findCaseInfo('${jzewm}');
									}
							},
					});
		        }    
		});
		
</script>
</body>
</html>
