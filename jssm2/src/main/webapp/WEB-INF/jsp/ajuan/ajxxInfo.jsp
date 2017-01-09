<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>案件详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  </head>
  <body>
		<table height="25%"  style="padding-left:20px;">
			<tr>
				<td align="right">案件标识：</td>
				<td><input id="ahdm" type="text" value="${ajxx.ahdm}" class="textbox1"/></td>
				<td width="20px"></td>
				<td align="right">案号：</td>
				<td><input id="ah" type="text" value="${ajxx.ah}" class="textbox1"/></td>
				<td width="20px"></td>
				<td align="right">经办法院：</td>
				<td><input id="fydmms" type="text" value="${ajxx.fydmms}" class="textbox1" style="color:#FF0000"/></td>
				<td width="20px"></td>
			</tr>
			<tr>
				<td align="right">案件名称：</td>
				<td><input id="ajmc" type="text" value="${ajxx.ajmc}" class="textbox1"/></td>
				<td width="20px"></td>
				<td align="right">案件代字：</td>
				<td><input id="dz" type="text" value="${ajxx.dz}" class="textbox1"/></td>
				<td width="20px"></td>
				<td align="right">案件类型：</td>
				<td><input id="ajlx" type="text" value="${ajxx.ajlx}" class="textbox1"/></td>
				<td width="20px"></td>
			</tr>	
			<tr>
				<td align="right">立案日期：</td>
				<td><input id="larq" type="text" value="${ajxx.larq}" class="textbox1"/></td>
				<td width="20px"></td>
				<td align="right">结案日期：</td>
				<td><input id="jarq" type="text" value="${ajxx.jarq}" class="textbox1"/></td>
				<td width="20px"></td>
				<td align="right">审限界满日期：</td>
				<td><input id="sxjmrq" type="text" value="${ajxx.sxjmrq}" class="textbox1"/></td>
				<td width="20px"></td>
			</tr>
			</table>
			<table height="40%" style="padding-left:20px;"> 
			<tr>
				<td align="right">收案案由：</td>
				<td ><input id="saayms" type="text" value="${ajxx.saayms}" class="textbox2" /></td>
			</tr>
			<tr>
				<td align="right">案由描述：</td>
				<td valign="center"><input id="ayms" type="text" value="${ajxx.ayms}" class="textbox2" /></td>
			</tr>
			<tr>
				<td align="right">当事人：</td>
				<td valign="center"><input id="dsr" type="text" value="${ajxx.dsr}" class="textbox2" /></td>
			</tr>
			</table>
			<table  height="25%" style="padding-left:20px;">
			<tr>
				<td align="right">结案方式：</td>
				<td><input id="jafsms" type="text" value="${ajxx.jafsms}" class="textbox1"/></td>
				<td width="20px"></td>
			</tr>
			<tr>
				<td align="right">承办部门：</td>
				<td><input id="cbbmms" type="text" value="${ajxx.cbbmms}" class="textbox1"/></td>
				<td width="20px"></td>
				<td align="right">&nbsp;&nbsp;承办人：</td>
				<td><input id="cbrms" type="text" value="${ajxx.cbrms}" class="textbox1"/></td>
			</tr>
			<tr>
				<td align="right">审判长：</td>
				<td><input id="spz" type="text" value="${ajxx.spz}" class="textbox1"/></td>
				<td width="20px"></td>
				<td align="right">&nbsp;&nbsp;书记员：</td>
				<td><input id="sjyms" type="text" value="${ajxx.sjyms}" class="textbox1"/></td>
			</tr>
		</table>
		<table>
			<tr>
				<hr>
			</tr>
		</table>
		<table align="center">
			<tr>
				<td><a id="closeBtn" href="javascript: void(0);">关闭</a></td>
			</tr>
		</table>
		<script type="text/javascript">
		
		$("#dlgAjxxDetail").dialog({
				title:'${ajxx.ah}'+'('+'${ajxx.zt==1?"已归档":"未归档"}'+')',
			});
			$(".textbox1").textbox({
				iconCls: 'icon-lock',
				readonly: true,
				width: 220,
				height:30
			});
			
			$(".textbox2").textbox({
				iconCls: 'icon-lock',
				readonly: true,
				multiline:true,
				height:60,
				width: 530
			});
			
			$("#closeBtn").linkbutton({    
			    iconCls: 'icon-cancel'   
			});  
			
			$("#closeBtn").click(function(){    
			  $("#dlgAjxxDetail").dialog('close');
			});
			
		</script>
  </body>
</html>
