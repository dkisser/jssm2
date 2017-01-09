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
    <title>案卷一级分类</title>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
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
  
  
  objYjfl={
  	add:function(){
  		var divYjflListInfo = $("<div id=\"dlgYjflListInfo\"></div>");
				divYjflListInfo.appendTo("body");
				$("#dlgYjflListInfo").dialog({
					title: '新建案卷一级分类', 	
				    width: 512,    
				    height: 300,    
				    closed: false,
				    modal: true,
				    onClose:function(){
				    	divYjflListInfo.remove();
				    	$("#tblYjflListDataGrid").datagrid("reload");
				    }
				});
				$("#dlgYjflListInfo").dialog('open').dialog('refresh', getContextPath() + "/catalog/yjflInfo.do?action=add");
  	},
  	update:function(index){
  		var divYjflListInfo = $("<div id=\"dlgYjflListInfo\"></div>");
   		var row = $('#tblYjflListDataGrid').datagrid('getData').rows[index];
				divYjflListInfo.appendTo("body");
				$("#dlgYjflListInfo").dialog({
					title: '编辑案卷一级分类', 	
				    width: 512,    
				    height: 300,    
				    closed: false,
				    modal: true,
				    onClose:function(){
				    	divYjflListInfo.remove();
				    	$("#tblYjflListDataGrid").datagrid("reload");
				    }
				});
				$("#dlgYjflListInfo").dialog('open').dialog('refresh', getContextPath() + "/catalog/yjflInfo.do?action=update&dm="+row.dm);
  	},
  	del:function(index){
  		var row = $('#tblYjflListDataGrid').datagrid('getData').rows[index];
  		$.messager.confirm('确认','您确认想要删除此记录吗？',function(sure){    
				    if (sure){    
				    	var url = getContextPath() + "/catalog/yjflInfo.do?action=delete&dm="+row.dm+"&";
						$('#center').dialog('refresh',url);
				    }    
				}); 
  		
  	}
  };
  
  	$("#dlgYjflList").dialog({
  		title: '案卷一级分类列表',    
	    width: 1024,    
	    height: 600,    
	    closed: false,
	    cache: false,    
	    shadow:true,		//显示阴影
	    resizable:false,	//不允许改变大小
	    modal: true,   	//是否窗口化
	    inline:true,			//是否在父容器中，若不在会出现很多BUG
  	});
  	$("#tblYjflListDataGrid").datagrid({
  		width:"100%",
		height:"100%",
		method:"post",
		url:getContextPath()+"/catalog/yjflList.do",
	
		toolbar:[{
			iconCls:'icon-add',
			text:'新增',
			height:40,
			handler:function(){
				objYjfl.add();
			},
			
		}],
		onLoadSuccess:function(data){
			$(".editBtn").linkbutton({plain:true,height:23, iconCls:'icon-edit'});
			$(".deleteBtn").linkbutton({plain:true,height:23, iconCls:'icon-no'});
		},
		
		
		//表单项
		columns : [[
			{	
				width:"20%",
				field:"dm",
				title:"分类代码",
				halign:"center",
				align:"center",
				resizable:false,
			},
			{	
				width:"20%",
				field:"mc",
				title:"分类名称",
				halign:"center",
				align:"center",
				resizable:false,
			},
			{	
				width:"20%",
				field:"pym",
				title:"拼音码",
				halign:"center",
				align:"center", 
				resizable:false,

			},
			 {
				width:"10%",
				field:"tybz",
				title:"状态",
				align:"center",  
				resizable:false,
				formatter:function(value,row,index){
	            	 if(value=="0"){
						 return "启用";
					 }
	            	 else return "停用";
	            },
	      	},{
	      		width:"28%",
				field:"method",
				title:"操作",
				align:"center",  
				formatter:function(value,row,index){
					if(row.dm == " "){
						return " ";
					}
						return '<a href = "##" class="editBtn" onclick = "objYjfl.update('+index+')">编辑</a>&nbsp;&nbsp;<a href = "##" class="deleteBtn" onclick = "objYjfl.del('+index+')">删除</a>'
		         	},
	            resizable:false,
	      	}
	      	
		]],
		
		
		pagination:true,					//是否有分页工具
		pagePosition:"bottom",			//分页工具位置
		pageSize:15,					//分页默认大小
		pageList:[15],
		striped:true,			//斑马线样式,
		nowrap : true,		//是否在一行显示所有，自动换行
		loadMsg:"努力加载中，请稍后。。。。",//加载信息
		rownumbers:true,	//是否显示行号
		singleSelect:true,	//只能同时选择一行
		showHeader : true,//显示行头，默认true;
		emptyMsg: "no records found",
		
  	});
  </script>
<div id="dlgYjflList">
	<!--  <div>内容测试内容测试内容测试内容测试</div>-->
	<table id ="tblYjflListDataGrid"></table>
	</div>
</div>
  </body>
</html>

