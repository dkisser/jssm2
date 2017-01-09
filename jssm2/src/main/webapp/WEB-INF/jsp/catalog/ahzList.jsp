<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>案号字列表</title>
    
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
  </head>
  
  <body>
   	<div id="dialogDiv">
   		<div id="toolbar" style="padding: 5px;"><a id="addBtn" href="javascript:void(0);" onclick="objAhz.add();" style="color:#6666FF">新增</a></div>
   		<table id="ahzListTab"></table>
   	</div>
   	<script type="text/javascript">
   		$(function(){
   			$("#dialogDiv").dialog({
   				width: 1024,
   				height: 600,
   				title: "案号字列表",
   				inline: true,	//在父容器中显示，否则会有许多bug
   				madal: true,	//模式化显示窗口
   				shadow: true,	//在窗体显示的时候显示阴影
   				resizable: false,	//不可改变大小
   				cache: false,		//不缓存
   				onClose: function(){
   					$("dialogDiv").dialog("close");
   				},
   			});
   			
			
			$("#addBtn").linkbutton({
				plain:true,
				height:23, 
				iconCls:'icon-add'
			});
			
			objAhz={
			  add:function(){
					var divAhzInfo=$("<div id=\"divAhzInfo\"></div>");
					divAhzInfo.appendTo("body");
					$("#divAhzInfo").dialog({
						title: "新建案号字",
						width: 512,
						height: 300,
						closed: false,
						modal: true,
						href: getContextPath() +"/catalog/AhzInfo.do?action=add",
						onClose: function(){
							divAhzInfo.remove;
						}
					});
				},
				edit:function(index){
					var divAhzInfo=$("<div id=\"divAhzInfo\"></div>");
					divAhzInfo.appendTo("body");
					$("#divAhzInfo").dialog({
						title: "编辑案号字",
						width: 512,
						height: 300,
						closed: false,
						href: getContextPath()+"/catalog/AhzInfo.do?action=update",
						modal: true,
						onClose: function(){
							$("#ahzListTab").datagrid('reload');
							divAhzInfo.remove;
							$("#ahzListTab").datagrid('selectRow',index);
						}
					});
				}
			}
   			
   			$("#ahzListTab").datagrid({
   				width: "100%",
   				height: "100%",
   				method: "post",
   				
   				url: getContextPath()+"/catalog/getAhzList.do",
   				
   				onLoadSuccess: function(data){
   					$(".editBtn").linkbutton({plain:true,height:23, iconCls:'icon-edit'});
   					if(data.total==0){
   						$("#ahzListTab").datagrid("appendRow",{
   							dm:   "找不到数据",
   							ahz:  "找不到数据",
   							yjfl: "找不到数据",
   							cbt:  "找不到数据",
   							pym:  "找不到数据",
   							tybz: "找不到数据",
   						});
   					}
   				},
   				
   				columns:[[
		         {
		        	width:"15%",
		        	field:"dm",
		        	title:"代码",
		        	halign:"center",
		        	align:"center",
		        	resizable:false
		         },
		         {
		        	width:"20%",
		        	field:"ahz",
		        	title:"案号字",
		        	halign:"center",
		        	align:"center",
		        	resizable:false
		         },
		          {
		        	width:"12%",
		        	field:"yjflms",
		        	title:"一级分类",
		        	halign:"center",
		        	align:"center",
		        	resizable:false
		         },
		          {
		        	width:"20%",
		        	field:"cbtsms",
		        	title:"承办庭",
		        	halign:"center",
		        	align:"center",
		        	resizable:false
		         },
		          {
		        	width:"10%",
		        	field:"pym",
		        	title:"拼音码",
		        	halign:"center",
		        	align:"center",
		        	resizable:false
		         },
		          {
		        	width:"10%",
		        	field:"tybz",
		        	title:"停用标志",
		        	halign:"center",
		        	align:"center",
		        	resizable:false,
		        	formatter: function(value){
						if (value==0){
							return "启用";
						} else {
							return "停用";
						}
		        	}
		         },
		         {
					width:"10%",
					field:"opt1",
					title:"操作",
					align:"center",  
			        formatter:function(value,row,index){
			            if(row.id=="找不到数据"){
							return "";
						}
				            return '<a href="#" class="editBtn" onclick="objAhz.edit('+row.dm+',' +index +')">编辑</a>';
			         },
			         resizable:false,
			       }, 
		]],
   			
		   		pagination:true,					//是否有分页工具
				pagePosition:"bottom",			//分页工具位置
				pageSize:15,						//分页默认大小
				pageList:[15],
				striped:true,			//斑马线样式,
				nowrap : true,		//是否在一行显示所有，自动换行
				loadMsg:"努力加载中，请稍后。。。。",//加载信息
				rownumbers:true,	//是否显示行号
				singleSelect:true,	//只能同时选择一行
				showHeader : true,//显示行头，默认true;
				
				toolbar:"#toolbar",				//类选择器引入
				
				emptyMsg: "no records found",
				
			
   			});
   		});
   	</script>
  </body>
</html>
