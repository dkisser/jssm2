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
    <div id="scanParmDialog" style="text-align: center;">
    	<div class="easyui-layout" style="width:100%;height:100%;">
    		<div data-options="region:'center'," style="padding:5px;padding-top: 15px">
    		<form id="scanParmForm" method="post">
	    		<table align="center">
					<tr>
						<td align="right">图片存储目录: &nbsp;</td>
						<td><input class="easyui-textbox"  value="" name="baseImgPath" id="baseImgPath"
								data-options="iconCls:'icon-filter',readonly:false,required:true" style="width:300px"></td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td align="right">镜头旋转角度: &nbsp;</td>
						<td>
							<input id="scanIdegree" name="scanIdegree"  style="width:300px">
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td align="right">扫描自动覆盖: &nbsp;</td>
						<td>
							<input id="scanDefaultCover" name="scanDefaultCover"  style="width:300px">
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td align="right">是否预览图片: &nbsp;</td>
						<td>
							<input id="imgPreview" name="imgPreview"  style="width:300px">
						</td>
					</tr>
				</table>
			</form>
    		</div>
    		<div data-options="region:'south',split : false,border:false," style="width:100%;height: 49px">
    			<table  align="center">
    				<tr>
    					<td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="scanParmOpt.save();" 
				    		data-options="plain : true,iconCls:'icon-save',size:'large',
					        iconAlign:'left'" >保存</a>
					    </td>
					    <td>&nbsp;&nbsp;&nbsp;</td>
    					<td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="scanParmOpt.reset();" 
				    		data-options="plain : true,iconCls:'icon-redo',size:'large',
					        iconAlign:'left'" >重置</a>
					    </td>
					    <td>&nbsp;&nbsp;&nbsp;</td>
    					<td><a href="javascript:void(0);" class="easyui-linkbutton" onclick="scanParmOpt.close();" 
				    		data-options="plain : true,iconCls:'icon-cancel',size:'large',
					        iconAlign:'left'" >关闭</a>
					    </td>
    				</tr>
    			</table>
    		</div>
    	</div>
	    
    </div>
    <script type="text/javascript">
    	var iDegree = 270;//旋转角度
    	var baseImgPath = 'D:\\DocImg\\';//存储路径
    	var scanDefaultCover = 1;//是否自动覆盖
		var imgPreview = 1;//是否预览图片
    	var scanningModel;//扫描模式
    	var scanInterval;//扫描时间间隔（秒）
   		var scanParmOpt = {
   			//保存按钮
   			save : function(){
   				$.messager.progress();
   				$('#scanParmForm').form('submit', {
				    url:"${pageContext.servletContext.contextPath }/raw/scanParamSave.do",    
				    onSubmit: function(param){
				    	var str = confirm('您确认想要保存此次设置吗?');
				    	if(str){
				    		//提交扫描模式
				    		param.scanningModel = scanningModel;
				    		//提交默认扫描间隔
				    		param.scanInterval = scanInterval;
				    		var isValid = $(this).form('validate');
							if (!isValid) {
								$.messager.progress('close');
								$.messager.alert('','请按照要求填写表单','info');
								return isValid;
							}
				    	}else{
				    		$.messager.progress('close');
				    		return false;
				    	}
				    },
				    success:function(data) {
				    	$.messager.progress('close');
				    	if(data==null || data ==''){
				    		$.messager.alert('','保存成功','info',function(){
// 		        				var url="${pageContext.servletContext.contextPath }/raw/manualScanParamUI.do?scanningModel="+scanningModel;
					    		$('#scanParmDialog').dialog('close');
// 								$('#center').panel('refresh',url);
		        			});
				    	}else{
				    		$.messager.alert('','保存失败','info');
				    	}
				    }
				});
   			},
   			//重置
   			reset : function(){
   				$.messager.confirm('','确定要把所有参数重置为默认值吗？',function(r){
				    if (r){
				    	$.messager.progress();
				    	$.ajax({
					        url: "${pageContext.servletContext.contextPath}/raw/scanParamReset.do",
					        async : true,
					        data : {"scanningModel":scanningModel},
					        type : 'POST',
					        cache:false,
					        dataType:'text',//返回文本
					        success: function(data){
					        		$.messager.progress('close');
					        		if(data == '\"success\"'){
					        			$.messager.alert('','重置成功','',function(){
					        				var url="${pageContext.servletContext.contextPath }/raw/manualScanParamUI.do?scanningModel="+scanningModel;
								    		$('#scanParmDialog').dialog('close');
											$('#center').panel('refresh',url);
					        			});
					        		}else{
					        			$.messager.alert('','重置失败');
					        		}
					        	},
					        error : function(){
					        		$.messager.alert('','重置失败');
					        	}
						});
				    }else{
					    return false;
				    }
				}); 
   			},
   			close : function(){
   				$('#scanParmDialog').dialog('close');
   			}
   		};
    	$(function(){
    		var jScanEnv = '${jScanEnv}';
			if (jScanEnv != null && jScanEnv != '' && jScanEnv != 'null') {
				var jScanEnvObj = eval('(' + jScanEnv + ')');
				if (jScanEnvObj != null) {
					//扫描模式为1
					scanningModel = jScanEnvObj.SCANNING_MODEL;
					if(scanningModel != '1'){
						$.messager.alert('', '读取扫描模式出错');
						return false;
					}
					//设置旋转角度
					iDegree = jScanEnvObj.SCAN_IDEGREE;
					//本地路径
					baseImgPath = jScanEnvObj.BASE_IMG_PATH;
					//是否覆盖
					scanDefaultCover = jScanEnvObj.SCAN_DEFAULT_COVER;
					//是否预览图片
					imgPreview = jScanEnvObj.IMG_PREVIEW;
					//扫描时间间隔
					scanInterval = jScanEnvObj.SCAN_INTERVAL;
				}
			} else {
				$.messager.alert('', '读取扫描参数失败');
				return false;
			}
			//显示存储目录
			$('#baseImgPath').val(baseImgPath);
			//初始化覆盖提示下拉框
			$('#scanDefaultCover').combobox({
				valueField: 'value',
				textField: 'key',
				editable : false,//不可编辑
				data: [{
					key: '扫描同一张图片时，提示是否覆盖',
					value: 1
				},{
					key: '扫描同一张图片时，不做提示直接覆盖',
					value: 2
				}
				],
			});
			//旋转角度下拉框
			$('#scanIdegree').combobox({
				valueField: 'value',
				textField: 'key',
				editable : false,//不可编辑
				data: [{
					key: '0',
					value: 0
				},{
					key: '90',
					value: 90
				},{
					key: '180',
					value: 180
				},
				{
					key: '270',
					value: 270
				}
				],
			});
			//是否预览
			$('#imgPreview').combobox({
				valueField: 'value',
				textField: 'key',
				editable : false,//不可编辑
				data: [{
					key: '扫描完成后，预览图片',
					value: 1
				},{
					key: '扫描完成后，不预览图片',
					value: 2
				}
				],
			});
			$('#scanDefaultCover').combobox('select',scanDefaultCover);
			$('#scanIdegree').combobox('select',iDegree);
			$('#imgPreview').combobox('select',imgPreview);
	    	$('#scanParmDialog').dialog({
				title : '手动扫描参数设置',
				width : 460,
				height : 300,
				closed : false,
				cache : false,
				shadow : true, //显示阴影
				resizable : false, //不允许改变大小
				modal : true, //模式化窗口，锁定父窗口
				inline : true, //是否在父容器中，若不在会出现很多BUG
			});
    	});
    </script>
  </body>
</html>
