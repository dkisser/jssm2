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

</head>
<body >
<div id="jssmDialog">
	<div class="easyui-layout" style="width:100%;height:100%;">   
	    <div data-options="region:'west',split : false" style="width:103px;">
	    	<div border="false" class="easyui-accordion" style="width:100%;height:100%;padding-top: 3px;text-align: center;">
		    	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="StartDevice();" style="margin-top: 3px;"
		    		data-options="plain : false,iconCls:'icon-search',width:99,size:'large',
			        iconAlign:'left'" >启 &nbsp;动</a>
		    	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="DoCaptureImage();" style="margin-top: 3px;"
		    		data-options="plain : false,iconCls:'icon-search',width:99,size:'large',
			        iconAlign:'left'" >扫 &nbsp;描</a>
			    <a href="javascript:void(0);" class="easyui-linkbutton" onclick="CloseDevice();" style="margin-top: 3px;"
		    		data-options="plain : false,iconCls:'icon-search',width:99,size:'large',
			        iconAlign:'left'" >关&nbsp;闭 </a>
			    <a href="javascript:void(0);" class="easyui-linkbutton" onclick="getHistory();" style="margin-top: 3px;"
		    		data-options="plain : false,iconCls:'icon-search',width:99,size:'large',
			        iconAlign:'left'" >扫描记录</a>
	    	</div>
	    </div>   
	    <div data-options="region:'center'" style="padding:5px;background:#eee;">
	    	<div>
	    		<table>
	    			<tr>
	    				<td>图片存储目录: &nbsp;</td>
	    				<td><input class="easyui-textbox" id="defaultImgPath"
	    						data-options="iconCls:'icon-lock',readonly:true" style="width:300px"></td>
	    				<td style="color: red;font-weight: bolder;">&nbsp;请确保此路径下的文件夹存在，若不存在请先创建该文件夹</td>
	    			</tr>
	    		</table>
	    	</div>
	    	<br>
			<!--摄像显示embed type="application/codeproject" id="plugin"-->
			<div style="margin:0 auto;width: 100%;height: 86%">
				<embed type="application/npcamera" width=100% height=100% id="plugin">
			</div>
			<br>
			<div>
				<div style="float: left;width: 200px">
					<select id="CameraIndex" onchange="CameraChange()">
						<option value ="0">文档头</option>
<!-- 						<option value ="1">人像头</option> -->
					</select>
					<select id="sel" onchange="SetResolution()"></select>
				</div>
				<div style="float: left;">
					图片路径:<input id="imgPath" value="" readonly="true"  style="width:360px;" type="text" />
				</div>
			</div>
	    </div>   
	</div>
<div id="rawPreviewDialog" ></div>
</div>
<script type="text/javascript" id="shortcutKeyScript" src="${pageContext.servletContext.contextPath }/js/jssm/shortcutKey.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/ewm.js"></script>
<script type="text/javascript">
		//高拍仪脚本
		//getting the instance of the plugin so we can use it every time
		var PLUGIN = null;//that dosent like Chrome
		var isAuto = false;
		var isEditCut = false;
		var iDegree = 270;
		var index = 0;
		var baseImgPath = "D:\\DocImage\\";
		var scanDefaultCover = '1';//默认提示覆盖
		var imgPreview = '1';//默认预览图片
		var scanningModel;//扫描模式
		var scanInterval;//扫描时间间隔（秒）
		var autoScanFlag = true;//空格键控制扫描暂停
		var scanTimer;//自动扫描定时器
		var uploadUrl = getContextPath() + "/raw/rawUpload.do?userId=${sessionScope.loginInfo.userId}";//上传路径
		var rawTitle;//dialog原始标题
		//将时间格式化为2016-08-09的形式
		function formatterDate(date) {
			var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
			var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
			+ (date.getMonth() + 1);
			return date.getFullYear() + '-' + month + '-' + day;
		};
		//查询当天扫描历史
		function getHistory(){
			$.messager.progress();
			var today = formatterDate(new Date());
			$.ajax({
				url: "${pageContext.servletContext.contextPath}/raw/selectHistory.do",
		        async : true,
		        type : 'POST',
		        data : {startDate : today,endDate : today},//查询状态为2，指定二维码的文件是否存在
		        cache:false,
		        dataType:'text',//返回字符串
		        success : function(data){
		        	$.messager.progress('close');
		        	if(data == '\"success\"'){
						var url="${pageContext.servletContext.contextPath }/raw/showHistoryUI.do?startDate="+today
							+"&endDate="+today;
						$('#jssmDialog').dialog('destroy')
						$('#center').panel('refresh',url);
			    	}else{
			    		$.messager.alert('','今天还没有记录','info');
			    	}
		        },
		        error : function(){
		        	$.messager.progress('close');
		        	$.messager.alert('','今天还没有记录','info');
		        }
			});
		};
		//高拍仪初始化
		function pluginInit()
		{
			try
			{
				PLUGIN = document.getElementById("plugin");
				
				//document.getElementById("name").value = PLUGIN.Name;//getting it from WIN-API
			}
			catch (err) {console.log("2222"); alert(err);$.messager.progress('close'); }
		};
		//开启高拍仪
		function StartDevice()
		{
			try
			{	
				stopAutoScan();
				//if( CheckBrowser() ) return;
				var selObj = document.getElementById("sel");
				selObj.options.length = 0;
				var cameraIndexObj = document.getElementById("CameraIndex");
				var res = PLUGIN.StartDevice(cameraIndexObj.selectedIndex.toString());//we can also add numbers and strings (because of the plugin implementation)
				//alert(PLUGIN.GetResolutionByIndex(0));
				//穷举设备的分辨率
				
				var selObj = document.getElementById("sel");
				var index = 0;
				while(true)
				{
					var resolution = PLUGIN.GetResolutionByIndex(index.toString());
					//alert(resolution);
					if(resolution !== "")
					{
						var Option = document.createElement("OPTION");
						Option.value = index;
						Option.text = resolution;
						selObj.options.add(Option);
						index++;
					}
					else
					{
						break;
					}
				}
				RotateVideoByDegree();//旋转镜头
				PLUGIN.SetAutoCrop(true);//自动切边、矫正
			}
			catch (err) {console.log("333");  alert(err);$.messager.progress('close'); }
		};
		
		function RotateVideoByDegree()
		{
			PLUGIN.RotateVideoByDegree(iDegree.toString());//顺时针依次旋转iDegree角度，iDegree的取值只能是90、180、270.	
		};
		
		function DeletePicsByPath()
		{
			PLUGIN.DeletePicsByPath("F:\\测试");//删除目录下的所有文件，但是不删除本身的空目录
		};
		
		function SetResolution()
		{
			var selObj = document.getElementById("sel");
			var index = selObj.options.selectedIndex;
			PLUGIN.SetResolutionByIndex(index.toString());
		};
		
		function CameraChange()
		{
			StartDevice();
		};
		//停止自动扫描
		function stopAutoScan(){
			$.messager.progress('close');
			window.clearInterval(scanTimer);
			$('#jssmDialog').dialog('setTitle',rawTitle);
			autoScanFlag = true;
		}
		//点击扫描按钮
		function DoCaptureImage()
		{
			try{
				$.messager.progress();
				if(scanningModel == '2'){
					//自动扫描 scanInterval
					if(autoScanFlag){
						doAutoScan();
						scanTimer = setInterval("doAutoScan()",scanInterval*1000);
						$('#jssmDialog').dialog('setTitle',rawTitle+" 正在自动扫描中...");
						autoScanFlag = false;
					}else{
						stopAutoScan();
					}
				}else if(scanningModel == '1'){
					//手动扫描
					var codeEwm = GetEwmCode();
					if(codeEwm == null || codeEwm =='null'  || codeEwm ==''  || codeEwm == undefined){
						stopAutoScan();
						return false;
					}
					if(scanDefaultCover == '1'){//scanDefaultCover位2，则不提示是否覆盖
						//检查二维码是否存在
						$.ajax({
						        url: "${pageContext.servletContext.contextPath}/raw/findEwmInfo.do",
						        async : true,
						        type : 'POST',
						        data : {ewm : codeEwm},//查询状态为2，指定二维码的文件是否存在
						        cache:false,
						        dataType:'json',//返回json对象
						        success: function(data){
						        		$.messager.progress('close');
						        		if(data == null || data.ewm == null || data == undefined || data == ''){
						        			doCapture(codeEwm);
						        			return false;
						        		}
										//二维码存在
										$.messager.confirm('确认','二维码已存在,确定要重新扫描该图片吗？',function(r){
										    if (r){
										    	doCapture(codeEwm);
										    }else{
											    return false;
										    }
										});
						        	},
						        error : function(){
						        		$.messager.progress('close');
						        		$.messager.alert('警告','系统异常');
						        		return false;
						        	}
							    });
					}else{
						doCapture(codeEwm);
					}
				}else{
					$.messager.alert('', '读取扫描模式失败');
					return false;
				}
			}
			catch (err) {console.log("444"); alert(err);$.messager.progress('close');}
			
		};
		//自动扫描
		function doAutoScan(){
			$.messager.progress();
			var codeEwm = GetEwmCode();
			if(codeEwm == null || codeEwm =='null'  || codeEwm ==''  || codeEwm == undefined){
				stopAutoScan();
				return null;
			}
			//本地存储路径
			var localImgpath = baseImgPath +"\\"+ codeEwm + ".jpg";
			//扫描图片
			PLUGIN.CaptureImage(localImgpath);
			//'data:image/jpeg;base64,'console.log(resultData);
			index++;
			//显示存储目录
			$('#imgPath').val(localImgpath);
			//上传
			HttpSendFile(localImgpath);
		}
		
		//执行拍摄上传
		function doCapture(codeEwm){
			try{
				//本地存储路径
				var localImgpath = baseImgPath +"\\"+ codeEwm + ".jpg";
				//扫描图片
				PLUGIN.CaptureImage(localImgpath);
				//'data:image/jpeg;base64,'console.log(resultData);
				index++;
				//显示存储目录
				$('#imgPath').val(localImgpath);
				//上传
				HttpSendFile(localImgpath);
			}catch (err) {console.log("555"); alert(err);$.messager.progress('close');}
		};
		//http上传
		function HttpSendFile(localImgpath) {
			//参数0：待上传文件的绝对路径
			//参数1：服务器url
// 			document.getElementById("PATH").value = PLUGIN.HttpSendFile(document.getElementById("ARG0").value, document.getElementById("ARG1").value).toString();
			//文件上传及入库
			var responseData = PLUGIN.HttpSendFile(localImgpath,uploadUrl);
			$.messager.progress('close');
			if(responseData == null || responseData == '' || responseData == undefined || responseData.indexOf('uploadErrorMsg') > 0){
				$.messager.alert('警告','扫描失败');
				return false;
			}
			var data = eval('(' + responseData + ')');
			var currEwm = data.ewm;
			var currFileVersion = data.fileVersion;
			//判断是否预览图片，imgPreview为1表示预览
			if(imgPreview == '1'){
				//预览窗口
				$('#rawPreviewDialog').dialog({
					title : '扫描预览',
					width : '100%',
					height : '100%',
					closed : false,
					maximizable : false,
					cache : false,
					shadow : true, //显示阴影
					resizable : false, //不允许改变大小
					modal : true, //模式化窗口，锁定父窗口
					inline : true, //是否在父容器中，若不在会出现很多BUG
					onBeforeClose : function(){//关闭之前提示是否保存
						$.getScript("${pageContext.servletContext.contextPath }/js/jssm/shortcutKey.js");
					    var colseFlag=confirm("关闭后将删除图片，确定要关闭吗？");
					    if(colseFlag){
					    	//关闭时，删除已扫描的文件信息
					    	$.messager.progress();
							//删除文件
					        $.ajax({
						        url: "${pageContext.servletContext.contextPath}/raw/deleteRawFile.do",
						        async : true,
						        type : 'POST',
						        data : {ewm : currEwm},
						        cache:false,
						        dataType:'json',
						        success: function(data){
						        	$.messager.progress('close');
						        },
						        error : function(){
						        	$.messager.progress('close');
						        	$.messager.alert('','删除失败');
						        }
						    });
					    }else{
					    	//取消，则不关闭
	       					return false;
					    }
					},
					href : '${pageContext.servletContext.contextPath}/raw/rawPreviewUI.do?currEwm=' + currEwm +'&currFileVersion='+currFileVersion
				});
			}else{
				$.messager.progress();
				//直接保存文件，跳过预览
		        $.ajax({
			        url: "${pageContext.servletContext.contextPath}/raw/confirmRawFile.do",
			        async : true,
			        type : 'POST',
			        data : {ewm : currEwm},
			        cache:false,
			        dataType:'text',
			        beforeSend : function(){
			        	if(!currEwm){
			        		playTipAudioError();//播放失败声音
			        		$.messager.alert('','无法解析二维码');
			        		return false;
			        	}
			        },
			        success: function(data){
			        	$.messager.progress('close');
			        	if(data == '\"success\"'){
			        		//加载快捷键
			        		$.getScript("${pageContext.servletContext.contextPath }/js/jssm/shortcutKey.js");
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
		};
		//自动切边
		function SetAutoCrop()
		{
			try{
				PLUGIN.SetAutoCrop(!isAuto);//or false
				isAuto = !isAuto;
			}
			catch (err) {console.log("666"); alert(err);$.messager.progress('close');}
			
		};
		//关闭		
		function CloseDevice()
		{
			try{
				stopAutoScan();
				PLUGIN.CloseDevice();
				var selObj = document.getElementById("sel");
				selObj.options.length = 0;
			}
			catch (err) {console.log("777"); alert(err);$.messager.progress('close');}
		};
		//读取二维码
		function GetEwmCode()
		{
			try{
				//获取二维码,
				//{"Code":[{"QR-Code":"2016070700002"},{"QR-Code":"2016062200139"}]}
				//{"Code":[{"QR-Code":"http://weixin.qq.com/r/GHZKYIDAL3MQA12UkLE1x_orQY4234RQx9yXMLOK"}]}
				var code1 = PLUGIN.GetBarCode();
				if(code1 ==  undefined){
					$.messager.alert('警告','请先启动高拍仪');
					stopAutoScan();
					return null;
				}
				if(code1 == null || code1 ==''){
					$.messager.alert('警告','读取二维码失败');
					stopAutoScan();
					return null;
				}
				code1 = code1.toString();
				//必须将QR-Code换成QR_Code，因为变量名称只能是数字字母下划线，不允许有-，正则表达式加上‘g’表示匹配全部
				code1 = code1.replace(/QR-Code/g, "QR_Code");
				var dataObj=eval("("+code1+")");
				var codeEwm = null;
				//可能存在多个条码（包含二维码、条形码等）
				$.each(dataObj.Code,function(index,item){
					//遍历所有二维码对象
					var ewmData = item.QR_Code;//只获取二维码
					//验证二维码为18位，前8位是yyyyMMdd格式的字符串，并且不包含http字符串
					if(isValidEwm(ewmData) == 101 && ewmData.indexOf("http") == -1 ){
						codeEwm = ewmData;
						return false;//相当于break,跳出循环
					}
				});
				if(codeEwm == null || codeEwm =='null'  || codeEwm ==''  || codeEwm == undefined){
					playTipAudioError();//播放失败声音
					$.messager.alert('警告','读取二维码失败');
					stopAutoScan();
					return null;
				}else{
					return codeEwm;
				}
			}
			catch (err) {console.log("11111");alert(err);$.messager.progress('close');}
		};

	$(function() {
		try{
			pluginInit();//扫描组件初始化
			PLUGIN.CloseDevice();//先执行一遍close
		}catch(err){
			$.messager.alert('', '请下载高拍仪插件,若已安装过，请浏览器点击右上角允许插件运行','',function(){
				window.location.href = "${pageContext.servletContext.contextPath}/etcs/npcamera-V3.6.zip";
			});
			return false;
		}
		var jScanEnv = '${jScanEnv}';
		if (jScanEnv != null && jScanEnv != '' && jScanEnv != 'null') {
			var jScanEnvObj = eval('(' + jScanEnv + ')');
			if (jScanEnvObj != null) {
				//设置旋转角度
				iDegree = jScanEnvObj.SCAN_IDEGREE;
				//本地路径
				baseImgPath = jScanEnvObj.BASE_IMG_PATH;
				//是否覆盖
				scanDefaultCover = jScanEnvObj.SCAN_DEFAULT_COVER;
				//是否预览图片
				imgPreview = jScanEnvObj.IMG_PREVIEW;
				//扫描模式
				scanningModel = jScanEnvObj.SCANNING_MODEL;
				//扫描时间间隔
				scanInterval = jScanEnvObj.SCAN_INTERVAL;
				scanInterval = parseFloat(scanInterval);
				if(isNaN(scanInterval)){
					$.messager.alert('', '读取扫描间隔时间失败');
					return false;
				}
			}
		} else {
			$.messager.alert('', '读取扫描参数失败');
			return false;
		}
		//显示存储目录
		$('#defaultImgPath').val(baseImgPath);
		var title = '即时扫描';
		if(scanningModel == '1'){
			title = title + '(手动扫描)';
		}else if(scanningModel == '2'){
			title = title + '(自动扫描,时间间隔为'+scanInterval+'秒)';
		}else{
			$.messager.alert('', '读取扫描模式失败');
			return false;
		}
		rawTitle = title;
		//开启扫描界面dialog
		$('#jssmDialog').dialog({
			title : title,
			width : 1024,
			height : 650,
			closed : false,
			cache : false,
			shadow : true, //显示阴影
			resizable : false, //不允许改变大小
			modal : true, //模式化窗口，锁定父窗口
			onBeforeClose : function() {
				$.getScript("${pageContext.servletContext.contextPath }/js/jssm/shortcutKeyDisable.js");
				//窗口关闭时，关闭高拍仪
				CloseDevice();//先执行一遍close
			},
			onBeforeDestroy : function() {
				$.getScript("${pageContext.servletContext.contextPath }/js/jssm/shortcutKeyDisable.js");
				//窗口销毁时，关闭高拍仪
				CloseDevice();//先执行一遍close
			},
			inline : true, //是否在父容器中，若不在会出现很多BUG
		});
	});
</script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/tip_audio.js"></script>
</body>
</html>
