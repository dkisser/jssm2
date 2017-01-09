<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
	<link rel="stylesheet" type="text/css"
		href="${pageContext.servletContext.contextPath }/js/jquery-css3-gallery-animation/css/zzsc.css" />
	<link rel="stylesheet" type="text/css"
		href="${pageContext.servletContext.contextPath }/js/fullscreenslides/css/fullscreenstyle.css" />
		<link rel="stylesheet" type="text/css"
		href="${pageContext.servletContext.contextPath }/js/fullscreenslides/css/pagestyle.css" />
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath }/js/fullscreenslides/js/jquery.fullscreenslides.js"></script>
	<div id="gallery-container">
		<ul class="items--small">
			<c:forEach items="${requestScope.listPictureInfo}" var="Picture">
				<li class="item">
					<a href="#">
						<figure>
							<a class="jzBrowseA"  rel="gallery" title="${Picture.pageNumber}"
								href='${picServerUrl}/raw/rawPreviewImg.do?currEwm=${Picture.picEwm}&currFileVersion=${Picture.picVersion}'>
								<img src='${picServerUrl}/raw/rawPreviewSmallImg.do?currEwm=${Picture.picEwm}&currFileVersion=${Picture.picVersion}'
										alt="" />
								<figcaption class="img-caption" style="font-size: 0.8em;text-align: center;">${Picture.pageNumber}</figcaption>
							</a>
						</figure>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<script>
		setTimeout(SetImg, 1500);
		function SetImg() {
			$("img").each(function() {
				if (CheckImgExistsSrc($(this).attr("src")) == false) {
					$(this).attr("src",getContextPath()
						+ "/js/jquery-css3-gallery-animation/images/serverNoPicture.jpg").width("100px").height("140px");
					}
				});
			$("figcaption").each(function(){
				if($(this).html()==""){
					$(this).html("&nbsp;");
				}
			});
		}
		
		function CheckImgExistsSrc(imgurl) {
			var ImgObj = new Image();
			ImgObj.src = imgurl;
			if (ImgObj.fileSize > 0 || (ImgObj.width > 0 && ImgObj.height > 0)) {
				return true;
			} else {
				return false;
			}
		}
		
	</script>
	<script>
		$(function() {
			$('.item img').fullscreenslides();
			var $container = $('#fullscreenSlideshowContainer');
			$container.bind("init",
							function() {
								$container
										.append(
												'<div class="ui" id="fs-close">&times;</div>')
										.append(
												'<div class="ui" id="fs-loader">Loading...</div>')
										.append(
												'<div class="ui" id="fs-prev">&lt;</div>')
										.append(
												'<div class="ui" id="fs-next">&gt;</div>')
										.append(
												'<div class="ui" id="fs-caption"><span></span></div>');

								$('#fs-prev').click(function() {
									$container.trigger("prevSlide");
								});

								$('#fs-next').click(function() {
									$container.trigger("nextSlide");
								});

								$('#fs-close').click(function() {
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

	</div>
</body>
</html>
