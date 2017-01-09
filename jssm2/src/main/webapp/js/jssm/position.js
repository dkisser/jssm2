$(function(){
	objPosition={	
		add:function(){
			dlgPositionDiv=$("<div id=\"dlgPositionDiv\"></div>");
			dlgPositionDiv.appendTo("body");
			$("#dlgPositionDiv").dialog({
				title:"添加职位信息",
				width: 512,
				height: 300,
				closed:false,
				modal:true,
				onClose: function(){
					dlgPositionDiv.remove();
				},
			});
			$("#dlgPositionDiv").dialog("open").dialog("refresh",getContextPath()+"/position/addpositionInfo.do");
		},
		edit:function(pid,index){
			dlgPositionDiv=$("<div id=\"dlgPositionDiv\"></div>");
			dlgPositionDiv.appendTo("body");
			$("#dlgPositionDiv").dialog({
				title: "修改职位信息",
				width: 512,
				height: 300,
				closed: false,
				modal: true,
				href: getContextPath()+"/position/updatepositionInfo.do?&pid="+pid,
				onClose: function(){
					$("#tbPositionList").datagrid("reload");
					$("#tbPositionList").datagrid("selectRow",index);
					dlgPositionDiv.remove();
				}
			});
		},
		dele:function(pid){
			$.messager.confirm("提示","确认删除本条记录？",function(r){
				if(r){
					var url=getContextPath()+"/position/deletepositionInfo.do?&pid="+pid;
					$("#center").panel("refresh",url);
				}
			});
		}
	};
	
	$("#SearchPositionList").searchbox({ 
		searcher:function(value,name){ 
//			if(value == null || value ==''){
//				$.messager.alert('警告','请输入职位名称');
//				return false;
//			}
			$("#tbPositionList").datagrid('load',{
				pname:value
			});
			}, 
			prompt:'请输入职位名称' 
			}); 

	
	$("#dlgPositionMain").dialog({
		title:"职位列表",
		width:1024,
		height:600,
		closed:false,
		cache:false,
		shadow:true,	//显示阴影
		resizable:false,	//是否可改变大小
		modal:true,
		inline:true		//是否在父容器中显示
	});
	
	//将对话框移到本页面的左上角
	$("#dlgPositionMain").dialog("move",{
		left:10,
		top:10,
	});
	
	$("#tbPositionList").datagrid({
		width:"100%",
		height:"100%",
		method:"post",
		
		url:getContextPath()+"/position/positionList.do",
		
		onLoadSuccess:function(data){
			$(".editBtn").linkbutton({plain:true,height:23, iconCls:'icon-edit'});
			$(".deleteBtn").linkbutton({plain:true,height:23, iconCls:'icon-no'});
			if(data.total==0){
				$('#tbPositionList').datagrid('appendRow',{
					id:'找不到数据',
					name:"找不到数据"
				});
			}
		},
		
		columns:[[
		         {
		        	width:"40%",
		        	field:"id",
		        	title:"职位编号",
		        	halign:"center",
		        	align:"center",
		        	resizable:false
		         },
		         {
		        	width:"40%",
		        	field:"name",
		        	title:"职位名称",
		        	halign:"center",
		        	align:"center",
		        	resizable:false
		         },
		         {
					width:"18%",
					field:"opt1",
					title:"操作",
					align:"center",  
			        formatter:function(value,row,index){
			            if(row.id=="找不到数据"){
							return "";
						}
				            return '<a href="#" class="editBtn" onclick="objPosition.edit('+row.id+',' +index +')">编辑</a>&nbsp&nbsp<a href="#" class="deleteBtn" onclick="objPosition.dele('+row.id+',' +index +')">删除</a>';
			         },
			         resizable:false
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
		
		toolbar:"#divPositiontb",				//类选择器引入
		
		emptyMsg: "no records found",
		
		
	});
	
	
	
});