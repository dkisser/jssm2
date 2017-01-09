<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>案件信息列表</title>
    
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
  <div id="dlgAjxxInfo">
		<table id="ajxxList"></table>
	</div>
	<script type="text/javascript">
	var currJzType = "${currJzType}";
	var obj = {
		detail:function(index){
		var ajxx = $('#ajxxList').datagrid('getData').rows[index];
		var divAjxxDetail = $("<div id=\"dlgAjxxDetail\"></div>");
					divAjxxDetail.appendTo("body");
			$("#dlgAjxxDetail").dialog({
				width:1024,
				height:600,
				closed:false,
				cache:false,
				shadow:true,	//显示阴影
				resizable:false,	//是否可改变大小
				modal:true,
				inline:true,	//是否在父容器中显示
				onClose: function(){
					divAjxxDetail.remove();
				},
			});
			$("#dlgAjxxDetail").dialog('open').dialog('refresh', getContextPath()
										+ "/ajuan/ajxxInfo.do?action=browse&ah="+ajxx.ah);
		},
		build:function(index){
			$.messager.progress();	// 显示进度条
			var ajxx = $('#ajxxList').datagrid('getData').rows[index];
			$.ajax({
				url:getContextPath() + "/ajuan/checkAjxx.do",
				async:true,		//是否异步请求
				cache:false,	//是否缓存
				type:"post",	//请求方式
				data:{ah:ajxx.ah},	//向服务器传递参数
				dataType:"json",	//返回数据类型
				success:function(result){
					if(result.success==1){
						if(currJzType == "ls"){
							$('#center').panel('refresh',getContextPath() +  "/ajuan/lsjzWizard_new.do?jzewm=${jzewm}&ah="+ajxx.ah);
						}else if(currJzType == "js"){
							$('#center').panel('refresh',getContextPath() +  "/ajuan/jsjzWizard_new.do?jzewm=${jzewm}&ah="+ajxx.ah);
						}
					}
					$.messager.progress('close');
				},
				error : function(){
					$.messager.progress('close');
				}
			});
		}
	};

	$("#ajxxList").datagrid({
		width:"100%",
		height:"100%",
		method:"post",
		url:getContextPath()+"/ajuan/getAjxxList.do",
		onLoadSuccess:function(data){
			$(".detailBtn").linkbutton({plain:true,height:23, iconCls:'icon-more'});
			$(".buildBtn").linkbutton({plain:true,height:23, iconCls:'icon-edit'});
			if(data.total==0){
				$('#ajxxList').datagrid('appendRow',{
					ah:'找不到数据',
					ajmc:"找不到数据",
					dsr:'找不到数据',
					ayms:'找不到数据'
				});
			};
            $(".note").tooltip({
                    trackMouse:true,
                    //position: "bottom",
                    onShow: function(){
                        $(this).tooltip('tip').css({ 
                            width:'300',
                            boxShadow: '1px 1px 3px #292929'                        
                        });
		
			},
			});
		},
		onSelect:function(){
			$("#jdBtn").linkbutton('enable');
		},
		
		columns:[[
		         {
		        	width:"20%",
		        	field:"ah",
		        	title:"案号",
		        	halign:"center",
		        	align:"center",
		        	resizable:false
		         },
		         {
		        	width:"20%",
		        	field:"ajmc",
		        	title:"案件名称",
		        	halign:"center",
		        	align:"center",
		        	resizable:false,
		        	formatter:function(value, row, index){
							if(value==""||value==null){
								return "";
							}
							 var abValue = value;
		                    if (value.length>=20) {
		                        abValue = value.substring(0,12) + "...";
		                        var content = '<span style="text-decoration:none;" href="#" title="' + value + '" class="note">' + abValue + '</span>';
		                        return content;
		                    }
		                    return value;
		                },
		         },
		         {
		        	 width:"23%",
		        	 field:"dsr",
		        	 title:"当事人",
		        	 halign:"center",
		        	 align:"center",
		        	 resizable:false,
		        	 formatter: function(value, row, index) {
		        	 							if(value==""||value==null){
								return "";
							}
							                    var abValue = value;
							                    if (value.length>=20) {
							                        abValue = value.substring(0,20) + "...";
							                        var content = '<span style="text-decoration:none;" href="#" title="' + value + '" class="note">' + abValue + '</span>';
							                        return content;
							                    }
							                    return value;
							                },
		         },
		         {
		        	 width:"20%",
		        	 field:"ayms",
		        	 title:"案由描述",
		        	 halign:"center",
		        	 align:"center",
		        	 resizable:false,
		        	 formatter: function(value, row, index) {
		        	 							if(value==""||value==null){
								return "";
							}
							                    var abValue = value;
							                    if (value.length>=80) {
							                        abValue = value.substring(0,80) + "...";
							                        var content = '<span style="text-decoration:none;" href="#" title="' + value + '" class="note">' + abValue + '</span>';
							                        return content;
							                    }
							                   return value;
							                },
		         },
		         {
					width:"15%",
					field:"opt1",
					title:"操作",
					align:"center",  
			        formatter:function(value,row,index){
			            if(row.ajmc=="找不到数据"){
							return "";
						}
				            return '<a href="javascript:void(0);" class="detailBtn" onclick="obj.detail(' +index +')">详情</a>&nbsp;<a href="javascript:void(0);" class="buildBtn" onclick="obj.build(' +index +')">建档</a>';
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
		
		//toolbar:"#ListEnd",				//类选择器引入
		emptyMsg: "no records found",
	});
	
	var title = "";
	if(currJzType == "js"){
		title = "新建即时卷宗--案件信息列表";
	}else if(currJzType == "ls"){
		title = "新建历史卷宗--案件信息列表";
	}else if(currJzType == "gd"){
		title = "新建归档卷宗--案件信息列表";
	}
	$("#dlgAjxxInfo").dialog({
		title: title, 	
	    width: 1024,    
	    height: 600,    
	    closed: false,
	    modal: true,
	    inline:true,
	});
	
  	</script>
  </body>
</html>
