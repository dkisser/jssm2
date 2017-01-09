<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<div id="dlguserListdiv" >
		<div id="tlbuserListdiv"  style="padding:5px;">
		  <a href="#" onclick="userList.add()" class="easyui-linkbutton" plain="true"
						iconCls="icon-add" style="color:#6666FF">新增</a>
		</div>
		<table id="dguserListtb"> </table>	
	</div>
<script type="text/javascript">
$(function () {
	userList= {
		dele: function (index) {
			$.messager.confirm ("提示","您确定要删除该条记录吗?",function (sure) {
				var row = $("#dguserListtb").datagrid("getData").rows[index];
				var uname = row.uname;
				if (sure) {
					//发送删除的请求
					var url = getContextPath() + "/user/deleteUser.do?&uname=" +uname;
					$("#center").panel("refresh",url);
				}
			});
			
			
		},
		
		edit: function (  index ) {
			var dlguserInfo = $("<div id='dlguserInfo'></div>");
			dlguserInfo.appendTo("body");
			var row = $("#dguserListtb").datagrid("getData").rows[index];
			$("#dlguserInfo").dialog({
				href:getContextPath() + "/user/toEditUserInfo.do?uname=" + row.uname,//从外面加载一个jsp到这个容器(dialog)中
				title: "编辑用户",
				width: 512,
				height: 300,
				modal: true,
				draggable: false,
				inline: true,
				 onClose:function(){
						$("#dguserListtb").datagrid("reload");
						$("#dguserListtb").datagrid("selectRow", index);
						dlguserInfo.remove();
					   },
			});
		},
		
		add: function () {
			var dlguserInfo = $("<div id='dlguserInfo'></div>");
			dlguserInfo.appendTo("body");
			$("#dlguserInfo").dialog({
				href:getContextPath() + "/user/toAddUserInfo.do",//从外面加载一个jsp到这个容器(dialog)中
				title: "新增用户",
				width: 512,
				height: 300,
				modal: true,
				draggable: false,
				inline: true,
				
			});
		
		},
		
		reset: function ( index ) {
			$.messager.confirm ("提示","您确定将密码重置为123456吗?",function (sure) {
					var row = $("#dguserListtb").datagrid("getData").rows[index];
					var uname = row.uname;
				if (sure) {
					//执行重置的操作
					var url = getContextPath() + "/user/resetPassword.do?uname=" +uname;
					$("#center").panel("refresh",url);
				}
			});
			
		},
			
	};
	
	//定义userList要显示的位子的dialog的属性
	$("#dlguserListdiv").dialog ({
		title: "用户列表",
		width: 1024,
		height: 600,
		inline: true,
		cache : false,
		shadow : true,
		closed : false,
		modal : true,
	});
	
	
	//定义dialog中的表的属性
	$("#dguserListtb").datagrid({
		url:getContextPath() + "/user/userList.do" ,
		striped: true,//斑马线效果
		loadMsg: "数据正在加载当中，请稍等...",//加载数据时的提示消息
		pagination: true,//分页工具栏
		rownumbers: true,//显示行列号
		singleSelect: true,//只允许选中一行
		pagePosition: "bottom",//分页工据显示的位子的设置
		pageSize: 15,//设置分页时的初始化的分页大小
		pageList: [15],//选择分页显示行数的列表里面的值
		width:"100%",
		height:"100%",
		toolbar:'#tlbuserListdiv',
		nowrap : true, //是否在一行显示所有，自动换行
		columns: [[
		           	{
		           		field: "name",
		           		title: "用户姓名",
		           		width: "12%",
		           		align: "center",
		           		halign: "center",
		           		resizable:false,
		           	},
		           	{
		           		field: "uname",
		           		title: "登录名",
		           		width: "14%",
		           		align: "center",
		           		halign: "center",
		           		resizable:false,
		           	},
		           	{
		           		field: "jg",
		           		title: "部门",
		           		width: "16%",
		           		align: "center",
		           		halign: "center",
		           		resizable:false,
		           	},
		           	{
		           		field: "zw",
		           		title: "职位",
		           		width: "16%",
		           		align: "center",
		           		halign: "center",
		           		resizable:false,
		           	},
		           	{
		           		field: "role",
		           		title: "现有角色",
		           		width: "16%",
		           		align: "center",
		           		halign: "center",
		           		resizable:false,
		           	},
		           	{
		           		field: "option",
		           		title: "操作",
		           		width: "24%",
		           		align: "center",
		           		resizable:false,
		           		formatter: function (value , row , index) {
		           			return "<a href='#' class='editBtn' onclick='userList.edit("+index+")'>编辑</a>&nbsp;&nbsp;<a href='#' class='deleBtn' onclick='userList.dele("+index+")'>删除</a>&nbsp;&nbsp;<a href='#' class='resetBtn' onclick='userList.reset("+index+")'>重置</a>";
		           		}
		           	},
		         ]],
		         onLoadSuccess : function(data) {

		        		$(".editBtn").linkbutton({
		        			iconCls: "icon-edit",
		        			plain: true,
							height : 24,//这里一定要设置行高，必须是24，要不然和分页左边的不对齐
		        		});
		        		
		        		$(".deleBtn").linkbutton({
		        			iconCls: "icon-no",
							height : 24,
		        			plain: true,
		        		});
		        		
		        		$(".resetBtn").linkbutton({
		        			iconCls: "icon-reload",
							height : 24,
		        			plain: true,
		        		});
		 			},
	});

});
	

</script>
</body>
</html>
