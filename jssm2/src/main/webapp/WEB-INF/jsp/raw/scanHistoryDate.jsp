<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  </head>
  
<body>
	<div id="scanHistoryDialog">
		<div class="easyui-layout" style="width:100%;height:100%;">   
		    <div data-options="region:'center'" style="padding:5px;padding-top: 15px;">
		    	<form id="scanHistoryForm" method="post">
			    	<table align="center">
			    		<tr>
			    			<td>起始时间： </td>
			    			<td><input  id="startDate" name="startDate"  type= "text"  style="width:230px"></input></td>
			    		</tr>
			    		<tr>
			    			<td colspan="2"> &nbsp;</td>
			    		</tr>
			    		<tr>
			    			<td>结束时间： </td>
			    			<td><input  id="endDate" name="endDate" type= "text"  style="width:230px"></input></td>
			    		</tr>
			    	</table>
		    	</form>
		    </div>   
		    <div data-options="region:'south',split : false,border:false," style="width:100%;height: 49px;">
		    	<table  align="center">
		    		<tr><td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="doSearch();" 
		    				data-options="plain : true,iconCls:'icon-search',size:'large',
					        iconAlign:'left'" >查询</a></td></tr>
		    	</table>
		    </div>   
		</div>
	</div>
	<script type="text/javascript">
		//将时间格式化为2016-08-09的形式
		function formatterDate(date) {
			var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
			var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
			+ (date.getMonth() + 1);
			return date.getFullYear() + '-' + month + '-' + day;
		};
		//查询
		function doSearch(){
			$.messager.progress();
			$('#scanHistoryForm').form('submit', {
			    url : "${pageContext.servletContext.contextPath }/raw/selectHistory.do",    
			    onSubmit : function(){
		    		var isValid = $(this).form('validate');
					if (!isValid) {
						$.messager.progress('close');
						$.messager.alert('','请按照要求填写表单','info');
						return isValid;
					}
					var startDate = $('#startDate').datebox('getValue');
					var endDate = $('#endDate').datebox('getValue');
					if(startDate > endDate){
						$.messager.alert('','起始时间不能大于结束时间','info');
						$.messager.progress('close');
						return false;
					}
			    },
			    success : function(data){
			    	$.messager.progress('close');
			    	if(data == '\"success\"'){
						var url="${pageContext.servletContext.contextPath }/raw/showHistoryUI.do?startDate="+$('#startDate').datebox('getValue')
							+"&endDate="+$('#endDate').datebox('getValue');
			    		$('#scanHistoryDialog').dialog('close');
						$('#center').panel('refresh',url);
			    	}else{
			    		$.messager.alert('','没有相关历史记录','info');
			    	}
			    }
			});
		};
		$(function(){
			//起始时间,默认为当前时间前一周
			var weekAgo = new Date();
			var oneWeek = 7*24*60*60*1000;
			weekAgo.setTime(weekAgo.getTime()-oneWeek);
			$('#startDate').datebox({
			    required:true,
			    editable:false,
			    value:formatterDate(weekAgo),
			});
			//结束时间，默认为当前时间
			$('#endDate').datebox({
			    required:true,
			    editable:false,
			    value:formatterDate(new Date()),
			});
			$('#scanHistoryDialog').dialog({
				title : '扫描历史查询',
				width : 360,
				height : 220,
				closed : false,
				cache : false,
				shadow : true, //显示阴影
				resizable : false, //不允许改变大小
				modal : true, //模式化窗口，锁定父窗口
				inline : true, //是否在父容器中，若不在会出现很多BUG
			});
		});
	</script>
</body>
</html>
