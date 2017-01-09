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
	<div id="showHistoryDialog">
		<div class="easyui-layout" data-options="fit:true">   
		    <div data-options="region:'west',split : false,border:false," style="width:130px;height:100%;">
		    	<div  data-options="border:false,fit:true" class="easyui-accordion" style="width:100%;height:100%;">
		    		<div title="扫描时间" data-options="border:false,collapsible:false,fit:true" style="overflow:auto;">
			    		<ul id="historyTree"></ul>
		    		</div>
		    	</div>
		    </div>   
		    <div id="showHistoryCenter"  data-options="region:'center',border:true," style="padding:0;margin: 0"></div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$('#showHistoryDialog').dialog({
				title : '扫描历史浏览',
				width : '100%',
				height : '100%',
				closed : false,
				cache : false,
				shadow : true, //显示阴影
				resizable : false, //不允许改变大小
				modal : true, //模式化窗口，锁定父窗口
				inline : true, //是否在父容器中，若不在会出现很多BUG
				toolbar : [{
					text : "返回扫描",
					iconCls:'icon-back',
					handler:function(){
						var url="${pageContext.servletContext.contextPath }/raw/rawScanUI.do";
						$('#showHistoryDialog').dialog('close')
						$('#center').panel('refresh',url);
					}
				}],
			});
			
			//初始化树目录
			$('#historyTree').tree({
			    url : '${pageContext.servletContext.contextPath }/raw/scanHistoryTree.do?startDate=${historyStartDate}&endDate=${historyEndDate}',
			    animate : false,//展开动画
			    lines : true,//虚线连接
			    onLoadError : function(arg){
			    	$.messager.alert('','查询目录结构出错','info');
			    },
			    onClick: function(node){
			    	if($('#historyTree').tree('isLeaf',node.target)){
			    		//没有子节点时,获得父节点
						var day = $('#historyTree').tree('getParent',node.target);
						var month = $('#historyTree').tree('getParent',day.target);
						var year = $('#historyTree').tree('getParent',month.target);
						var url = '${pageContext.servletContext.contextPath }/raw/getHistoryImgs.do?year='+$.trim(year.text.replace(/年/,""))+'&month='+$.trim(month.text.replace(/月/,""))
							+ '&day='+$.trim(day.text.replace(/日/,""))+'&hour='+$.trim(node.text.replace(/时/,""));
						$('#showHistoryCenter').panel('refresh',url);
			    	}else{
			    		//点击非叶节点，则展开目录
		    			$('#historyTree').tree('expand',node.target);
			    	}
				},
			});
		});
	</script>
</body>
</html>
