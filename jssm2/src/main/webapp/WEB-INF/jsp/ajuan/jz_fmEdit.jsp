<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'jzNew_fm.jsp' starting page</title>
    	<meta charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/flexslider/flexslider.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/fullscreenslides/css/fullscreenstyle.css" />
		<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>


  </head>
  
 <body>
 <style type="text/css">
	body {
		margin-left: 0px;
		margin-top: 0px;
		margin-right: 0px;
		margin-bottom: 0px;
	}
	.tlbfromdata{
		margin-left:40px;
	}
	.fMainData{
		padding-top: 20px;
	}
	
</style>
		<div class="easyui-layout" fit="true">   
		    <div data-options="region:'south'" style="height:50px;padding:auto">
		    	<a id="btnjzNew_fmAdd" class="easyui-linkbutton" data-options="iconCls:'icon-ok',size:'large'" style="margin-left: 120px;margin-top: 5px;font-size: 20">确认修改</a> 
		    	<a id="btneditfmcancle" class="easyui-linkbutton" data-options="iconCls:'icon-clear',size:'large'" style="margin-left: 20px;margin-top: 5px;font-size: 20">跳过此步骤</a>
		    </div>   
		    <div data-options="region:'center'">
			    <form class="fMainData" id="formjzNew_fmMain">
					<table class="tlbfromdata">
						<tr>
							<td style="text-align: right">卷宗名称:</td>
							<td colspan="3"><input id="tbjzNew_fmJZMC" type="text" name="jzmc"></td>
							<td style="color:#f00;text-align: left" >*</td>
						</tr>
						<tr>
							<td style="text-align: right">卷宗类型:</td>
							<td colspan="3"><input id="cbjzNew_fmJZLX" type="text" name="jzlx"></td>
							<td style="color:#f00;text-align: left">*</td>
						</tr>
						<tr>
							<td style="text-align: right">审判程序:</td>
							<td><input id="cbjzNew_fmSPCX1" type="text" style="width:60px" name="spcx1"></td>
							<td width="5px;"></td>
							<td style="text-align: right">审判结果:裁定</td>
							<td><input id="cbjzNew_fmSPJG1" type="text" style="width:140px" name="spjg1"></td>
						</tr>
						<tr>
							<td style="text-align: right">审判程序:</td>
							<td><input id="cbjzNew_fmSPCX2" type="text" style="width:60px" name="spcx2"></td>
							<td width="5px;"></td>
							<td style="text-align: right">审判结果:裁定</td>
							<td><input id="cbjzNew_fmSPJG2" type="text" style="width:140px" name="spjg2"></td>
						</tr>
						<tr>
							<td style="text-align: right">保密级别:</td>
							<td colspan="3"><input id="cbjzNew_fmBMJB" name="jbdm"></td>
							<td style="color:#f00;text-align: left">*</td>
						</tr>
					</table>		
				</form>
		    </div>   
		</div>  
	
		
	
	<script type="text/javascript">
	
		var flagcombox1 = false;
		var flagcombox2 = false;
		var flagcombox1_s = false;
		var flagcombox2_s = false;
		
		if('${jzfm.cx1}')flagcombox1 = true;
		if('${jzfm.cx2}')flagcombox2 = true;
		if('${jzfm.jg1}')flagcombox1_s = true;
		if('${jzfm.jg2}')flagcombox2_s = true;
				
				
		$('#tbjzNew_fmJZMC').textbox({    
			prompt:'请输入案件名称（必填）',
			value:'${jzfm.jzmc}',
			required:true,
			width:"100%",
		});
		
		$('#cbjzNew_fmJZLX').combobox({ 
			valueField: 'text',
			textField: 'text',
			required:true,
			editable:false,
		    url: "${pageContext.servletContext.contextPath }/ajuan/getJZLX.do",
			panelHeight:'auto',
			onLoadSuccess: function(){
				$('#cbjzNew_fmJZLX').combobox('select','${jzfm.jzlx}');
			}
		});
		
		$('#cbjzNew_fmSPCX1').combobox({ 
			valueField: 'text',
			textField: 'text',
			panelHeight:'auto',
			editable:false,
			url:"${pageContext.servletContext.contextPath }/ajuan/getSPCX.do",
			onSelect:function(record){
				if(record.id=="0"){
					$('#cbjzNew_fmSPCX2').combobox('select','空');
					$('#cbjzNew_fmSPJG1').combobox({disabled:true,});
					$('#cbjzNew_fmSPCX2').combobox({disabled:true,});
					$('#cbjzNew_fmSPJG2').combobox({disabled:true,});
					flagcombox1 = false;
					flagcombox2 = false;
				}else{
					$('#cbjzNew_fmSPJG1').combobox({disabled:false,});
					$('#cbjzNew_fmSPCX2').combobox({disabled:false,});
					flagcombox1 = true;
				}
			},
			onLoadSuccess: function(){
			if('${jzfm.cx1}'){
					$('#cbjzNew_fmSPCX1').combobox('select','${jzfm.cx1}');
					$('#cbjzNew_fmSPJG1').combobox({disabled:false,});
					$('#cbjzNew_fmSPCX2').combobox({disabled:false,});
					flagcombox1 = true;
				}
			}
		});
		$('#cbjzNew_fmSPCX2').combobox({ 
			valueField: 'text',
			textField: 'text',
			panelHeight:'auto',
			disabled:true,
			editable:false,
			url:"${pageContext.servletContext.contextPath }/ajuan/getSPCX.do",
			onSelect:function(record){
				if(!record.id){
					alert();
				}
				if(record.id=="0"){
					$('#cbjzNew_fmSPJG2').combobox({disabled:true,});
					flagcombox2 = false;
				}else{
					$('#cbjzNew_fmSPJG2').combobox({disabled:false,});
					flagcombox2 = true;
				}
			},
			onLoadSuccess: function(){
			if('${jzfm.cx2}'){
					$('#cbjzNew_fmSPCX2').combobox('select','${jzfm.cx2}');
					$('#cbjzNew_fmSPJG2').combobox({disabled:false,});
					flagcombox2 = true;
				}
			}
		});
		$('#cbjzNew_fmSPJG1').combobox({ 
			valueField: 'text',
			textField: 'text',
			panelHeight:'auto',
			url:"${pageContext.servletContext.contextPath }/ajuan/getSPJG.do",
			disabled:true,
			onSelect:function(){
				flagcombox1_s = true;
			},
			onLoadSuccess: function(){
			if('${jzfm.jg1}'){
					$('#cbjzNew_fmSPJG1').combobox('select','${jzfm.jg1}');
					flagcombox1_s = true;
				}
			}
		});
		$('#cbjzNew_fmSPJG2').combobox({ 
			valueField: 'text',
			textField: 'text',
			panelHeight:'auto',
			url:"${pageContext.servletContext.contextPath }/ajuan/getSPJG.do",
			disabled:true,
			onSelect:function(){
				flagcombox2_s = true;
			},
			onLoadSuccess: function(){
				if('${jzfm.jg2}'){
					$('#cbjzNew_fmSPJG2').combobox('select','${jzfm.jg2}');
					flagcombox2_s = true;
				}
			}
		});
		
		
		$('#cbjzNew_fmBMJB	').combobox({ 
			valueField: 'text',
			textField: 'text',
			panelHeight:'auto',
			url:"${pageContext.servletContext.contextPath }/ajuan/getAQJB.do",
			width:"100%",
			required:true,
			editable:false,
			onLoadSuccess: function(){
				$('#cbjzNew_fmBMJB').combobox('select','${jzfm.aqjb}');
			}
		});
		
		$('#btnjzNew_fmAdd').bind('click', function(){
			$.messager.progress();	// 显示进度条
			return buttoncilck();
    	});
    	
    	$('#btneditfmcancle').bind('click',function(){
    		$("#jzfmEdit").dialog('close');
    	
    	});
    	
    	$("#jzfmEdit").bind("keydown",function(e){
			var theEvent = e || window.event;    
			var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
			//code == 9   
			if (code == 13) {   
				buttoncilck();
			}
		});
		
		
		
		
		
		
		
		function buttoncilck(){
			var textcombo = $('#cbjzNew_fmSPJG1').combobox('getValue');
			if(flagcombox1){
				if(!flagcombox1_s||!textcombo){
					var comboxresult = $('#cbjzNew_fmSPJG1').combobox('getText');
					if(!comboxresult){
						$.messager.progress('close');
						$.messager.alert('错误','请选择或输入审判结果1');
						return;
					}
				}
			}
			textcombo = $('#cbjzNew_fmSPJG2').combobox('getValue');
			if(flagcombox2){
				if(!flagcombox2_s||!textcombo){
					var comboxresult = $('#cbjzNew_fmSPJG2').combobox('getText');
					if(!comboxresult){
						$.messager.progress('close');
						$.messager.alert('错误','请选择或输入审判结果2');
						return;
					}
				}				
			}
			$('#formjzNew_fmMain').form('submit', {
				url: "${pageContext.servletContext.contextPath }/ajuan/jzfmEdit.do",
				onSubmit: function(param){
        			param.jzewm = '${jzewm}'; 
        			param.txspjg1 = $.trim($('#cbjzNew_fmSPJG1').combobox('getText'));
        			param.txspjg2 = $.trim($('#cbjzNew_fmSPJG2').combobox('getText'));
					var isValid = $(this).form('validate');
					if (!isValid){
						$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
					}
					return isValid;	// 返回false终止表单提交
				},
				success: function(data){
						$.messager.progress('close');
						if(JSON.parse(data) == "success")
							$("#jzfmEdit").dialog('close');
						else 
						alert(data);
				},
			});
		}
	</script>
 </body>
</html>
