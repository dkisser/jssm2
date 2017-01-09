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
 <body>
	<div class="easyui-layout" data-options="fit:true" >
   		<div data-options="region:'center'," style="padding:5px;">
   			<form id="addPicLabelForm" method="post">
	    		<table align="center" cellspacing="10px" >
					<tr>
						<td align="right">标签名称: &nbsp;</td>
						<td>
							<input id="picLabelName" style="width:300px">
						</td>
						<td id="picLabelNameTip" style="color: red">&nbsp;</td>
					</tr>
				</table>
   			</form>
   		</div>
   		<div data-options="region:'south',split : false,border:false," style="width:100%;height: 49px">
   			<table  align="center">
   				<tr>
   					<td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="addPicLabelOpt.addPicLabSave();" 
			    		data-options="plain : true,iconCls:'icon-save',size:'large',
				        iconAlign:'left'" >保存</a>
				    </td>
				    <td>&nbsp;&nbsp;&nbsp;</td>
   					<td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="addPicLabelOpt.addPicLabClose();" 
			    		data-options="plain : true,iconCls:'icon-cancel',size:'large',
				        iconAlign:'left'" >关闭</a>
				    </td>
   				</tr>
   			</table>
   		</div>
   	</div>
   	<script type="text/javascript">
   	var checkedNodeIds = '${checkedNodeIds}';
	var currAjEwm = '${ajzlAPLabJad.aj_ewm}';
	var currMldm = '${ajzlAPLabJad.mldm}';
   	var addPicLabelOpt = {
   		addPicLabClose : function(){
   			//关闭
   			$("#addPicLabelDialog").dialog('close');
   			$('#lsajzlTree').tree('reload');//刷新树目录
			$('#picLabelCurrName').combobox('reload');//重新加载下拉框
   		},
   		addPicLabSave : function(){
   			//保存
   			$.messager.progress();	// 显示进度条
			$('#addPicLabelForm').form('submit', {
				url: "${pageContext.servletContext.contextPath }/ajuan/ajzlSavePicLab.do",
				onSubmit: function(param){
					var zml = $.trim($('#picLabelName').combobox('getText'));
					if(zml == null || zml == ''){
						$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
						$.messager.alert('错误','标签名称不能为空','warning');
						return false;
					}
					param.nodeIds = checkedNodeIds;
					param.aj_ewm = currAjEwm;
					param.zml = zml;
				},
				success: function(data){
					$.messager.progress('close');	// 如果提交成功则隐藏进度条
					if(data == '"success"'){
						playTipAudioSuccess();//播放成功声音
						addPicLabelOpt.addPicLabClose();
					}else{
						$.messager.alert('错误','添加标签失败','info');
						return false;
					}
				}
			});
   		}
   	};
   	$(function(){
   		//标签下拉框
		$('#picLabelName').combobox({
			valueField: 'id',
			textField: 'text',
			editable : true,
			required: true,
			url : "${pageContext.servletContext.contextPath }/ajuan/lsAjzlGetAllLab.do",
			queryParams: {
				"aj_ewm" : currAjEwm,
				"mldm" : currMldm
			},
		});
   	});
   	</script>
   	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/tip_audio.js"></script>
 </body>
</html>
