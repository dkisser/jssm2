<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  </head>
  <body>
   <script type="text/javascript">
	var objYhzw={
			edit:function(){
				var divlgRolelistInfo = $ ("<div id=\"dlgRolelistInfo\"></div>");
				divlgRolelistInfo.appendTo("body");
				$("#dlgRolelistInfo").dialog({
					title: '修改信息', 	
				    width: 512,    
				    height: 300,  
				   href: getContextPath() + "/catalog/yhzwAddInfo.do?isNew="+"no",
				    closed: false,
				    modal: true,				   
				    onClose:function(){
				    	divlgRolelistInfo.remove();
				    },
				});
			},
			
			
	add:function(){
				var divlgRolelistInfo = $ ("<div id=\"dlgRolelistInfo\"></div>");
				divlgRolelistInfo.appendTo("body");
				$("#dlgRolelistInfo").dialog({
					title: '新增', 	
				    width: 512,    
				    height: 300,  
				    href: getContextPath() + "/catalog/yhzwAddInfo.do?isNew="+"yes",
				    closed: false,
				   	modal: true,				   
				    onClose:function(){
				    	divlgRolelistInfo.remove();
				    },
				});
			}		
			
	};
  		$(function(){
  			$('#yhzwdg').dialog({    
  				title : '用户维护信息',
  				width: 1024,    
  		 	    height: 600,
  				closed : false,
  				cache : false,
  				shadow : true, //显示阴影
  				resizable : false, //不允许改变大小
  				modal : true, //模式化窗口，锁定父窗口
  				inline : true, //是否在父容器中，若不在会出现很多BUG
  			});
  			
  			$('#yhzwListdg').datagrid({ 
  				width:"100%",
  				height:"100%",
  				method:"get",
  				
  				singleSelect:true,
  				pagination:true,
  				striped:true,
  				rownumbers:true,
  				idField:'dm',
  				singleSelect:true,
  				striped:true,
  				toolbar:'#bar',
  				pageSize:'15',
  				toolbar:'#addbtn',
  				pageList:['15','20'],
  			    url:getContextPath()+'/catalog/yhzwList.do',
  			    
  			    onLoadSuccess:function(){
		  			  	$('.edit_button').linkbutton({    
		  		    	    iconCls: 'icon-edit' ,
		  		    	    height:22,
		  		    	    plain:true,
		  		    	});
  			    },
  			    columns:[[    
  			        {field:'dm',title:'职务代码',width:'10%',align:'center',resizable:false},    
  			        {field:'mc',title:'职务名称',width:'20%',align:'center',resizable:false},    
  			        {field:'pym',title:'拼音码',width:'20%',align:'center',resizable:false},
  			      	{field:'tybz',title:'停用标志',width:'10%',align:'center',resizable:false,
  			        	formatter: function(value){
							if(value==1){
								return '停用';
							}else{
								return '启用';
							}
						}	
  			      	},    
			        {field:'remark',title:'备注',width:'25%',align:'center',resizable:false},    
			        {field:'jj',title:'操作',width:'10%',align:'center',resizable:false,
			        	formatter: function(value,row,index){
								return '<a id="btn" href="#" class="edit_button"  onclick="objYhzw.edit()" >编辑</a>';
							}
			        	
			        } 
  			    ]]    
  			});
  			
  		});
  </script>
   	<div id="yhzwdg">
 		<table id="yhzwListdg"></table>
 		<div id="addbtn">
 			<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="objYhzw.add()">新增</a>
 		</div>
   	</div>
  </body>
</html>
