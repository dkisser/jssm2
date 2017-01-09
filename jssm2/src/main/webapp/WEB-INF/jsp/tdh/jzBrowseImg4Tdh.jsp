<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui4tdh/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui4tdh/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui4tdh/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui4tdh/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui4tdh/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	padding: 0px;
}
</style>


</head>

<body>
	<%-- 
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/fullscreenslides4Tdh/css/fullscreenstyle.css" />
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/fullscreenslides4Tdh/js/jquery.fullscreenslides.js"></script>
	--%>
	<c:forEach items="${requestScope.listPictureInfo}" var="Picture">
		<div class="imageForTdh"  style="width: 118px;height: 178px;float: left;margin-left: 3px;margin-top: 5px;" >
			<a rel="gallery" title="${Picture.pageNumber}"  style="text-decoration: none;"
				href="javascript:void(0);" onclick="showBigImg('${Picture.picEwm}','${Picture.pageNumber}')">
				<img src='${picServerUrl}/tdh/previewSmallImg4Tdh.do?currEwm=${Picture.picEwm}&currFileVersion=1'
						alt="${Picture.pageNumber}" style="border: 0px;width: 118px;height: 158px;" />
				<div class="caption" style="font-size: 18px;text-align: center;">${Picture.pageNumber}</div>
			</a>
		</div>
	</c:forEach>
	
	<script type="text/javascript">
		var showBigImgFlag = "";
		function showBigImg (picEwm,pageNumber){
			var bigImgDg = $('<div id="bigImgDg" style="text-align: center;overflow: auto;" ></div>');
			bigImgDg.appendTo("body");
			showBigImgFlag = "yes";
			$("#bigImgDg").dialog({
				title : "图片预览（" + pageNumber + "）",
				fit : true,
				closed : false,
				cache : true,
				shadow : true, //显示阴影
				resizable : false, //不允许改变大小
				modal : true, //模式化窗口，锁定父窗口
				inline : true, //是否在父容器中，若不在会出现很多BUG
				href : "${pageContext.servletContext.contextPath}/tdh/showBigImg4Tdh.do?picEwm="+picEwm,
				onClose: function(){
					showBigImgFlag = "no";
					bigImgDg.remove();
				}
			});
		};
		$(function(){
			if(loadFlag){
				loadFlag = false;
				var listPicCounts = parseInt("${listPicCounts}");
				if(!isNaN(listPicCounts) && listPicCounts > 0){
					var interval = listPicCounts * (30);
					if(interval > 5000){
						interval = 5000;
					}
					$.messager.progress();
					setTimeout(function() {
						$.messager.progress('close');
					}, interval);
				}
			}
		});
	</script>
</body>
</html>
