<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>博法案卷云管理平台</title>
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
</style>

</head>
<body>
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/fullscreenslides/css/fullscreenstyle.css" />
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/fullscreenslides/js/jquery.fullscreenslides.js"></script>
	
	<div  class="easyui-layout" data-options="fit:true">
		<div  data-options="region:'center'" style="padding:10px;">
			<div class="image" id="preImgDiv" style="margin:0 auto;height: 100%;" >
				<a id="preShowBigImg" rel="gallery" title="${currImgEwm}.jpg" href=""><img alt="" 
					id="preShowImg" src="" width="100%" height="96%" ></a>
					<div class="caption" style="font-weight: bolder;">${currImgEwm}.jpg</div>
			</div>
		</div>
		<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 0;height:34px;">
			<span><a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="confirmImg();" style="width:80px">完成</a>&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span><a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="doCancle();" style="width:80px">删除</a></span>
		</div>
	</div>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/jssm/shortcutKeyDisable.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/fullscreenslides/js/jquery.fullscreenslides.js"></script>
<script type="text/javascript">
		$(function(){
			$.messager.progress('close');
			//设置图片div宽度
			var preImgDivHeight = $(window).height() - 170;
			$('#preImgDiv').width(preImgDivHeight * 0.76);
			//设置图片访问路径，注意加入随机数，以防止图片缓存
			var imgUrl = '${picServerUrl}/raw/rawPreviewImg.do?currEwm=${currImgEwm}&currFileVersion=${currImgFileVer}';
			$('#preShowImg').attr('src',imgUrl);
			$('#preShowBigImg').attr('href',imgUrl);
			//设置全屏预览
			$('.image img').fullscreenslides();

			var preViewContainer = $('#fullscreenSlideshowContainer');
		
			preViewContainer.bind("init", function() {
			
				preViewContainer
				.append('<div class="ui" id="fs-close" >&times;</div>')
				.append('<div class="ui" id="fs-loader">Loading...</div>')
				.append('<div class="ui" id="fs-prev">&lt;</div>')
				.append('<div class="ui" id="fs-next">&gt;</div>')
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
		//确认
		function confirmImg(){
			$.messager.progress();
			//确认保存文件
	        $.ajax({
		        url: "${pageContext.servletContext.contextPath}/raw/confirmRawFile.do",
		        async : true,
		        type : 'POST',
		        data : {ewm : "${currImgEwm}"},
		        cache:false,
		        dataType:'text',
		        beforeSend : function(){
		        	if(!'${currImgEwm}'){
		        		$.messager.progress('close');
		        		return false;
		        	}
		        },
		        success: function(data){
		        	$.messager.progress('close');
		        	if(data == '\"success\"'){
		        		//加载快捷键
		        		$.getScript("${pageContext.servletContext.contextPath }/js/jssm/shortcutKey.js");
						$('#rawPreviewDialog').dialog('close',true);
						playTipAudioSuccess();//播放成功声音
		        	}else{
		        		playTipAudioError();//播放失败声音
		        		$.messager.alert('','保存文件出错');
			        	return false;
		        	}
		        },
		        error : function(){
		        	$.messager.progress('close');
		        	playTipAudioError();//播放失败声音
		        	$.messager.alert('','保存文件出错');
		        }
		    });
		}
		//删除
		function doCancle(){
// 			console.log($(document.body).height());
// 			console.log($(window).height());
			$.messager.confirm('','确定要删除该图片吗？',function(r){
				if(r){
					$.messager.progress();
					//删除文件
			        $.ajax({
				        url: "${pageContext.servletContext.contextPath}/raw/deleteRawFile.do",
				        async : true,
				        type : 'POST',
				        data : {ewm : "${currImgEwm}"},
				        cache:false,
				        dataType:'text',
				        success: function(data){
				        	$.messager.progress('close');
				        	if(data == '\"success\"'){
				        		//加载快捷键
				        		$.getScript("${pageContext.servletContext.contextPath }/js/jssm/shortcutKey.js");
								$('#rawPreviewDialog').dialog('close',true);
				        	}else{
				        		$.messager.alert('','删除失败');
					        	return false;
				        	}
				        },
				        error : function(){
				        	$.messager.progress('close');
				        	$.messager.alert('','删除失败');
				        }
				    });
				}
			});
		}
		
	</script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/tip_audio.js"></script>
</body>
</html>
