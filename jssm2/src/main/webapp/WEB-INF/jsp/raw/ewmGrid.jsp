<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>二维码打印</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
    <style type="text/css">
    	html,body{
    		border: 0px;
    		margin: 0px;
    		padding: 0px;
    		width: 752px;
    		overflow: auto;
    	}
    	.chrome_adjust{
		    font-size: 41%;line-height:1;padding-top: 2px;
/* 		    -webkit-transform: scale(0.7);   */
		}
    </style>
  </head>
  <body><%-- 分辨率是96像素/英寸时，A4纸的尺寸的图像的像素是794×1123；(默认) --%>
    <table cellpadding="11px" cellspacing="0" border="0" style="text-align: center;width: 752px;">
    	<c:forEach items="${allEwmInfo}" var="rowInfo" varStatus="status">
    		<tr>
    			<c:forEach items="${rowInfo}" var="colInfo" varStatus="status2">
    				<td>
    					<table cellpadding="0" cellspacing="0" border="0" style="text-align: center;">
    						<tr>
    							<td class="ewmImg" title="${colInfo}"></td>
    						</tr>
    						<tr>
    							<td class="chrome_adjust">${colInfo}</td>
    						</tr>
    					</table>
    				</td>
    			</c:forEach>
    		</tr>
    	</c:forEach>
	</table>
    <script type="text/javascript">
    	$(function(){
    		var ewmInfoList = "${allEwmInfo[0]}";
    		if(ewmInfoList == null || ewmInfoList ==''){
    			alert("生成二维码出错或者当天二维码达到上限");
    			//关闭当前窗口
    			window.opener=null;
				window.open('','_self');
				window.close();
    			return false;
    		}
    		var ewmObjs = $(".ewmImg");//获得所有二维码div对象
    		$.each(ewmObjs, function(index, value){
    			$(value).qrcode({
					width: 72, //宽度 
			    	height: 73, //高度
					text : $(value).attr('title')
				});
			});
    	});
    </script>
  </body>
</html>
