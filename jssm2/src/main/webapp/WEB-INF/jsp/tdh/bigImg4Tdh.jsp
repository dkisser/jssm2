<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
  <body>
	<img id="theBigImg" alt="" src="${picServerUrl}/tdh/previewImg4Tdh.do?currEwm=${picEwm4Tdh}&currFileVersion=1" >
	<script type="text/javascript">
// 		var theBigImgOpt = {
// 			showOtherImg : function(flag){
// 				$.messager.progress();
// 				$.ajax({
// 					url: "",
// 					async:true,		//是否异步请求
// 					cache:false,	//是否缓存
// 					type:"post",	//请求方式
// 					dataType:"text",	//返回数据类型
// 					data : {"nodeIds" : nodeIds,"isls" : isls},
// 					success:function(data){
// 						$.messager.progress('close');
// 						if(data == '"success"'){
// 							$('#ajzlTree').tree('reload');//刷新树目录
// 						}else{
// 							$.messager.alert('','删除失败','warning');
// 						}
// 					},
// 					error : function(){
// 						$.messager.progress('close');
// 						$.messager.alert('错误','系统异常','warning');
// 					}
// 				});
// 			}
// 		};
// 		window.document.onkeydown = function(e){
// 			var theEvent = e || window.event;
// 			var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
// 			if(showBigImgFlag == "yes"){
// 				if(code == 37){
// 					theBigImgOpt.showOtherImg("pre");//左键按下
// 				}
// 				if(code == 39){
// 					theBigImgOpt.showOtherImg("next");//右键按下
// 				}
// 			}
// 		};
	</script>
  </body>
</html>
