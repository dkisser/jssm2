$(function(){
	objRole = {
			
			//里面的方法需要全部修改！ 
			add : function(){
				
				//解决样式丢失
				var divlgRolelistInfo = $("<div id=\"dlgRolelistInfo\"></div>");
				divlgRolelistInfo.appendTo("body");
				$("#dlgRolelistInfo").dialog({
					title: '新增角色信息', 	
				    width: 512,    
				    height: 300,    
				    closed: false,
				    modal: true,
				    onClose:function(){
				    	divlgRolelistInfo.remove();
				    }
				});
				$("#dlgRolelistInfo").dialog('open').dialog('refresh', getContextPath() + "/user/roleInfo.do?action=add");
			},
			
			
			edit : function(role_id){
				var divlgRolelistInfo = $ ("<div id=\"dlgRolelistInfo\"></div>");
				divlgRolelistInfo.appendTo("body");
				$("#dlgRolelistInfo").dialog({
					title: '修改角色信息', 	
				    width: 512,    
				    height: 300,  
				    href: getContextPath() + "/user/roleInfo.do?action=update&role_id=" + role_id,
				    closed: false,
				    modal: true,
				    onClose:function(){
				    	divlgRolelistInfo.remove();
				    },
				});
				//$("#dlgRolelistInfo").dialog('open').dialog('refresh', "/admin/user/roleInfo.do?action=update&role_id=" + role_id);
			},
		
			
		};
	$("#dlgRoleMain").dialog({
		title: '角色信息列表',    
	    width: 1024,    
	    height: 600,    
	    closed: false,
	    cache: false,    
	    shadow:true,		//显示阴影
	    resizable:false,	//不允许改变大小
	    modal: true,   	//是否窗口化
	    inline:true,			//是否在父容器中，若不在会出现很多BUG
	});
	
	
	//数据表格
	$("#tblRoleDateGrid").datagrid({
		width:"100%",
		height:"100%",
		method:"get",
		
		url:getContextPath() + "/user/rolelist.do",
		
		onLoadSuccess:function(data){
			if(data.total==0){
				$('#tblRoleDateGrid').datagrid('appendRow',{
					name: '',
					uname:'',
					email:'找不到数据',
					position:'',
					type:'',
					opt1:'',
				});
			}
		},

		
		//表单项
		columns : [[
			{	
				width:"25%",
				field:"role_name",
				title:"角色名",
				halign:"center",
				align:"center",
				resizable:false,
			},
			{	
				width:"15%",
				field:"status",
				title:"状态",
				halign:"center",
				align:"center",
				resizable:false,
				formatter:function(value){
	            	 if(value=="1"){
						 return "激活";
					 }
	            	 else return "未激活";
	            },
			},
			{	
				width:"48%",
				field:"priv_list",
				title:"权限",
				halign:"center",
				align:"left",
				resizable:false,
				
				//将长字符串做一个省略
				formatter:function(value){
					if(value.length>40){
						return value.substr(0,40) + "...";
					}
					else return value;
				},
			},
			 {
				width:"10%",
				field:"opt1",
				title:"操作",
				align:"center",  
	            formatter:function(value,row,index){
	            	 if(row.email=="找不到数据"){
						 return "";
					 }
	            	return '<a href="#" onclick="objRole.edit('+row.role_id+')"><img src="js/easyui/themes/icons/pencil.png" />修改</a>';
	            },
	            resizable:false,
	        } , 
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
		
		toolbar:"#divRoleList",				//类选择器引入
		
		emptyMsg: "no records found",
		
	});
});