<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
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
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/flexslider/flexslider.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/fullscreenslides/css/fullscreenstyle.css" />
 	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/flexslider/jquery.flexslider-min.js"></script>
 	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/fullscreenslides/js/jquery.fullscreenslides.js"></script>
 	<style type="text/css">
 		html,body { height:100%;width: 100%;}
	</style>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:true,">
			<div id="historyPreviewDiv"  style="margin:0 auto;height: 100%;padding: 0;border: 0;">
				<div id="slider" class="flexslider" style="margin:0;padding: 0;border: 0;height: 100%;" >
					<ul class="slides" >
						<c:forEach items="${imgEwmList}" var="imgEwmInfo">
							<li><div class="image" ><a rel="gallery" title="${imgEwmInfo.rawJpg}.jpg" 
							href="${picServerUrl}/raw/rawPreviewImg.do?currEwm=${imgEwmInfo.rawJpg}&currFileVersion=${imgEwmInfo.fileVersion}"><img  
							src="${picServerUrl}/raw/rawPreviewImg.do?currEwm=${imgEwmInfo.rawJpg}&currFileVersion=${imgEwmInfo.fileVersion}"
								 width="100%" height="100%"/></a>
 							<div style="display: block; font-weight: bolder;" >${imgEwmInfo.rawJpg}.jpg</div>
							</div></li>	
						</c:forEach>				
					</ul>
				</div>
			</div>
	    </div>
	    <div data-options="region:'south',split:false,border:false," style="width:100%;height:135px;">
	    	<div id="carousel" style="margin:0 auto;"  class="flexslider1" >
				<ul class="slides">
					<c:forEach items="${imgEwmList}" var="imgEwmInfo">
						<li><table><tr><td align="center"><img  width="115px" height="120px" style="border: solid 1px #1A98E5;" title="${imgEwmInfo.rawJpg}.jpg" 
							src="${picServerUrl}/raw/rawPreviewSmallImg.do?currEwm=${imgEwmInfo.rawJpg}&currFileVersion=${imgEwmInfo.fileVersion}" /></td>
							</tr></table></li>
					</c:forEach>   					   		
				</ul>
			</div>
	    </div>
	</div>
	<script type="text/javascript">
		$(function(){
			//设置图片div宽度
			var preImgDivHeight = $(window).height() - 270;
			$('#historyPreviewDiv').width(preImgDivHeight * 0.63);
			$('#carousel').flexslider({
			animation: "slide",
	        controlNav: false,
	        animationLoop: false,
	        slideshow: false,
	        directionNav:true,
	        keyboardNav: true, 
	        itemWidth: 130,
	        asNavFor: '#slider',
	        start: function(carousel){
	       		 $('body').removeClass('loading');
	        }
			});
			
			$('#slider').flexslider({
			animation: "slide",
	        controlNav: false,
	        animationLoop: false,
	        keyboardNav: true, 
	        slideshow: false,
	        itemHeight: '100%',
	        sync: "#carousel",
	        touch: true,
	        start: function(slider){
	       		 $('body').removeClass('loading');
	        }
			});
			
			//设置全屏预览
			$('.image img').fullscreenslides();

			var $container = $('#fullscreenSlideshowContainer');
		
			$container.bind("init", function() { 
			
				$container
				.append('<div class="ui" id="fs-close" >&times;</div>')
				.append('<div class="ui" id="fs-loader">Loading...</div>')
				.append('<div class="ui" id="fs-prev">&lt;</div>')
				.append('<div class="ui" id="fs-next">&gt;</div>')
				.append('<div class="ui" id="fs-caption"><span></span></div>');
			
				$('#fs-prev').click(function(){
					$container.trigger("prevSlide");
				});
				
				$('#fs-next').click(function(){
					$container.trigger("nextSlide");
				});
				
				$('#fs-close').click(function(){
					$container.trigger("close");
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
