<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>图片上传</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  </head>
  
  <body>
    <div id="rawPicUploadDilog" style="text-align: center;">
    	<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center'" style="padding:30px 70px 50px 70px;text-align: center;">
				<div>
					<input id="tipInfo" type="text" style="width:350px">
				</div><br>
				<form id="rawPicUploadForm" enctype="multipart/form-data" method="post">
			    	<input id="file_upload" name="file_upload" type="text" style="width:350px"><br>
		    	</form>
<!-- 		    	<embed type="application/npcamera" width=1 height=1 id="plugin"> -->
			</div>
			<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 0;height:34px;">
				<span><a id="doUploadBtn" href="javascript:void(0)" style="width:80px">上传</a></span>
			</div>
		</div>
    </div>
    <script type="text/javascript">
//     	var PLUGIN = null;
//     	var baseImgPath = "D:\\DocImage\\";
    	//高拍仪初始化
// 		function pluginInit()
// 		{
// 			PLUGIN = document.getElementById("plugin");
// 		};
// 		function DeletePicsByPath()
// 		{
// 			PLUGIN.DeletePicsByPath(baseImgPath);//删除目录下的所有文件，但是不删除本身的空目录
// 		};
    	var rawPicUploadOpt = {
    		doUpload : function(){
    			if($.trim($("#file_upload").filebox("getText")) == ""){
					$.messager.progress('close');
					$.messager.alert('','请至少选择一个文件');
					return false;
				}
	    		$.messager.confirm('确认上传', '确定要上传选中的图片吗？', function(r){
					if (r){
						$.messager.progress();	// 显示进度条
						$('#rawPicUploadForm').form('submit', {
							url: "${pageContext.servletContext.contextPath}/raw/rawPicUpload.do",
							onSubmit: function(param){
								param.userId="${sessionScope.loginInfo.userId}";
								param.upload4Zl = "false";
							},
							success: function(data){
								$.messager.progress('close');//如果提交成功则隐藏进度条
								console.log(data);
								if(data != null && data.indexOf("Exception") >= 0){
									//上传成功
									$.messager.alert('','上传失败');
								} else {
									var result = eval('(' + data + ')');
									var msgInfo = result.msgInfo;
									if (msgInfo != null && msgInfo != "null" && msgInfo.length > 0){//存在上传失败的文件
										var result = eval('(' + data + ')');
										var msgInfo = result.msgInfo;
										var uploadErrorData = msgInfo.substring(0,msgInfo.length-1);//去除末尾分号
										//显示上传失败的文件列表对话框
										var uploadErrorFiles = $("<div id=\"uploadErrorFiles\"></div>");
										uploadErrorFiles.appendTo("body");
										$("#uploadErrorFiles").dialog({
											title: '上传失败，请重新拍摄并上传', 	
										    width: 512,    
										    height: 322,    
										    closed: false,
										    closable: false,
										    cache: false,
										    modal: true,
										    inline: true,
										    onClose:function(){
										    	uploadErrorFiles.remove();
										    },
										    queryParams:{
										    	"uploadErrorData" : uploadErrorData
										    },
										    href: "${pageContext.servletContext.contextPath}/raw/rawPicUploadErrorUI.do"
										});
									}else{
										//上传成功
										$.messager.alert('','上传成功');
									}
								}
							}
						});
					}
				});
    		}
    	};
    	function PreviewImage(fileObj,imgPreviewId,divPreviewId){  
		    var allowExtention=".jpg,.bmp,.gif,.png";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;  
		    var extention=fileObj.value.substring(fileObj.value.lastIndexOf(".")+1).toLowerCase();              
		    var browserVersion= window.navigator.userAgent.toUpperCase();  
		    if(allowExtention.indexOf(extention)>-1){   
		        if(fileObj.files){//HTML5实现预览，兼容chrome、火狐7+等  
		            if(window.FileReader){  
		                var reader = new FileReader();   
		                reader.onload = function(e){  
		                    document.getElementById(imgPreviewId).setAttribute("src",e.target.result);  
		                }    
		                reader.readAsDataURL(fileObj.files[0]);  
		            }else if(browserVersion.indexOf("SAFARI")>-1){  
		                alert("不支持Safari6.0以下浏览器的图片预览!");  
		            }  
		        }else if (browserVersion.indexOf("MSIE")>-1){  
		            if(browserVersion.indexOf("MSIE 6")>-1){//ie6  
		                document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);  
		            }else{//ie[7-9]  
		                fileObj.select();  
		                if(browserVersion.indexOf("MSIE 9")>-1)  
		                    fileObj.blur();//不加上document.selection.createRange().text在ie9会拒绝访问  
		                var newPreview =document.getElementById(divPreviewId+"New");  
		                if(newPreview==null){  
		                    newPreview =document.createElement("div");  
		                    newPreview.setAttribute("id",divPreviewId+"New");  
		                    newPreview.style.width = document.getElementById(imgPreviewId).width+"px";  
		                    newPreview.style.height = document.getElementById(imgPreviewId).height+"px";  
		                    newPreview.style.border="solid 1px #d2e2e2";  
		                }  
		                newPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";                              
		                var tempDivPreview=document.getElementById(divPreviewId);  
		                tempDivPreview.parentNode.insertBefore(newPreview,tempDivPreview);  
		                tempDivPreview.style.display="none";                      
		            }  
		        }else if(browserVersion.indexOf("FIREFOX")>-1){//firefox  
		            var firefoxVersion= parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);  
		            if(firefoxVersion<7){//firefox7以下版本  
		                document.getElementById(imgPreviewId).setAttribute("src",fileObj.files[0].getAsDataURL());  
		            }else{//firefox7.0+                      
		                document.getElementById(imgPreviewId).setAttribute("src",window.URL.createObjectURL(fileObj.files[0]));  
		            }  
		        }else{  
		            document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);  
		        }           
		    }else{  
		        alert("仅支持"+allowExtention+"为后缀名的文件!");  
		        fileObj.value="";//清空选中文件  
		        if(browserVersion.indexOf("MSIE")>-1){                          
		            fileObj.select();  
		            document.selection.clear();  
		        }                  
		        fileObj.outerHTML=fileObj.outerHTML;  
		    }  
		};
// 		function change_photo(){
// 	        PreviewImage($("input[name='file_upload']")[0], 'Img', 'Imgdiv');
// 	    };
    	$(function(){
//     		try{
// 				pluginInit();//扫描组件初始化
// 				PLUGIN.CloseDevice();//先执行一遍close
// 			}catch(err){
// 				console.log(err);
// 				$.messager.alert('', '请下载高拍仪插件,若已安装过，请浏览器点击右上角允许插件运行','',function(){
// 					window.location.href = "${pageContext.servletContext.contextPath}/etcs/npcamera-V3.6.zip";
// 				});
// 				return false;
// 			}
			var jScanEnv = '${jScanEnv}';
			if (jScanEnv != null && jScanEnv != '' && jScanEnv != 'null') {
				var jScanEnvObj = eval('(' + jScanEnv + ')');
				if (jScanEnvObj != null) {
					//本地路径
					baseImgPath = jScanEnvObj.BASE_IMG_PATH;
				}
			} else {
				$.messager.alert('', '读取扫描参数失败');
				return false;
			}
    		//主窗口
	    	$("#rawPicUploadDilog").dialog(
		    	{
					title : '图片上传',
					width : 512,
					height : 300,
					closed : false,
					cache : false,
					shadow : true, //显示阴影
					resizable : false, //不允许改变大小
					modal : true, //模式化窗口，锁定父窗口
					inline : true, //是否在父容器中，若不在会出现很多BUG
				}
			);
			$("#tipInfo").textbox({
				editable : false,
				multiline : true,
				height : 90,
				value : "  提示：选择一个或多个卷宗影像图片上传到服务器。上传前系统将自动识别影像文件中的二维码信息，并以该二维码来重命名该文件。如果影像文件已使用二维码命名，将忽略识别过程。二维码长度为18位，并以F（封面）、M（目录）或者Y（页面）字符结尾。",
				readonly : true
			});
			//文件选择框
			$("#file_upload").filebox({    
			    buttonText: '选择文件', 
			    buttonAlign: 'right',
			    height : 30,
			    multiple: true,
			    accept:"image/jpeg",
			    separator: ";",
			    required: true,
			});
			//上传按钮
			$("#doUploadBtn").linkbutton({
				iconCls:'icon-ok',
				onClick: function(){
					rawPicUploadOpt.doUpload();
				},
			});
    	});
    </script>
  </body>
</html>
