$(function(){
	objUser = {
		add : function(){
			
			//解决样式丢失
			var divlgUserlistInfo = $("<div id=\"dlgUserlistInfo\"></div>")
			divlgUserlistInfo.appendTo("body");
			$("#dlgUserlistInfo").dialog({
				title: '新增用户信息', 	
			    width: 512,    
			    height: 300,    
			    closed: false,
			    modal: true,
			    onClose:function(){
			    	divlgUserlistInfo.remove();
			    }
			});
			$("#dlgUserlistInfo").dialog('open').dialog('refresh', getContextPath() + "/user/userInfo.do?action=add");			
		},
		
		reset: function(userid){
			$.messager.confirm('确认','您确认想重置此条记录的密码为123456吗？',function(sure){    
			    if (sure){    
			    	var url = getContextPath() + "/user/resetPwd.do?userid=" + userid + "&";
					$('#center').panel('refresh',url);
			    }    
			});  
		},
		
		
		edit : function(userid,index){
			var divlgUserlistInfo = $("<div id=\"dlgUserlistInfo\"></div>")
			divlgUserlistInfo.appendTo("body");
			$("#dlgUserlistInfo").dialog({
				title: '修改用户信息', 	
			    width: 512,    
			    height: 300,    
			    closed: false,
			    href: getContextPath() + "/user/userInfo.do?action=update&userid=" + userid,
			    modal: true,
			    onClose:function(){
					$('#tblUserList').datagrid('reload');
					$('#tblUserList').datagrid('selectRow', index);
			    	divlgUserlistInfo.remove();
			    },
			});
	    	
		},
	
		dele : function(userid, index){
			$.messager.confirm('确认','您确认想要删除此记录吗？',function(sure){    
			    if (sure){    
			    	var url = getContextPath() + "/user/userInfo.do?action=delete&userid=" + userid + "&";
					$('#center').panel('refresh',url);
			    }    
			});  
		},
		

	};
	
	$("#dlgUserMain").dialog({
		title: '用户列表',    
	    width: 1024,    
	    height: 600,    
	    closed: false,
	    cache: false,    
	    shadow:true,		//显示阴影
	    resizable:false,	//不允许改变大小
	    modal: true,   	//是否窗口化
	    inline:true,			//是否在父容器中，若不在会出现很多BUG
	});
	
	//将Dialog移动至此页面左上角
	$('#dlgUserMain').dialog('move',{    
		  left:10,    
		  top:10,   
	});  
	
	//查询下拉列表
	$('#cbUserList').combobox({    
		url:getContextPath() + '/user/findAllType.do',
		height:'100%',
		valueField: 'id',
		textField: 'text',  
		onLoadSuccess: function () { 
			$('#cbUserList').combobox('select', '全部');
		},
		onSelect:function(record){
			$('#tblUserList').datagrid('gotoPage', 1);
			$("#tblUserList").datagrid({
				url:getContextPath() + "/user/findByType.do?role_id=" + record.id,
			});
		},
	});  
	
	//数据表格
	$("#tblUserList").datagrid({
		width:"100%",
		height:"100%",
		method:"get",
		
		//数据来源：一个包含total和List的JSON对象
		//url:"${pageContext.servletContext.contextPath}/user/userlist.do",这个地方要注意
		url:getContextPath() + "/user/userlist.do",
		
		onLoadSuccess:function(data){
			$(".editBtn").linkbutton({plain:true,height:23, iconCls:'icon-edit'});
			$(".deleteBtn").linkbutton({plain:true,height:23, iconCls:'icon-no'});
			$(".resetBtn").linkbutton({plain:true,height:23, iconCls:'icon-reload'});
			if(data.total==0){
				$('#tblUserList').datagrid('appendRow',{
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
				width:"10%",
				field:"name",
				title:"用户姓名",
				halign:"center",
				align:"center",
				resizable:false,
			},
			{	
				width:"16%",
				field:"uname",
				title:"登录名",
				halign:"center",
				align:"center",
				resizable:false,
			},
			{	
				width:"18%",
				field:"email",
				title:"email",
				halign:"center",
				align:"center",
				resizable:false,
			},
			{	
				width:"16%",
				field:"position",
				title:"职位",
				halign:"center",
				align:"center",
				resizable:false,
			},
			{	
				width:"13%",
				field:"type",
				title:"现有角色",
				halign:"center",
				align:"center",
				resizable:false,
			},
			 {
				width:"25%",
				field:"opt1",
				title:"操作",
				align:"center",  
	            formatter:function(value,row,index){
	            	 if(row.email=="找不到数据"){
						 return "";
					 }
		            	return '<a href="#" class="editBtn" onclick="objUser.edit('+row.userid+',' +index +')">修改</a>&nbsp&nbsp<a href="#" class="deleteBtn" onclick="objUser.dele('+row.userid+',' +index +')">删除</a>&nbsp&nbsp<a href="#" class="resetBtn" onclick="objUser.reset('+row.userid+')">重置</a>';
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
		
		toolbar:"#divUsertb",				//类选择器引入
		
		emptyMsg: "no records found",
		
	});
});