<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html>
<html>
<body>
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/style/JZQuery.css">
	<div id="query_Dlg">
		<div id="query_Datagrid"></div>
	</div>
	
	<!-- ------------------------------------------------------------------------------------------------------ -->
	<div id="queryToolsBar" style="height:110px;padding-left:20px;">
		<form id="query_form_Submit" method="post">
			<table id="tableSty">
				<tr class="rowSty">
					<td class="textSty">年份:</td><td class="iptSty"><input id="query_yearIpt" name="year"/></td>
					<td class="textSty">审判字号:</td><td class="iptSty"><input id="query_cpzhIpt" name="spzh"/></td>
					<td class="textSty">案卷编号:</td><td class="iptSty"><input id="query_bhIpt" name="ajbh"/></td>
					<td class="textSty">卷宗类型:</td><td class="iptSty"><input id="query_jzlxIpt" name="jzlx"/></td>
					<td class="textSty">安全级别:</td><td class="iptSty"><input id="query_aqjbIpt" name="aqjb"/></td>
					<td class="textSty">保管期限:</td><td class="iptSty"><input id="query_bgqxIpt" name="bgqx"/></td>
				</tr>
				<tr class="rowSty">
					<td class="textSty">当事人:</td><td class="iptSty"><input id="query_dsrIpt" name="dsr"/></td>
					<td class="textSty">收卷案由:</td><td class="iptSty"><input id="query_aymsIpt" name="ayms"/></td>
					<td class="textSty">归档年份:</td><td class="iptSty"><input id="query_gdnfIpt" name="gdnf"/></td>
					<td class="textSty">卷宗状态:</td><td class="iptSty"><input id="query_jzztIpt" name="jzzt"/></td>
					<td class="textSty">归档号数:</td><td class="iptSty"><input id="query_gdhsIpt" name="gdhs"/></td>
					<td></td>
					<td></td>
				</tr>
			</table>
			<table class="tableSty1">
				<tr class="rowSty1">
	 				<td class="textSty1">立案日期:</td><td class="iptSty1 dateSty"><input id="query_larqFIpt" name="larqFStr"/></td> 
	 				<td class="textSty1 fromto">到:</td><td class="iptSty1 dateSty"><input id="query_larqTIpt" name="larqTStr"/></td> 
	 				<td class="textSty1">结案日期:</td><td class="iptSty1 dateSty"><input id="query_jarqFIpt" name="jarqFStr"/></td> 
	 				<td class="textSty1 fromto">到:</td><td class="iptSty1 dateSty"><input id="query_jarqTIpt" name="jarqTStr"/></td> 
	 				<td class="textSty1 fmewmSty">封面二维码:</td><td class="iptSty1 fmewmStyIpt"><input id="query_fmewmIpt" name="fmEwm"/></td>
	 				<td><a class="btnSty" id="query_findBtn">查询</a>&nbsp;<a class="btnSty" id="query_resetBtn">重置</a></td> 
				</tr>
			</table>
		</form>
	</div>
	<!-- ------------------------------------------------------------------------------------------------------ -->
	
	
	<script>
			
			$("#query_Dlg").dialog({
					title:'卷宗内部查询',
					width:1024,
					height:600,
					modal:true,
					inline:true,
				});
			jzQueryInit();
			enterEvent(nbcxSubmit);
			
			objMethod = {
				browse:function(index){
					var datagridData = $("#query_Datagrid").datagrid('getData').rows[index];
					var ah = datagridData.ah;
					var jzewm =datagridData.jzewm;
					var jzBrowseDia =  $("<div id=\"nbcxBrowseDia\"></div>");
					jzBrowseDia.appendTo("body");
					$("#nbcxBrowseDia").dialog({
						width:1024,
						title:ah,
						href:getContextPath() + "/ajuan/jzBrowse.do?hasdlg=no&jzEwm="+jzewm, 
						height:600,
						closed:false,
						cache:false,
						shadow:true,	
						resizable:false,	
						modal:true,
						inline:true,	
						onClose: function(){
							jzBrowseDia.remove();
						},
					});
				},
				
			};
			
			$("#query_resetBtn").linkbutton({
				iconCls:'icon-undo',
				onClick:function(){
					JzQueryReset();
				}
			});
			
			$("#query_findBtn").linkbutton({
				iconCls:'icon-search',
				onClick:function(){
					nbcxSubmit();
				}
			});
			
			function nbcxSubmit(){
				$.messager.progress();	
				$('#query_form_Submit').form('submit', {
					url: getContextPath()+"/query/formSubmit.do",
					onSubmit: function(){
						var isValid = $(this).form('validate');
						if (!isValid){
							$.messager.alert('提示','请按要求输入查询条件');  
							$.messager.progress('close');
						}
						return isValid;	
					},
					success: function(data){
						var status = data.slice(1,data.length-1);
						if(status == 'success'){
							$("#query_Datagrid").datagrid({
								url:getContextPath()+"/query/query_Datagrid.do",
							});
						}
						$.messager.progress('close');
					}
				});
			}
			
			$("#query_Datagrid").datagrid({
				border:false,
				noheader:true,
				width:'100%',
				height:'100%',
				toolbar:'#queryToolsBar',
				columns:[[    
				        {
				        	field:'ah',
				        	title:'案号',
				        	resizable:false,
				        	width:"20%",
				        	align:'center',
				        	formatter: function(value,row,index){
				        		if (value != null) {
									var abValue = value;
									var content = '<a href="#" title="' + value + '" class="note"  style="text-decoration:none;color:#000">'
											+ abValue + '</a>';
									return content;
								} else {
									return '';
								}
							}
				        },
				        {
				        	field:'jzlx',
				        	title:'卷宗类型',
				        	resizable:false,
				        	width:"10%",
				        	align:'center'
				        }, 
				        {
				        	field:'jh',
				        	title:'卷号',
				        	resizable:false,
				        	width:"10%",
				        	align:'center'
				        }, 
				        {
				        	field:'aqjb',
				        	title:'安全级别',
				        	resizable:false,
				        	width:"10%",
				        	align:'center'
				        }, 
				        {
				        	field:'status',
				        	title:'归档状态',
				        	resizable:false,
				        	width:"10%",
				        	align:'center',
				        	formatter: function(value,row,index){
								if (row.status == '0'){
									return "未归档";
								}else if(row.status == '1'){
									return "归档";
								}else{
									return null;
								}
							}
				        }, 
				        {
				        	field:'gdrq',
				        	title:'归档日期',
				        	resizable:false,
				        	width:"10%",
				        	align:'center'
				        },
				        {
				        	field:'gdhs',
				        	title:'归档号数',
				        	resizable:false,
				        	width:"10%",
				        	align:'center'
				        }, 
				        {
				        	field:'method',
				        	title:'操作',
				        	width:"18%",
				        	resizable:false,
				        	align:'center',
				        	formatter : function(value, row,index) {
				        		if(row.ah != null){
				        			return '<a href="#" class="browseBtn"  onclick="objMethod.browse('+index+')" style="text-decoration:none;color:#000">浏览</a>';
				        		}else{
				        			return null;
				        		}
							}
				        },
				    ]],
				onLoadSuccess:function(data){
					 if (data.total == 0) {
						    var body = $(this).data().datagrid.dc.body2;
						    body.find('table tbody').append('<tr><td colspan="8" width="' + body.width() + '" style="color:red;height: 25px; text-align: center;border-style: dotted;border-width: 0px 0px 0px 0px;">没有相关记录！</td></tr>');
						    $(this).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();//隐藏底部工具栏
					}else{
						$(this).closest('div.datagrid-wrap').find('div.datagrid-pager').show();//显示底部工具栏
					}
					
					$(".browseBtn").linkbutton({plain:true,height:20, iconCls:'icon-search'});
					$(".note").tooltip({
						onShow : function() {
						$(this).tooltip('tip').css({
							boxShadow : '1px 1px 3px #292929'
						});
						}
					});
				},
				onBeforeLoad:function(){
					$(this).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
				},
				loadMsg:'正在加载数据...',
				pagination:true,
				pagePosition:'bottom',
				pageSize:10,
				pageList:[10,15,20],
				singleSelect:true,
				rownumbers:true
			});
			
  		</script>

</body>
</html>