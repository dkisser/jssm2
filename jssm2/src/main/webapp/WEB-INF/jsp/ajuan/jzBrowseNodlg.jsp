<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	padding: 0px;
}

</style></head>

<body>

<style type="text/css">

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.datagrid-cell,.datagrid-cell-group,.datagrid-cell-rownumber
	{
	margin: 0;
	padding: 0 4px ;
	white-space: nowrap;
	word-wrap: normal;
	overflow: hidden;
	height: 20px;
	line-height: 20px;
	font-size: 15px;
}
.textbox .textbox-text {
  font-size: 18px;
  border: 0;
  margin: 0;
  padding: 4px;
  white-space: normal;
  vertical-align: top;
  outline-style: none;
  resize: none;
  -moz-border-radius: 5px 5px 5px 5px;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
}
.datagrid-header-row{
	height:43px;
}
.input_jzInfo {
	border: 0px solid #95B8E7;
	vertical-align: middle;
	display: inline-block;
	font-size: 20px;
	margin: 0;
	padding: 2;
	overflow: visible;
}

.input_special {
	border: 0px;
	font-size: 20px;
	color: red;
	margin: 0;
	padding: 2;
	width: auto;
	font-weight: bold
}
</style>

<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/jquery-css3-gallery-animation/css/zzsc.css" />
<!-- 	<div id="jzBrowseDia" class="easyui-dialog" data-options="title:'${jzid}',width: 1024,height: 600,inline: true,model:true"> -->
	<div id="jzBrowsetab" class="easyui-tabs" data-options="selected:1" fit="true">
	
		<div title="封面" fit="true">
				<div class="easyui-layout" fit="true">
					<div data-options="region:'north'" height=45>
						<table rules=all>
							<tr>
								<td width="1008" height=36>
									<input id="aqjb"  readonly="true" style="padding-left:2px;border: 0px;font-size: 20px;color: red;margin: 0;padding: 2;width: auto;font-weight: bold;">
									<input id="status" class="input_special" readonly="true" style="padding-left:15px;margin-left: 185;border: 0px;font-size: 20px;color: red;width: auto;font-weight: bold;"> 
									<input id="jzlx" value='${jz.jzlx}' style=" font-size:17pt;width:50px;border-color:black;float:right;margin-right:20px"readonly="true";></td>
							</tr>
						</table>
					</div>
					<div data-options="region:'center'"  height=455>
						<div style="width:1000;font-size:21pt;text-align:center;font-weight:bold">
							${jz.fymc}
						</div>
						<div style="width:1000;font-size:15pt;text-align:center;">
							${jz.jzmc}
						</div>
						<div style="width:1000;font-size:15pt;text-align:center;">
							${jz.ah}
						</div>
						<table rules=all border="1">
							<tr>
								<td width="100" height="36" align="right"
									style="font-size: 20px;padding-right: 20">案由</td>
								<td width="908"><textarea rows="2" readonly="true" style="width: 900px;height: 44px;resize: none;font-size: 20px;border: 0px solid #95B8E7;vertical-align: middle;overflow: visible;">${jz.ayms}</textarea></td>
							</tr>
							<tr>
								<td width="100" height="36" align="right"
									style="font-size: 20px;padding-right: 20">当事人</td>
								<td width="908"><textarea rows="2" readonly="true" style="width: 900px;height: 44px;resize: none;font-size: 20px;border: 0px solid #95B8E7;vertical-align: middle;overflow: visible;">${jz.dsr}</textarea></td>
							</tr>
						</table>
						<table rules=all border="0">
							<tr bgcolor="#F5F5F5" align="center">
								<td width="245" height="36"
									style="font-size: 20px;padding-left: 10">审判长</td>
								<td width="245" style="font-size: 20px;padding-left: 10">承办部门</td>
								<td width="245" style="font-size: 20px;padding-left: 10">承办人</td>
								<td width="245" style="font-size: 20px;padding-left: 10">书记员</td>
							</tr>
							<tr bgcolor="#F5F5F5" align="center">
								<td width="245" height="36"
									style="font-size: 20px;padding-left: 10">${jz.spz}</td>
								<td width="245" class="input_jzInfo" style="padding-left: 10;">${jz.cbbm}</td>
								<td width="245" style="font-size: 20px;padding-left: 10">${jz.cbr}</td>
								<td width="245" style="font-size: 20px;padding-left: 10">${jz.sjy}</td>
							</tr>
							<tr>
								<td width="245" height="36" align="right"
									style="font-size: 20px;padding-right: 20px">收案日期</td>
								<td width="245" style="padding-left: 10"><input
									value='${larq}' class="input_jzInfo" readonly="true"></td>
								<td width="245" align="right" style="font-size: 20px;padding-right: 20px">结案日期</td>
								<td width="245" style="padding-left: 10"><input
									value='${jarq}' class="input_jzInfo" readonly="true"></td>
							</tr>
							<tr>
								<td width="245" height="36" align="right" style="font-size: 20px;padding-right: 20px"><input id="cx1" 
									value='${jz.cx1}结果' class="input_jzInfo" readonly="true" style="text-align:right;"></td>
								<td width="245" style="padding-left: 10"><input id="jg1"
									value='裁定${jz.jg1}' class="input_jzInfo" readonly="true"></td>
								<td width="245" align="right" style="font-size: 20px;padding-right: 20px"><input style="text-align: right;width:240;"
									value='${jz.cx2}结果' id="cx2" class="input_jzInfo" readonly="true" style="text-align: right;"></td>
								<td width="245" style="padding-left: 10"><input id="jg2"
									value='裁定${jz.jg2}' class="input_jzInfo" readonly="true"></td>
							</tr>
							<tr>
								<td width="245" height="36" align="right"
									style="font-size: 20px;padding-right: 20px">归档日期</td>
								<td width="245"><input value='${gdrq}' class="input_jzInfo"
									readonly="true"></td>
								<td width="245"align="right" style="font-size: 20px;padding-right: 20px">归档号数</td>
								<td width="245" style="padding-left: 10"><input
									value='${jz.gdhs}' class="input_jzInfo" readonly="true"></td>
							</tr>
							<tr>
								<td width="245" height="36" align="right"
									style="font-size: 20px;padding-right: 20px">保存期限</td>
								<td width="245" style="padding-left: 10"><input
									value='${jz.bcqx}' class="input_special" readonly="true"></td>
								<td width="245"align="right"  style="font-size: 20px;padding-right: 20px">统计级别</td>
								<td width="245" style="padding-left: 10"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
	
	<div title="内容" fit="true">
		<div class="easyui-layout" data-options="border:false" fit=true>
			<div data-options="region:'west',split:false,noheader:false,border:false" style="width:20%;border-right: 2px solid #EDF4FF">
				<div>
					<div  style="width:100%;padding:10px 0px 10px 0px;border-top:0px;border-left:0px;border-right:0px;border-bottom:1px;solid:0;background:#EDF4FF;overflow: hidden;"
						id="ClickPanel" class="easyui-panel">
						<span id="jzBrowsePdfOut" style="text-decoration:none;margin-left: 10px;" href="#" title="导出PDF"
							class="note"> <a><img id="pdfDownloadBtn"
							src="${pageContext.servletContext.contextPath}/js/easyui/themes/icons/PDF.png"></a>
						</span>
					</div>
					<div style="display: none">
						<a id="pdfDownLoad" href="${pageContext.servletContext.contextPath}/ajuan/jzPdfDownload.do" class="easyui-linkbutton"> 
						</a>
					</div>
					<br>
					<div id="jzBrowseTree" style="overflow: hidden"></div>
				</div>
			</div>
			<div id="pictureShowPanel" data-options="region:'center',noheader:false,border:false,href:'${pageContext.servletContext.contextPath}'+'/ajuan/jzBrowsePicture.do?jzEwm='+'${jzEwm}'" style="width:80%;background-color: #000000">
				 
			</div>
	</div>
		</div>
		</div>
	<script type="text/javascript">
		var isShowPdfButton = '${print}';
		if(isShowPdfButton === 'false'){
			$('#ClickPanel').panel({closed:true,});
		}
		
		var jzid = '${jzid}';
		if(jzid==""){
			$("#jzBrowsePdfOut").hide();
		}
		$("#jzBrowseTree").tree({url : getContextPath()+ "/ajuan/jzBrowsetreeData.do?jzEwm="+ '${jzEwm}',
							onLoadSuccess : function(node, data) {
								$(".note").tooltip({
									onShow : function() {
										$(this).tooltip('tip').css({
											boxShadow : '1px 1px 3px #292929'
										});
									}
								});
							},
                            cascadeCheck:true,
							formatter : function(node) {
								if (node.text != null) {
									var abValue = node.text;
									var content = '<span title="' + node.text + '" class="note"  style="text-decoration:none;color:#000">'+ abValue + '</span>';
									return content;
								} else {
									return '';
								}
							},
							checkbox:true,
							onClick : function(node) {
								if(node.text=='全部'){
									$('#pictureShowPanel').panel('refresh',getContextPath()+'/ajuan/jzBrowsePicture.do?jzEwm='+'${jzEwm}');
								}else{
									$('#pictureShowPanel').panel('refresh',getContextPath()
												+ "/ajuan/jzBrowsePictureShowAll.do?jzEwm="
												+"${jzEwm}"+"&nodeText="+ node.text);
								}
							},

						});
		
		$("#pdfDownloadBtn").click(function() {
			var nodes = $('#jzBrowseTree').tree('getChecked');
			var childs = $('#jzBrowseTree').tree('getChildren');
			if(nodes.length == childs.length){//全部导出
				$.ajax({
					url : getContextPath() + "/ajuan/jzFileIntoPdf.do",
					async : false,
					cache : false,
					data : {"jzEwm":'${jzEwm}'},
					type : "post",
					dataType : "json",
					success : function(data) {
						if (data.success == "0") {
							document.getElementById("pdfDownLoad").click();
						}
					}
				});
			}else{//部分导出
				var str = '';
				for(var i=0; i<nodes.length; i++){
					str+=nodes[i].text+';';
				}
				$.ajax({
					url : getContextPath() + "/ajuan/jzFileIntoPdf.do",
					async : false,
					cache : false,
					data : {"jzEwm":'${jzEwm}',str: str},
					type : "post",
					dataType : "json",
					success : function(data) {
						if (data.success == "0") {
							document.getElementById("pdfDownLoad").click();
						}
					}
				});
				
			}
		});
		
		
		if (${jz.status} == 1)
			document.getElementById("status").value = "归档";
		if ("${jz.aqjb}" != "普通")
			document.getElementById("aqjb").value = "${jz.aqjb}";
			
		if("${jz.cx1}".length==0){
			document.getElementById("cx1").value = "      结果";
			document.getElementById("jg1").value = "";
		}	
			
		if("${jz.cx2}".length==0){
			document.getElementById("cx2").value = "      结果";
			document.getElementById("jg2").value = "";
		}
		

		
	</script>
</body>
</html>
