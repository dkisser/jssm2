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
var Operations={
		edit:function(){
			var div = $ ("<div id=\"dgEdit\"></div>");
			div.appendTo("body");
			$("#dgEdit").dialog({
				title: '编辑二级机构', 	
			    width: 512,    
			    height: 300,  
			    href: getContextPath() + "/catalog/jgdmInfo.do?isNew="+"no",
			    closed: false,
			    modal: true,				   
			    onClose:function(){
			    	div.remove();
			    },
			});
		},
		
		
add:function(){
	var div = $ ("<div id=\"dgAdd\"></div>");
			div.appendTo("body");
			$("#dgAdd").dialog({
				title: '新建二级机构', 	
			    width: 512,    
			    height: 300,  
			    href: getContextPath() + "/catalog/jgdmInfo.do?isNew="+"yes",
			    closed: false,
			    modal: true,				   
			    onClose:function(){
			    	div.remove();
			    },
			});
		}		
		
};


	$('#dg').dialog({    
		title : '二级机构代码列表',
		width: 1024,    
	    height: 600,
		closed : false,
		cache : false,
		shadow : true, //显示阴影
		resizable : false, //不允许改变大小
		modal : true, //模式化窗口，锁定父窗口
		inline : true, //是否在父容器中，若不在会出现很多BUG
	});
	$("#jgdmDg").datagrid({
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
			pageList:['15','20'],
		    url:getContextPath()+'/catalog/jgdmListInfo.do',
		striped:true,
		loadMsg:'正在加载....',
		pagination:true,
		onLoadSuccess:function(){//加载完后将edit渲染为button
	    	$('.edit_button').linkbutton({    
	    	    iconCls: 'icon-edit' ,
	    	    height:22,
	    	    plain:true,
	    	});
	    },
		columns:[[    
		          {field:'dm',title:'机构代码',width:'10%',align:'center',resizable : false,},    
		          {field:'mc',title:'机构名称',width:'15%',align:'center',resizable : false,},    
		          {field:'sjmc',title:'上级机构',width:'15%',align:'center',resizable : false,}, 
		          {field:'pym',title:'拼音码',width:'10%',align:'center',resizable : false,} ,
		          {field:'tybz',title:'停用标志',width:'10%',align:'center',resizable : false,
		        	  formatter: function(value){
							if(value==1){
								return '停用';
							}else{
								return '启用';
							}
						}	  
		          },
		          {field:'remark',title:'备注',width:'25%',align:'center',resizable : false,
	  			      	formatter: function(value,row,index){
	  						if(row.remark!=null&&row.remark.length>16){
	  							var content=row.remark.substring(0,16)+'...';
	  							//<abbr title="etcetera">etc.</abbr>
	  							return "<abbr title='"+row.remark+"'>"+content+"</abbr>";
	  						}else{
	  							return row.remark;
	  						}
	  					}
		        	},    
		          {field:'edit',title:'编辑',width:'10%',align:'center',resizable : false,
		        	  formatter: function(value,row,index){
		        		  return '<a id="btn" href="#" class="edit_button"  onclick="Operations.edit()" >编辑</a>';
						}
		          },
		          {field:'sjdm',title:'sjdm',hidden:true,resizable : false,} ,
		          
		      ]]    

	});

</script>
<div id="dg">
	<table id="jgdmDg"></table>
	<div id="bar">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="Operations.add()">新建</a>
	</div>
</div>
</body>
</html>
