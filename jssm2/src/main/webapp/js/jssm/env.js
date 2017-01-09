$(function(){

	$("#envListMain").dialog({
		title: "环境参数列表",
		width: 1024,
		height: 600,
		closed: false,
		shadow: true,	//是否显示阴影
		modal: true,	//是否窗口化
		cache: false,	//是否缓存
		inline: true,	//设置是否在父容器中显示，不在会出现很多问题
		resizable: false	//是否可改变大小
	});

	$("#envList").datagrid({
		width: "100%",
		height: "100%",
		method: "post",
		
		url: getContextPath()+"/sys/envList.do",
		
		onLoadSuccess: function(data){
			$(".editBtn").linkbutton({plain:true,height:23, iconCls:'icon-edit'});
			if(data.total==0){
				$('#tbPositionList').datagrid('appendRow',{
					id: '找不到数据',
					envKey: "找不到数据",
					envValue: "找不到数据",
				});
			}
		},
		
		pagination: true,	//是否显示分页工具栏
		pagePosition: "bottom",	//设置分页工具的位置
		pageSize: 15,		//一页记录数
		pageList: [15,20,30],	//页面大小选择列表
		striped:true,			//斑马线样式,
		nowrap : true,		//是否在一行显示所有，自动换行
		loadMsg:"努力加载中，请稍后。。。。",//加载信息
		rownumbers: true,		//显示行号
		singleSelect: true,		//只允许选择一行
		showHeader : true,//显示行头，默认true;
		emptyMsg: "no records found",
		
		
		columns: [[
		      {
		    	  field: "id",
		    	  title: "编号",
		    	  width: "15%",
		    	  align: "center",
		    	  resizable: false
		      },
		      {
		    	  field: "envKey",
		    	  title: "参数名",
		    	  width: "18%",
		    	  align: "center",
		    	  resizable: false
		      },
		      {
		    	  field: "envValue",
		    	  title: "参数值",
		    	  width: "50%",
		    	  align: "center",
		    	  resizable: false
		      },
		      {
		    	  field: "opt1",
		    	  title: "操作",
		    	  width: "15%",
		    	  formatter: function(value,row,index){
		    		  if(value=="找不到数据"){
		    			  return "";
		    		  }
		    		  return "<a href='#' class='editBtn' onclick='edit("+row.id+","+index+");'>编辑</a>";
		    	  },
		    	  align: "center",
		    	  resizable: true
		      }
		 ]], 
	});
});

function edit(envId,index){
	envInfoDiv=$("<div id=\"envInfoDiv\"></div>");
	envInfoDiv.appendTo("body");
	$("#envInfoDiv").dialog({
		title: "修改环境参数",
		width: 512,
		height: 300,
		href: getContextPath()+"/sys/showUpdatePage.do?envId="+envId,
		closed: false,
		modal: true,
		onClose: function(){
			$("#envList").datagrid("reload");
			$("#envList").datagrid("selectRow",index);
			envInfoDiv.remove();
		} 
	});
};