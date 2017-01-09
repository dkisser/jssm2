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
   	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'" style="text-align: left;padding-top: 5px;padding-left: 30px;">
			<div>上传失败的有： </div>
			<div>
				<form id="uploadErrorForm"  method="post">
					<input id="uploadErrorList" name="uploadErrorList" type="text">
				</form>
			</div>
		</div>
		<div data-options="region:'south'" style="height:50px;padding-top: 3px;text-align: center;">
			<a id="downloadError" href="javascript:void(0);" >下载</a> &nbsp;&nbsp;&nbsp;&nbsp;<a 
				id="closeErrorDialog" href="javascript:void(0);" >关闭</a>
		</div>
	</div>
    <script type="text/javascript">
    	var uploadErrorOpt = {
    		downloadErrorInfo : function(){
    			$("#uploadErrorForm").form('submit', { 
				    url: "${pageContext.servletContext.contextPath}/raw/picUploadErrorInfo.do",    
				    success:function(){
				    	$("#uploadErrorFiles").dialog("close");
				    }
				});
    		},
    		closeErrorDialog : function(){
    			$.messager.confirm('警告','关闭后将无法下载上传失败的文件信息，确定要关闭吗？',function(r){    
				    if (r){   
		    			$("#uploadErrorFiles").dialog("close");
				    }
				    return false;
				});
    		}
    	};
    	$(function(){
    		var uploadErrorData= "${uploadErrorData}";
//     		uploadErrorData = uploadErrorData.replace(/;/g, "\n");
			//多行文本框，显示所有上传失败的文件名
			$('#uploadErrorList').textbox({
				value: uploadErrorData,
				multiline: true,
				height: 200,
				width: 450,
				readonly: true
			});
// 			$('#uploadErrorList').textbox("textbox").css("ime-mode", "disabled");
			//下载按钮
			$('#downloadError').linkbutton({
			    iconCls: 'icon-save',
			    size: 'large',
			    onClick: function(){
			    	uploadErrorOpt.downloadErrorInfo();
			    }
			});
			//关闭按钮
			$('#closeErrorDialog').linkbutton({
			    iconCls: 'icon-cancel',
			    size: 'large',
			    onClick: function(){
			    	uploadErrorOpt.closeErrorDialog();
			    }
			});
    	});
    </script>
  </body>
</html>
