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
<title>案卷目录列表</title>
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
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
</head>
<body>
	<script type="text/javascript">
		var pcdm = '${mlpc.dm}';
		if ("${action}" == "maintain") {
			objAjml = {
				add : function() {
					var divAjmlListInfo = $("<div id=\"dlgAjmlListInfo\"></div>");
					divAjmlListInfo.appendTo("body");
					$("#dlgAjmlListInfo").dialog({
						title : "新增案卷目录",
						width : 512,
						height : 300,
						closed : false,
						modal : true,
						onClose : function() {
							divAjmlListInfo.remove();
							$("#tblAjmlpcListDataGrid").datagrid("reload");
						}
					});
					$("#dlgAjmlListInfo").dialog('open').dialog(
							'refresh',
							getContextPath() + "/catalog/ajmlInfo_add.do?pcdm="
									+ pcdm);
				},
				update : function(index) {
					var divAjmlListInfo = $("<div id=\"dlgAjmlListInfo\"></div>");
					var row = $('#tblAjmlpcListDataGrid').datagrid('getData').rows[index];
					divAjmlListInfo.appendTo("body");
					$("#dlgAjmlListInfo").dialog({
						title : '编辑案卷目录',
						width : 512,
						height : 300,
						closed : false,
						modal : true,
						onClose : function() {
							divAjmlListInfo.remove();
							$("#tblAjmlpcListDataGrid").datagrid("reload");
						}
					});
					$("#dlgAjmlListInfo").dialog('open').dialog(
							'refresh',
							getContextPath()
									+ "/catalog/ajmlInfo_update.do?pcdm="
									+ pcdm + "&id=" + row.id);
				},
				del : function(index) {
					var row = $('#tblAjmlpcListDataGrid').datagrid('getData').rows[index];
					$.messager.confirm('确认', '您确认想要删除此记录吗？', function(sure) {
						if (sure) {
							var url = getContextPath()
									+ "/catalog/ajmlInfo_del.do?pcdm=" + pcdm
									+ "&id=" + row.id;
							$.ajax({
								url : url,
								type : "post",
								dateType : "json",
								async : false,
								cache : false,
								success : function(data) {
									var result = JSON.parse(data);
									if (result.success == 1) {
										$("#tblAjmlpcListDataGrid").datagrid(
												"reload");
									}
								}
							});
						}
					});
				},
				clear : function() {
					$.messager.confirm('确认', '您确认想要清空当前案卷目录吗？', function(sure) {
						if (sure) {
							var url = getContextPath()
									+ "/catalog/clearAjuanMl.do?pcdm=" + pcdm;
														$.ajax({
								url : url,
								type : "post",
								dateType : "json",
								async : false,
								cache : false,
								success : function(data) {
									var result = JSON.parse(data);
									if (result.success == 1) {
										$("#tblAjmlpcListDataGrid").datagrid(
												"load");
									}
								}
							});
						}
					});
				},
				import : function() {
					var divAjmlListInfo = $("<div id=\"dlgAjmlListInfo\"></div>");
					divAjmlListInfo.appendTo("body");
					$("#dlgAjmlListInfo").dialog({
						title : '批量导入目录',
						width : 512,
						height : 300,
						closed : false,
						modal : true,
						onClose : function() {
							divAjmlListInfo.remove();
							$("#tblAjmlpcListDataGrid").datagrid("reload");
						}
					});
					$("#dlgAjmlListInfo").dialog('open').dialog(
							'refresh',
							getContextPath()
									+ "/catalog/ajmlInfo_import.do?pcdm="
									+ pcdm);
				}
			};

			$("#dlgAjmlpcListMain").dialog({
				title : '${mlpc.pcmc}' + "维护",
				width : 1024,
				height : 600,
				closed : false,
				cache : false,
				shadow : true, //显示阴影
				resizable : false, //不允许改变大小
				modal : true, //是否窗口化
				inline : true, //是否在父容器中，若不在会出现很多BUG
			});
			$("#tblAjmlpcListDataGrid")
					.datagrid(
							{
								width : "100%",
								height : "100%",
								method : "post",
								url : getContextPath()
										+ "/catalog/mlpcBrowse.do?pcdm="+ pcdm,

								toolbar : [ {
									iconCls : 'icon-add',
									text : '新增',
									height : 40,
									handler : function() {
										objAjml.add();
									},

								}, {
									iconCls : 'icon-remove',
									text : '清空',
									height : 40,
									handler : function() {
										objAjml.clear();
									},

								}, {
									iconCls : 'icon-redo',
									text : '导入',
									height : 40,
									handler : function() {
										objAjml.import();
									},

								} ],
								onLoadSuccess : function(data) {
									$(".editBtn").linkbutton({
										plain : true,
										height : 23,
										iconCls : 'icon-edit'
									});
									$(".deleteBtn").linkbutton({
										plain : true,
										height : 23,
										iconCls : 'icon-no'
									});
								},

								//表单项
								columns : [ [
										{
											width : "10%",
											field : "id",
											title : "序号",
											halign : "center",
											align : "center",
											resizable : false,
										},
										{
											width : "36%",
											field : "mc",
											title : "文书名称",
											halign : "center",
											align : "center",
											resizable : false,
											formatter : function(value, row,
													index) {
												var abValue = value;
												if (value.length >= 20) {
													abValue = value.substring(
															0, 15)
															+ "...";
												}
												var content = '<span style="text-decoration:none;" href="#" title="' + value + '" class="note">'
														+ abValue + '</span>';
												return content;
											},
										},
										{
											width : "8%",
											field : "pym",
											title : "拼音码",
											halign : "center",
											align : "center",
											resizable : false,

										},
										{
											width : "14%",
											field : "tybz",
											title : "停用标志",
											align : "center",
											halign : "center",
											align : "center",
											resizable : false,
											formatter : function(value, row,
													index) {
												if (value == 0) {
													return "启用";
												} else {
													return "停用";
												}
											}
										},
										{
											width : "15%",
											field : "remark",
											title : "备注",
											align : "center",
											halign : "center",
											align : "center",
											resizable : false,
										},
										{
											width : "15%",
											field : "method",
											title : "操作",
											align : "center",
											formatter : function(value, row,
													index) {
												if (row.id == " ") {
													return " ";
												}
												return '<a href = "##" class="editBtn" onclick = "objAjml.update('
														+ index
														+ ')">编辑</a>&nbsp;&nbsp;<a href = "##" class="deleteBtn" onclick = "objAjml.del('
														+ index + ')">删除</a>';
											},
										}

								] ],

								pagination : true, //是否有分页工具
								pagePosition : "bottom", //分页工具位置
								pageSize : 15, //分页默认大小
								pageList : [ 15 ],
								striped : true, //斑马线样式,
								nowrap : true, //是否在一行显示所有，自动换行
								loadMsg : "努力加载中，请稍后。。。。",//加载信息
								rownumbers : true, //是否显示行号
								singleSelect : true, //只能同时选择一行
								showHeader : true,//显示行头，默认true;
								emptyMsg : "no records found",

							});
		} else if ("${action}" == "browse") {
			$("#dlgAjmlpcListMain").dialog({
				title : '${mlpc.pcmc}' + "一览",
				width : 1024,
				height : 600,
				closed : false,
				cache : false,
				shadow : true, //显示阴影
				resizable : false, //不允许改变大小
				modal : true, //是否窗口化
				inline : true, //是否在父容器中，若不在会出现很多BUG
			});

			$("#tblAjmlpcListDataGrid")
					.datagrid(
							{
								width : "100%",
								height : "100%",
								method : "post",
								url : getContextPath()
										+ "/catalog/mlpcBrowse.do?pcdm=" + pcdm,

								onLoadSuccess : function(data) {
									$(".editBtn").linkbutton({
										plain : true,
										height : 23,
										iconCls : 'icon-edit'
									});
									$(".deleteBtn").linkbutton({
										plain : true,
										height : 23,
										iconCls : 'icon-no'
									});
								},

								//表单项
								columns : [ [
										{
											width : "10%",
											field : "id",
											title : "序号",
											halign : "center",
											align : "center",
											resizable : true,
										},
										{
											width : "38%",
											field : "mc",
											title : "文书名称",
											halign : "center",
											align : "center",
											resizable : true,
											formatter : function(value, row,
													index) {
												var abValue = value;
												if (value.length >= 20) {
													abValue = value.substring(
															0, 15)
															+ "...";
												}
												var content = '<span style="text-decoration:none;" href="#" title="' + value + '" class="note">'
														+ abValue + '</span>';
												return content;
											},
										},
										{
											width : "15%",
											field : "pym",
											title : "拼音码",
											halign : "center",
											align : "center",
											resizable : true,

										},
										{
											width : "10%",
											field : "tybz",
											title : "停用标志",
											align : "center",
											halign : "center",
											resizable : true,
											formatter : function(value, row,
													index) {
												if (value == 0) {
													return "启用";
												} else {
													return "停用";
												}
											}
										}, {
											width : "25%",
											field : "remark",
											title : "备注",
											align : "center",
											halign : "center",
											resizable : true,
										}

								] ],

								pagination : true, //是否有分页工具
								pagePosition : "bottom", //分页工具位置
								pageSize : 15, //分页默认大小
								pageList : [ 15 ],
								striped : true, //斑马线样式,
								nowrap : true, //是否在一行显示所有，自动换行
								loadMsg : "努力加载中，请稍后。。。。",//加载信息
								rownumbers : true, //是否显示行号
								singleSelect : true, //只能同时选择一行
								showHeader : true,//显示行头，默认true;
								emptyMsg : "no records found",
								onLoadSuccess : function(data) {
									$(".note")
											.tooltip(
													{
														trackMouse : true,
														onShow : function() {
															$(this)
																	.tooltip(
																			'tip')
																	.css(
																			{
																				width : '300',
																				boxShadow : '1px 1px 3px #292929'
																			});
														}
													});
								},

							});
		}
	</script>
		<!--  <div>内容测试内容测试内容测试内容测试</div>-->
		<table id="tblAjmlpcListDataGrid"></table>
	</div>
</body>
</html>

