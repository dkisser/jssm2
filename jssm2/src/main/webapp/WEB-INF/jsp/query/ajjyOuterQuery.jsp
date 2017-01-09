<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
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
		var isRegisted = false;
		$.ajax({
			async : false,
			cache : false,
			dataType : 'JSON',
			type : 'GET',
			url : getContextPath() + '/query/checkIsRegister.do',
			success : function(result) {
				if (result == 1) {
					isRegisted = true;
				}
			}
		});

		if (!isRegisted) {
			$.messager.alert('提示', '暂无卷宗可浏览,请到登记处登记！');
		} else {

			$('#AjjyQuterQuerydialog').dialog({
				title : '案卷对外借阅',
				width : 1024,
				height : 600,
				closable : false,
				closed : false,
				cache : false,
				inline : true,
				modal : true
			});

			$('#AjjyQuterQuerydatagrid')
					.datagrid(
							{
								width : '100%',
								height : 500,
								striped : true,
								url : getContextPath()
										+ '/query/findAllDatas.do',
								loadMsg : '努力加载中，请稍后...',
								pagination : true,
								rownumbers : true,
								singleSelect : true,
								pageNumber : 1,
								pageSize : 15,
								pageList : [ 15, 20, 30 ],
								columns : [ [
										{
											field : 'ah',
											title : '案号',
											width : '20%',
											align : 'center',
											resizable : false,
										},
										{
											field : 'jzlx',
											title : '卷宗类型',
											width : '10%',
											align : 'center',
											resizable : false,
										},
										{
											field : 'jh',
											title : '卷号',
											width : '10%',
											align : 'center',
											resizable : false,
										},
										{
											field : 'aqjb',
											title : '安全级别',
											width : '10%',
											align : 'center',
											resizable : false,
										},
										{
											field : 'status',
											title : '归档状态',
											width : '10%',
											align : 'center',
											resizable : false,
											formatter : function(value, row,
													index) {
												if (value == 1) {
													return "已归档";
												} else if (value == 0) {
													return "未归档";
												}
											}
										},
										{
											field : 'gdrq',
											title : '归档日期',
											width : '15%',
											align : 'center',
											resizable : false,
											formatter : function(value, row,
													index) {
												var date = new Date(value);
												return date.getFullYear() + '-'
														+ date.getMonth() + '-'
														+ date.getDate();
											}
										},
										{
											field : 'gdhs',
											title : '归档号数',
											width : '10%',
											align : 'center',
											resizable : false,
										},
										{
											field : 'field',
											title : '操作',
											width : '10%',
											align : 'center',
											resizable : false,
											formatter : function(value, row,
													index) {
												return '<a class="searchDatas" onclick="showDatas('
														+ index
														+ ')" href="#">浏览</a>';
											}
										} ] ],
								onLoadSuccess : function(data) {
									$('.searchDatas').linkbutton({
										iconCls : 'icon-search',
										plain : true,
										height : 20,
									});
								}

							});

			$('#closedBtn')
					.linkbutton(
							{
								iconCls : 'icon-cancel',
								text:'关闭',
								onClick : function() {
									$.messager
											.confirm(
													'确认关闭',
													'是否退出卷宗浏览页面？退出后如需再次浏览卷宗，需重新到档案室进行登记',
													function(r) {
														if (r) {
															$
																	.ajax({
																		async : false,
																		cache : false,
																		dataType : 'JSON',
																		type : 'GET',
																		url : getContextPath()
																				+ '/query/updateStatus.do',
																		success : function(
																				result) {
																			if (result >= 1) {
																				$(
																						'#AjjyQuterQuerydialog')
																						.dialog(
																								'close');
																			} else {
																				$.messager
																						.alert(
																								'警告',
																								'错误消息,请与管理员联系！');
																			}
																		}
																	});
														}
													});
								}
							});

			function showDatas(index) {
				var datagridData = $("#AjjyQuterQuerydatagrid").datagrid(
						'getData').rows[index];
				var ah = datagridData.ah;
				var jzewm = datagridData.jzewm;
				var jzBrowseDia = $("<div id=\"ajjyOuterBrowseDia\"></div>");
				jzBrowseDia.appendTo("body");
				$("#ajjyOuterBrowseDia").dialog(
						{
							width : 1024,
							title : ah,
							href : getContextPath()
									+ "/ajuan/jzBrowse.do?hasdlg=no&jzEwm="
									+ jzewm + "&print=" + '${print}',
							height : 600,
							closed : false,
							cache : false,
							shadow : true,
							resizable : false,
							modal : true,
							inline : true,
							onClose : function() {
								jzBrowseDia.remove();
							},
						});
			}
		}
	</script>
		<div id="AjjyQuterQuerydialog">
		<div id="AjjyQuterQuerydatagrid"></div>
		<table>
			<tr>
				<td width="450" height="50"></td>
				<td width="100" height="50"><a id="closedBtn" href="#"></a></td>
				<td width="450" height="50"></td>
			</tr>
		</table>
	</div>
</body>
</html>
