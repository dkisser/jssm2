<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>案卷图片</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  </head>
  <body>
  	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/fullscreenslides/css/fullscreenstyle.css" />
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/fullscreenslides/js/jquery.fullscreenslides.js"></script>
	<div  class="easyui-layout" data-options="fit:true">
		<div  data-options="region:'center'" style="padding:10px;background-color: #f4f4f4;">
			<div class="image" id="preImgDiv" style="margin:0 auto;height: 100%;" >
				<a id="preShowBigImg" rel="gallery" title="${zlImgInfo.ewm}.jpg" 
					href="${picServerUrl}/raw/rawPreviewImg.do?currEwm=${zlImgInfo.ewm}&currFileVersion=${zlImgInfo.fileVersion}"><img alt="" 
					id="preShowImg" src="${picServerUrl}/raw/rawPreviewImg.do?currEwm=${zlImgInfo.ewm}&currFileVersion=${zlImgInfo.fileVersion}" 
						width="100%" height="96%" ></a>
				<div id="curImgName" class="caption" style="font-weight: bolder;"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	var isls = "${isls}";
	$(function(){
			var curImgEwm = "${zlImgInfo.ewm}";
			if(curImgEwm){
				var imgName = curImgEwm + '.jpg';
				$('#curImgName').html(imgName);
			}
			$.messager.progress('close');
			if(isls == "true"){
				//历史整理
				var preImgDivHeight = $("#jzInfomltab").tabs("options").height - 20;
				$('#preImgDiv').width(preImgDivHeight * 0.73);
			}else{
				//即时整理
				var preImgDivHeight = $('#ajzlDialog').dialog('options').height- 20;
				$('#preImgDiv').width(preImgDivHeight * 0.73);
			}
			//设置全屏预览
			$('.image img').fullscreenslides();

			var preViewContainer = $('#fullscreenSlideshowContainer');
		
			preViewContainer.bind("init", function() {
			
				preViewContainer
				.append('<div class="ui" id="fs-close" >&times;</div>')
				.append('<div class="ui" id="fs-loader">Loading...</div>')
				.append('<div class="ui" id="fs-caption"><span></span></div>');
			
				$('#fs-prev').click(function(){
					preViewContainer.trigger("prevSlide");
				});
				
				$('#fs-next').click(function(){
					preViewContainer.trigger("nextSlide");
				});
				
				$('#fs-close').click(function(){
					preViewContainer.trigger("close");
				});
			
			})
			
			.bind("startLoading", function() { 
				$('#fs-loader').show();
			})
			
			.bind("stopLoading", function() { 
				$('#fs-loader').hide();
			})
			
			.bind("startOfSlide", function(event, slide) { 
		
				$('#fs-caption span').text(slide.title);
				$('#fs-caption').show();
			})
			
			.bind("endOfSlide", function(event, slide) { 
				$('#fs-caption').hide();
			});
			
		});
	</script>
  </body>
</html>
