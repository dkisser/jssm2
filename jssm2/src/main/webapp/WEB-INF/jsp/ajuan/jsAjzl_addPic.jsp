<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title></title>
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  </head>
 <body >
 <style type="text/css">
		.textbox-text {
		    border: 0 none;
		    border-radius: 5px;
		    font-size: 18px;
		    margin: 0;
		    outline-style: none;
		    padding: 4px;
		    resize: none;
		    vertical-align: top;
		    white-space: normal;
		    ime-mode: disabled;
		}
	</style>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/jqueryQtip/jquery.qtip.min.css"">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/jqueryQtip/jquery.qtip.min.js"></script>
	<div id="addPicDiv" class="easyui-layout" data-options="fit:true" >
   		<div data-options="region:'center'," style="padding:5px;">
   			<form id="addPicForm" method="post">
	    		<table align="center" cellspacing="10px" >
	    			
					<tr>
						<td align="right">二维码1: &nbsp;</td>
						<td><input class="picEwmTextBox"  name="addPicEwm0" id="addPicEwm0" style="width: 300px;" onblur="addPicOpt.ckeckCurrEwm();"
							 ></td>
						<td id="addPicEwmTip0" style="color: red">&nbsp;</td>
					</tr>
					
					<tr>
						<td align="right">二维码2: &nbsp;</td>
						<td><input class="picEwmTextBox"  name="addPicEwm1" id="addPicEwm1" style="width: 300px;"
							 ></td>
						<td id="addPicEwmTip1" style="color: red">&nbsp;</td>
					</tr>
					
					<tr>
						<td align="right">二维码3: &nbsp;</td>
						<td><input class="picEwmTextBox"  name="addPicEwm2" id="addPicEwm2" style="width: 300px;"
							 ></td>
						<td id="addPicEwmTip2" style="color: red">&nbsp;</td>
					</tr>
					
					<tr>
						<td align="right">二维码4: &nbsp;</td>
						<td><input class="picEwmTextBox"  name="addPicEwm3" id="addPicEwm3" style="width: 300px;"
							 ></td>
						<td id="addPicEwmTip3" style="color: red">&nbsp;</td>
					</tr>
					
					<tr>
						<td align="right">二维码5: &nbsp;</td>
						<td><input class="picEwmTextBox"  name="addPicEwm4" id="addPicEwm4" style="width: 300px;"
							 ></td>
						<td id="addPicEwmTip4" style="color: red">&nbsp;</td>
					</tr>
					
					<tr>
						<td align="right">二维码6: &nbsp;</td>
						<td><input class="picEwmTextBox"  name="addPicEwm5" id="addPicEwm5" style="width: 300px;"
							 ></td>
						<td id="addPicEwmTip5" style="color: red">&nbsp;</td>
					</tr>
					
					<tr>
						<td align="right">二维码7: &nbsp;</td>
						<td><input class="picEwmTextBox"  name="addPicEwm6" id="addPicEwm6" style="width: 300px;"
							 ></td>
						<td id="addPicEwmTip6" style="color: red">&nbsp;</td>
					</tr>
					
					<tr>
						<td align="right">二维码8: &nbsp;</td>
						<td><input class="picEwmTextBox"  name="addPicEwm7" id="addPicEwm7" style="width: 300px;"
							 ></td>
						<td id="addPicEwmTip7" style="color: red">&nbsp;</td>
					</tr>
				</table>
   			</form>
   		</div>
   		<div data-options="region:'south',split : false,border:false," style="width:100%;height: 49px">
   			<table  align="center">
   				<tr>
   					<td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="addPicOpt.addPicDialogSave();" 
			    		data-options="plain : true,iconCls:'icon-save',size:'large',
				        iconAlign:'left'" >保存</a>
				    </td>
				    <td>&nbsp;&nbsp;&nbsp;</td>
<!--    					<td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="addPicOpt.addPicFormClear();"  -->
<!-- 			    		data-options="plain : true,iconCls:'icon-clear',size:'large', -->
<!-- 				        iconAlign:'left'" >清空</a> -->
<!-- 				    </td> -->
				    <td>&nbsp;&nbsp;&nbsp;</td>
   					<td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="addPicOpt.addPicDialogClose();" 
			    		data-options="plain : true,iconCls:'icon-cancel',size:'large',
				        iconAlign:'left'" >关闭</a>
				    </td>
   				</tr>
   			</table>
   		</div>
   	</div>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/ewm.js"></script>
<script type="text/javascript">
var currAjEwm = '${currJadInfo.aj_ewm}';
var currMldm = '${currJadInfo.mldm}';
var currNode_id = '${currNode_id}';
// var successItemCount = 0;
//获得所有不为空的文本框数据,包含重复的元素
function getAllEwm(){
	var allEwms = []
	for(var i=0;i<=7;i++){
		var ewmValue = $.trim($("#addPicEwm" + i).textbox('getValue'));
		if(ewmValue!=null && ewmValue!='' && ewmValue!=undefined){
			allEwms.push(ewmValue);
		}
	}
	return allEwms;
};
//获得所有不为空的文本框数据,并且去掉重复的元素
function getAllUniEwm(){
	var allEwms = []
	for(var i=0;i<=7;i++){
		var ewmValue = $.trim($("#addPicEwm" + i).textbox('getValue'));
		if(ewmValue!=null && ewmValue!='' && ewmValue!=undefined && allEwms.indexOf(ewmValue) == -1){
			allEwms.push(ewmValue);
		}
	}
	return allEwms;
};
//判断数组是否重复
function isRepeat(arr){
	var hash = {};
	for(var i in arr) {
		arr[i] = $.trim(arr[i]);
		if(hash[arr[i]])
		return true;
		hash[arr[i]] = true;
	}
	return false;
};
//获取数组中指定元素的个数
function getEleCount(array,element){
	var count = 0;
	$.each(array, function(index, value){
		if(element == value){
			count = count + 1;
		}
	});
	return count;
};
// 最简单数组去重法
function uniqueArray(array){
  var n = []; //一个新的临时数组
  //遍历当前数组
  for(var i = 0; i < array.length; i++){
    //如果当前数组的第i已经保存进了临时数组，那么跳过，
    //否则把当前项push到临时数组里面
    if (n.indexOf(array[i]) == -1) n.push(array[i]);
  }
  return n;
};
//获得当前文本框的值
function getCurrVal(currId){
	return $.trim($(currId).textbox('getValue'));
};
//初始化所有textbox，单个二维码检查
function initAllEwmTb(i){
	//入参i为文本框编号（0~7）
	var textBoxId = "#addPicEwm" + i;
	var addPicEwmTipId = "#addPicEwmTip" + i;
	$(textBoxId).textbox({
		iconAlign:'right',
		icons:[{
			iconCls: 'icon-clear',
			handler: function(e){
				addPicOpt.clearCurrEwm(e);
		}}],
		onChange : function(newValue, oldValue){
			//验证二维码有效性
			$.ajax({
				url: "${pageContext.servletContext.contextPath}/ajuan/jsAjzlCheckEwm.do",
				async:true,		//是否异步请求
				cache:false,	//是否缓存
				type:"post",	//请求方式
				dataType:"text",	//返回数据类型
				data : {"ewm" : $.trim(newValue)},
		        beforeSend : function(){
		        	//加载图片loading.gif
		        	var imgUrl = "${pageContext.servletContext.contextPath}/js/easyui/themes/icons/loading.gif";
					var innerHtml = '<img style="height: 16px;width: 16px;" alt="" src="' + imgUrl + '">';
					$(addPicEwmTipId).html(innerHtml);
		        	//先判断二维码是否重复
		        	newValue = $.trim(newValue);
		        	if(newValue.length > 0){
		        		if(isValidEwm(newValue,"Y") != 101){
		        			$(addPicEwmTipId).html("二维码格式错误");
			        		return false;
		        		}
		        	} else {
		        		$(addPicEwmTipId).html("");
			        	return false;
		        	}
		        	ewmArray = getAllEwm();
		        	var count = getEleCount(ewmArray,newValue);
					if(count > 1){
						//出现重复二维码
						$(addPicEwmTipId).html("二维码重复");
						return false;
					}else{
						$(addPicEwmTipId).html(innerHtml);
					};
		        },
				success:function(data){
					if(data == '"notExsit"'){
						$(addPicEwmTipId).html("二维码不存在");
					}else if(data == '"beUsed"'){
						$(addPicEwmTipId).html("二维码已被使用");
					}else if(data == '"empty"'){
						$(addPicEwmTipId).html(" ");
					}else if(data = '"success"'){
						var imgUrl = "${pageContext.servletContext.contextPath}/js/easyui/themes/icons/ok.png";
						var innerHtml = '<img style="height: 16px;width: 16px;" alt="" src="' + imgUrl + '">';
						$(addPicEwmTipId).html(innerHtml);
					}
				},
				error : function(){
					$(addPicEwmTipId).html("系统异常");
				}
			});
		},
	});
	//鼠标悬浮预览
	$(textBoxId).textbox('textbox').qtip({
		 content : {
		        text: function(event, api) {
		        	if($.trim($(this).val()) != ""){
			        	$.ajax({
			                url: "ajuan/rawFile4Qtip.do",
			                data:{"currEwm":$.trim($(this).val())},
			            })
			            .then(function(content) {
			            	var newContent="<img alt='' width='250' heigth='353' src="+content.slice(1,-1)+">";
			                api.set('content.text', newContent);
			            }, function(xhr, status, error) {
			                api.set('content.text', '图片加载失败');
			            });
			            return '努力加载中...'; // Set some initial text
		        	} else {
		        		return false;
		        	}
		        }
		  },
		  show : {
			  effect : true,
			  delay: 1000,  
		  }
	 });
};

var addPicOpt = {
	clearCurrEwm : function(e){
		$(e.data.target).textbox('clear');
		$(e.data.target).textbox('textbox').focus();
		$(e.data.target).parent().next("td").html(" ");//删除警告信息
		return false;
	},
	addPicDialogSave : function(){
		//添加图片：保存按钮
		$.messager.progress();
		$('#addPicForm').form('submit',{
			url : "${pageContext.servletContext.contextPath }/ajuan/jsAjzlAddPic.do",
			onSubmit : function(param){
				var isValid = $(this).form('validate');
				if (!isValid){
					$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
					return isValid;	// 返回false终止表单提交
				}
				//得到所有要上传的二维码数组（不包含重复的）
				var allEwms = getAllUniEwm();
				//将所有二维码以分号连接起来
				var submitString = "";
				$.each(allEwms, function(index, value){
					submitString = submitString + $.trim(value) + ";";
				});
				//去掉末尾分号
				submitString = submitString.substring(0, submitString.length-1);
				param.addPicEwms = submitString;//将二维码连接字符串做为提交参数
				param.currNode_id = currNode_id;//当前节点id
				param.aj_ewm = currAjEwm;//当前案卷二维码
				param.mldm = currMldm;
				param.isls = "${isls}";
			},
			success: function(data){
				$.messager.progress('close');	// 如果提交成功则隐藏进度条
				if(data == '"success"'){
					$('#ajzlTree').tree('reload');//刷新树目录
					$('#lsajzlTree').tree('reload');//刷新树目录
					playTipAudioSuccess();//播放成功声音
					if(${isls=="true"}){
						$('#addPicDialog').dialog('refresh');
					}else{
						$('#addPicDialog').dialog('close');
					}
				}else{
					$.messager.alert('错误','添加失败','info');
					return false;
				}
			}
		});
	},
	addPicFormClear : function(){
		//清空二维码
// 		$('#addPicForm').form('clear');
// 		$('#addPicEwm0').textbox('textbox').focus();
		$('#addPicDialog').dialog('refresh');
	},
	addPicDialogClose : function(){
// 		addPicOpt.addPicDialogSave();
		//关闭添加图片对话框
		$('#addPicDialog').dialog('close');
	}
};
$(function(){
	for(var i=0;i<=7;i++){
		initAllEwmTb(i);
	}
	$("#addPicEwm0").textbox({
		required: true,
		validType:'length[8,18]',
	});
	//鼠标悬浮预览
	$("#addPicEwm0").textbox('textbox').qtip({
		 content : {
		        text: function(event, api) {
		        	if($.trim($(this).val()) != ""){
			        	$.ajax({
			                url: "ajuan/rawFile4Qtip.do",
			                data:{"currEwm":$.trim($(this).val())},
			            })
			            .then(function(content) {
			            	var newContent="<img alt='' width='250' heigth='353' src="+content.slice(1,-1)+">";
			                api.set('content.text', newContent);
			            }, function(xhr, status, error) {
			                api.set('content.text', '图片加载失败');
			            });
			            return '努力加载中...'; // Set some initial text
		        	} else {
		        		return false;
		        	}
		        }
		  },
		  show : {
			  effect:true,
			  delay: 1000,  
		  }
	 			  
	 });
	function getLastEwm(){
		var lastEwmValue = $.trim($("#addPicEwm7").textbox('getText'));
		return lastEwmValue;
	};
    //回车保存
	$("#addPicDiv").bind("keydown",function(e){
		var theEvent = e || window.event;    
		var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
		if (code == 13) {
// 			theEvent.preventDefault();
		    addPicOpt.addPicDialogSave();
	    }
	    if (code == 9) {
	    	var lastEwmValue = getLastEwm();
	    	if(lastEwmValue != ''){
			    addPicOpt.addPicDialogSave();
	    	}
	    }
	});
});
</script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/tip_audio.js"></script>
 </body>
</html>
