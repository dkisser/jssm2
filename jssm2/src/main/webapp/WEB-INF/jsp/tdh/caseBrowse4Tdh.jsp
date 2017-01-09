<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>档案列表</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui4tdh/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui4tdh/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui4tdh/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui4tdh/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui4tdh/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  </head>
  <body>
    <div class="easyui-layout" data-options="border:false,fit:true" >
		<div  data-options="region:'west',split:false,border:true" style="width:200px;border-right: 2px solid #EDF4FF">
			<div>
				<ul id="jzBrowseTree"></ul>
			</div>
		</div>
		<div id="pictureShowPanel" data-options="region:'center',border:false,
			href:'${pageContext.servletContext.contextPath}/tdh/jzBrowseImg4Tdh.do?jzEwm=${requestScope.jzewm4Tdh}'" style="padding: 5px 30px;">
		</div>
	</div>
    <script type="text/javascript">
    	var loadFlag = true;
    	$(function(){
    		var jzewm = "${requestScope.jzewm4Tdh}";
    		$("#jzBrowseTree").tree(
	    		{
	    			url : "${pageContext.servletContext.contextPath}/tdh/tdhTreeData.do?jzEwm=${requestScope.jzewm4Tdh}",
	    			lines : true,
	                cascadeCheck:true,
					checkbox : false,
					onClick : function(node) {
						if(node.text=='全部'){
							$('#pictureShowPanel').panel('refresh','${pageContext.servletContext.contextPath}/tdh/jzBrowseImg4Tdh.do?jzEwm=${requestScope.jzewm4Tdh}');
						} else {
							var url4Tdh = encodeURI("${pageContext.servletContext.contextPath}/tdh/jzBrowseImgAll4Tdh.do?jzEwm=${requestScope.jzewm4Tdh}&nodeText="+node.text);
							$("#pictureShowPanel").panel('refresh',url4Tdh);
						}
					}
				}
			);
    	});
    </script>
  </body>
</html>
