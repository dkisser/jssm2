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
    <title></title>
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
	//定义mlpcList的操作命名空间
	mlpcList = {
		add: function () {
			var dlgmlpcInfodiv = $("<div id='dlgmlpcInfodiv'></div>");
			dlgmlpcInfodiv.appendTo("body");
			$("#dlgmlpcInfodiv").dialog ({
				href: getContextPath() + "/catalog/addMlpcInfo.do",
				title: "新建案卷目录批次",
				width: 512,
				height: 300,
				inline: true,
				modal: true,
			});
		},

		edit: function (index) {
			var dlgmlpcInfodiv = $("<div id='dlgmlpcInfodiv'></div>");
			dlgmlpcInfodiv.appendTo("body");
			var row=$("#dgmlpclistdiv").datagrid("getData").rows[index];
			$("#dlgmlpcInfodiv").dialog ({
				href: getContextPath() + "/catalog/editMlpcInfo.do?dm=" + row.dm,
				title: "编辑案卷目录批次",
				width: 512,
				height: 300,
				inline: true,
				modal: true,
			});

		},

		catalogmaintain: function (index) {
			var row=$("#dgmlpclistdiv").datagrid("getData").rows[index];
			var dlgmlpcInfodiv = $("<div id='dlgAjmlpcListMain'></div>");
			dlgmlpcInfodiv.appendTo("body");
			$("#dlgAjmlpcListMain").dialog ({
				width: 1024,
				height: 600,
				inline: true,
				modal: true,
				onClose:function(){
					dlgmlpcInfodiv.remove();
					$("#dgmlpclistdiv").datagrid("reload");
				}
			});
			$("#dlgAjmlpcListMain").dialog('open').dialog(
							'refresh',
							getContextPath() + "/catalog/ajmlList.do?pcdm="+row.dm+"&action=maintain");
				
			
		},


		catalogbrowse: function (index) {
						var row=$("#dgmlpclistdiv").datagrid("getData").rows[index];
			var dlgmlpcInfodiv = $("<div id='dlgAjmlpcListMain'></div>");
			dlgmlpcInfodiv.appendTo("body");
			$("#dlgAjmlpcListMain").dialog ({
				width: 1024,
				height: 600,
				inline: true,
				modal: true,
				onClose:function(){
					dlgmlpcInfodiv.remove();
					$("#dgmlpclistdiv").datagrid("reload");
				}
			});
			$("#dlgAjmlpcListMain").dialog('open').dialog(
							'refresh',
							getContextPath() + "/catalog/ajmlList.do?pcdm="+row.dm+"&action=browse");
		},

		sos: function (index) {
			$.messager.confirm("提示","您确定要执行此操作吗?",function (sure) {
				if (sure) {
					var row=$("#dgmlpclistdiv").datagrid("getData").rows[index];
					$.ajax({
						url: getContextPath() + "/catalog/refirmSOS.do",
						cache: false,
						async: true,
						type: "post",
						data: {"dm":row.dm,"tybz":row.tybz},
						traditional: true,
						dataType: "json",
						success: function (result) {
							$("#dgmlpclistdiv").datagrid("reload");
							$("#dgmlpclistdiv").datagrid({
								onLoadSuccess : function(){
									$("#dgmlpclistdiv").datagrid("selectRow", index);
									$(".editBtn").linkbutton ({
										iconCls: "icon-edit",
										iconAlign: "left",
										plain: true,
										height: 24,
									});

									$(".sosBtn").linkbutton ({
										iconCls: "icon-reload",
										iconAlign: "left",
										plain: true,
										height: 24,
									});

									$(".catalogBtn").linkbutton ({
										iconCls: "icon-reload",
										iconAlign: "left",
										plain: true,
										height: 24,
									});
								}
							});
						},
						error:function(data){
							$.messager.show ({
								title: "Sorry",
								msg: "系统出错了,请您重新操作!",
								showType: "fade",
								timeout: 2000,
							}); 
						}
					});
				}
			});
		},
	};

	//定义最外边的dialog
	$("#dlgmlpclistdiv").dialog({
		title: "案卷目录批次列表",
		width: 1024,
		height: 600,
		inline: true,
		modal: true,
		closed: false,
		cache: false,
// 		top: 5,
// 		left: 5,
	});

	//定义datagrid(同时将toolbar注入)
	$("#dgmlpclistdiv").datagrid ({
		url: getContextPath() + "/catalog/mlpcList.do",
		width: "100%",
		height: "100%",
		striped: true,
		nowrap: true,
		loadMsg: "数据正在努力加载中,请稍后....",
		pagination: true,
		pagePosition: "bottom",
		pageSize: 15,
		pageList: [15],
		singleSelect: true,	
		rownumbers: true,
		toolbar: "#tlbmlpclistdiv",
		columns: [[
			{
				field: "dm",
				title: "批次代码",
				width: "12%",
				resizable: false,
				align: "center",
			},
			{
				field: "qynd",
				title: "启用年份",
				width: "8%",
				resizable: false,
				align: "center",
			},
			{
				field: "lx",
				title: "案卷类型",
				width: "16%",
				resizable: false,
				align: "center",
			},
			{
				field: "pcmc",
				title: "批次名称",
				width: "22%",
				resizable: false,
				align: "center",
				formatter: function(value,row,index){
					var abValue = (value == null) ? "" : value;
					var content = '<span title="' + value
							+ '" class="note">' + abValue
							+ '</span>';
					return content;
				}
			},
			{
				field: "tybz",
				title: "停用标志",
				width: "8%",
				resizable: false,
				align: "center",
				formatter: function (value,row,index) {
					if (value == 1) {
						return "停用";
					} else {
						return "启用";
					}
				}
			},
			{
				field: "remark",
				title: "备注",
				width: "8%",
				align: "center",
				resizable: false,
			},
			{
				field: "option",
				title: "操作",
				width: "24%",
				align: "center",
				formatter: function (value,row,index) {
					if (row.tybz == 0) {
						if (row.status == 1) {
							return "<a href='#' class='editBtn' onclick='mlpcList.edit("+index+")'>编辑</a>&nbsp;&nbsp;<a href='#' class='sosBtn' onclick='mlpcList.sos("+index+")'>停用</a>&nbsp;&nbsp;<a href='#' class='catalogBtn' onclick='mlpcList.catalogmaintain("+index+")'>目录维护</a>";
						} else {
							return "<a href='#' class='editBtn' onclick='mlpcList.edit("+index+")'>编辑</a>&nbsp;&nbsp;<a href='#' class='sosBtn' onclick='mlpcList.sos("+index+")'>停用</a>&nbsp;&nbsp;<a href='#' class='catalogBtn' onclick='mlpcList.catalogbrowse("+index+")'>目录浏览</a>";
						}
					} else {
						if (row.status == 1) {
							return "<a href='#' class='editBtn' onclick='mlpcList.edit("+index+")'>编辑</a>&nbsp;&nbsp;<a href='#' class='sosBtn' onclick='mlpcList.sos("+index+")'>启用</a>&nbsp;&nbsp;<a href='#' class='catalogBtn' onclick='mlpcList.catalogmaintain("+index+")'>目录维护</a>";
						} else {
							return "<a href='#' class='editBtn' onclick='mlpcList.edit("+index+")'>编辑</a>&nbsp;&nbsp;<a href='#' class='sosBtn' onclick='mlpcList.sos("+index+")'>启用</a>&nbsp;&nbsp;<a href='#' class='catalogBtn' onclick='mlpcList.catalogbrowse("+index+")'>目录浏览</a>";
						}
					} 
				},
				resizable: false,
			},
		]],
		onLoadSuccess: function () {
			$(".editBtn").linkbutton ({
				iconCls: "icon-edit",
				iconAlign: "left",
				plain: true,
				height: 24,
			});

			$(".sosBtn").linkbutton ({
				iconCls: "icon-reload",
				iconAlign: "left",
				plain: true,
				height: 24,
			});

			$(".catalogBtn").linkbutton ({
				iconCls: "icon-reload",
				iconAlign: "left",
				plain: true,
				height: 24,
			});
		},
	});

	$("#addBtn").linkbutton ({
		iconCls: "icon-add",
		iconAlign: "left",
		plain: true,
	});
</script>
  <div id="dlgmlpclistdiv">
  	<div id="tlbmlpclistdiv">
  	  <a href="#" id="addBtn" onclick="mlpcList.add()">新增</a>
  	</div>
  	<div id="dgmlpclistdiv"></div>
  </div>
</body>
</html>
