<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
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
<title>快速卷宗整理</title>
</head>
<body>
	<div id="ksWizardDialog">
	   	<form id="ksWizardForm" method="post">
	   		<table align="center" style="margin-top:85px;">
	   			<tr>
	   				<td>卷宗封面扫码: </td>
	   				<td><input id="fmewm" type="text" ></td>
	   			</tr>
	   			<tr>
	   				<td colspan="2"> &nbsp;</td>
	   			</tr>
	   			<tr>
	   				<td colspan="2" align="center">
				    	<a href="javascript:void(0);" id="doSearchBtn" >查找</a>
	   				</td>
	   			</tr>
	   		</table>
	   	</form>
	</div>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/ewm.js"></script>
	<script type="text/javascript">
		var currJzType = "${currJzType}";
		var ksWizardOpt = {
// 			clearCurrEwm : function(e){
// 				清空文本框
// 				$(e.data.target).textbox('clear');
// 				$(e.data.target).textbox('textbox').focus();
// 				return false;
// 			},
			selectCurrEwm : function(){
				$("#fmewm").textbox('textbox').select();
				return false;
			},
			searchCase : function(newValue){
				$.messager.progress();	// 显示进度条
				//点击查找按钮
				newValue = $.trim(newValue);
				var ewmCheck = isValidEwm(newValue,"F");
				if(ewmCheck == 102){
					$.messager.progress('close');
					$.messager.alert('','二维码长度必须为18位','info',function(){ksWizardOpt.selectCurrEwm();});
					return false;
				}
				if(ewmCheck == 103){
					$.messager.progress('close');
					$.messager.alert('','二维码头8位必须为日期字符串','info',function(){ksWizardOpt.selectCurrEwm();});
					return false;
				};
				if(ewmCheck == 104){
					$.messager.progress('close');
					$.messager.alert('','封面二维码必须以“F”结尾','info',function(){ksWizardOpt.selectCurrEwm();});
					return false;
				};
				$("#ksWizardForm").form('submit', {
					url: "${pageContext.servletContext.contextPath}/ajuan/checkFmEwm.do",
					onSubmit: function(param){
						var isValid = $(this).form('validate');
						if (!isValid){
							$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
							return isValid;	// 返回false终止表单提交
						}
						param.jzewm = newValue;
						param.currJzType =  currJzType;
					},
					success: function(data){
						$.messager.progress('close');	// 如果提交成功则隐藏进度条
						if(data == "" || data.indexOf("Exception") >= 0){
							$.messager.alert('','查找出错');
							return false;
						}
						var result = eval('(' + data + ')');
						if(result.msgInfo == "isEmpty"){
							$.messager.alert('','二维码不能为空');
						}else if(result.msgInfo == "ewmError"){
							$.messager.alert('','二维码对应的图片文件不存在');
						}else if(result.msgInfo == "typeError"){
							var zlType = "";
							if(currJzType == "ls"){
								zlType = "历史";
							} else if(currJzType == "js") {
								zlType = "即时";
							} else {
								$.messager.alert('错误','整理类型错误','warning');
								return false;
							}
							$.messager.alert('整理类型错误','本卷宗不是采用"'+zlType+'"卷宗方式进行整理的','warning',function(){
								ksWizardOpt.selectCurrEwm();
							});
						}else if(result.msgInfo == "newJz"){
							//新卷宗
							$.messager.confirm('确认','二维码“' + newValue + '”尚未创建卷宗。是否新建一个卷宗？',function(r){
							    if (r){
							    	$("#ksWizardDialog").dialog("close");
							        $('#center').panel('refresh', getContextPath() + "/ajuan/jzWizard.do?jzewm=" + newValue);
							    }else{
							    	ksWizardOpt.selectCurrEwm();
							    }
							});
						}else if(result.msgInfo == "ygd"){
							//已归档
							$('#center').panel('refresh', getContextPath() + "/ajuan/jzBrowse.do?hasdlg=''&jzEwm="+newValue);
						}else if(result.msgInfo == "wgd"){
							//未归档
							if(currJzType == "ls"){
								$('#center').panel('refresh',getContextPath() + "/ajuan/lsjzWizard_info.do?action=browse&jzewm="+ newValue);
							} else if(currJzType == "js") {
								$('#center').panel('refresh',getContextPath() + "/ajuan/jsjzWizard_info.do?action=browse&jzewm="+ newValue);
							}
						}
					}
				});
			}
		};
		$(function(){
			if(currJzType == ""){
				return false;
			}
			var title = "卷宗整理导航";
			if(currJzType == "ls"){
				title = "历史" + title;
			}else if(currJzType == "js"){
				title = "即时" + title;
			}
			//主窗口
			$("#ksWizardDialog").dialog({
				title : title,
				width : 512,
				height : 300,
				closed : false,
				cache : false,
				shadow : true, //显示阴影
				resizable : false, //不允许改变大小
				modal : true, //模式化窗口，锁定父窗口
				inline : true, //是否在父容器中，若不在会出现很多BUG
			});
			//封面二维码输入框
			$("#fmewm").textbox({    
			    width : 300,
				required: true,
				validType:'length[8,18]',
				onChange: function(newValue, oldValue){
					ksWizardOpt.searchCase(newValue);
				}
			});
			$("#fmewm").textbox("textbox").css("ime-mode", "disabled");//禁止中文输入法
			$("#fmewm").textbox('textbox').focus();
			//查找按钮
			$("#doSearchBtn").linkbutton({    
			    iconCls: 'icon-search',
			    onClick: function(){
			    	ksWizardOpt.searchCase($("#fmewm").textbox('getValue'));
			    }
			});
		});
	</script>
</body>
</html>
