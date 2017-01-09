<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

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
    <div id="scanModelDialog" style="text-align: center;">
    	<div class="easyui-layout" style="width:100%;height:100%;">
    		<div data-options="region:'center'," style="padding:5px;padding-top: 25px">
	    		<form id="scanModelForm" method="post">
		    		<table align="center" style="width: 300px;">
						<tr>
	    					<td>
	    						<div style="text-align: center;"><input id="manualScan" type="radio" onclick="scanModelOpt.manualScanChecked();" 
	    							 name="scanningModel" value="1"><label for="manualScan">手动扫描</label></input></div>
						    </td>
	    					<td>
	    						<div style="text-align: center;"><input id="autoScanYes" type="radio" onclick="scanModelOpt.autoScanYesChecked();" 
	    							 name="scanningModel" value="2"><label for="autoScanYes">自动扫描</label></input></div>
						    </td>
	    				</tr>
	    				<tr>
		    				<td colspan="3"> &nbsp;</td>
		    			</tr>
	    				<tr>
		    				<td colspan="3">
		    					<span>&nbsp;说明： </span><span id="manualScanExplain" 
		    						style="display: inline-block;">每次扫描时，都要点击扫描按钮</span>
		    					<span id="autoScanYesExplain" style="display:none;">点击扫描按钮后，将每间隔指定时间自动扫描一次，可以点击空格键暂停</span>
		    				</td>
		    			</tr>
					</table>
				</form>
    		</div>
    		<div data-options="region:'south',split : false,border:false," style="width:100%;height: 49px">
    			<table  align="center">
    				<tr>
    					<td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="scanModelOpt.save();" 
				    		data-options="plain : true,iconCls:'icon-save',size:'large',
					        iconAlign:'left'" >确定</a>
					    </td>
    				</tr>
    			</table>
    		</div>
    	</div>
    </div>
    <div id="scanModelDialogTb">
    	<span>当前模式： </span><span id="currScanModel" style="font-weight: bolder;"></span>
    </div>
    <script type="text/javascript">
    	var scanningModel = '1';
    	var scanModelOpt = {
    		manualScanChecked : function(){
    			$("#manualScanExplain").show();
    			$("#autoScanYesExplain").hide();
    		},
    		autoScanYesChecked : function(){
    			$("#autoScanYesExplain").show();
    			$("#manualScanExplain").hide();
    		},
    		save : function(){
    			//$('input:radio[name=scanningModel]:checked').val();
    			var msg = '确定要选择 <strong>\"' + $("input[name='scanningModel']:checked").next("label").text() + '\"</strong> 模式吗？';
    			$.messager.confirm('模式设置', msg ,function(r){
				    if (r){
				    	var scanningModel = $('input:radio[name=scanningModel]:checked').val();
				    	if(scanningModel == '1'){
				    		//手动扫描
				    		var url="${pageContext.servletContext.contextPath }/raw/manualScanParamUI.do?scanningModel="+scanningModel;
							$('#center').panel('refresh',url);
				    		$('#scanModelDialog').dialog('close');
				    	}else if(scanningModel == '2'){
				    		//自动扫描
				    		var url="${pageContext.servletContext.contextPath }/raw/autoScanParamUI.do?scanningModel="+scanningModel;
							$('#center').panel('refresh',url);
				    		$('#scanModelDialog').dialog('close');
				    	}
				    	return false;
				    }else{
					    return false;
				    }
				});
		    }
    	};
    	$(function(){
    		//读取并解析参数
    		var jScanEnv = '${jScanEnv}';
			if (jScanEnv != null && jScanEnv != '' && jScanEnv != 'null') {
				var jScanEnvObj = eval('(' + jScanEnv + ')');
				if (jScanEnvObj != null) {
					//扫描模式
					scanningModel = jScanEnvObj.SCANNING_MODEL;
				}else{
					$.messager.alert('', '读取扫描参数失败');
					return false;
				}
			} else {
				$.messager.alert('', '读取扫描参数失败');
				return false;
			}
			//设置默认选中项
			if(scanningModel == '1'){
				//手动扫描
				$('#currScanModel').html('手动扫描');
				$('#manualScan').attr('checked',true);
				scanModelOpt.manualScanChecked();
			}else{
				//自动扫描
				$('#currScanModel').html('自动扫描');
				$('#autoScanYes').attr('checked',true);
				scanModelOpt.autoScanYesChecked();
			}
			
	    	$('#scanModelDialog').dialog({
				title : '扫描模式设置',
				width : 360,
				height : 230,
				closed : false,
				cache : false,
				shadow : true, //显示阴影
				resizable : false, //不允许改变大小
				modal : true, //模式化窗口，锁定父窗口
				inline : true, //是否在父容器中，若不在会出现很多BUG
				toolbar: "#scanModelDialogTb"
			});
    	});
    </script>
  </body>
</html>
