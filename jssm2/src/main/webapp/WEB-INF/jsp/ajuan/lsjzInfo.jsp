<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title>卷宗信息目录</title>

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
<script type="text/javascript"src="${pageContext.servletContext.contextPath }/js/jssm/pageCounts.js"></script>



<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

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
  text-align: center;
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
.datagridinput{
	text-align: center;
	color:#f00;
}
</style>

	<div id="dlgJzInfo">
		<div id="jzInfomltab" class="easyui-tabs" fit="true">
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
					<div data-options="region:'south'" height=45>
						<div style="margin-bottom: 0px;text-align: center;width:100%;position: absolute;bottom: 10px;">
							<tr>
								<td><a id="btlsjzInfosave" style="font-size: 16px;">保存</a></td>
							</tr>
						</div>
					</div>
				</div>
			</div>
			<div title="目录" fit="true">
				<div class="easyui-layout" data-options="fit:true">
						<div data-options="region:'north'" style="height:93px;">
							<h1 style="text-align: center;" id = "JzInfopcmd">${pcmc}</h1>
							<%-- 
								<h2 style="text-align: center;margin: 8px 0px;padding: 0px;" id = "JzInfopcmd">${pcmc}</h2>
								<div id="syymDiv" style="display: none;">
								<table align="center">
									<tr>
										<td style="font-weight: bolder;">首页页码： </td>
										<td><input type="text" id="syymDivNumBox"></input></td>
									</tr>
								</table>
								</div>--%>
							<div>
								<table width="100%"  style="border:1px solid #c0c0c0;">
									<tr style="background-color: #efefef;font-wei0ght: bolder;"><td width="11%" style="text-align: center;">序号</td><td width=64%  style="text-align: center;">文 书 名 称</td><td width="17%"  style="text-align: center;">页码（首页页码：${syym4ks}）</td></tr>
								</table>
							</div>
						</div>
						<div data-options="region:'center'" style="height:350px;">
							<div id="jzInfodatagrid" style=""></div>
						</div>
						<div data-options="region:'south'" style="height:150px;">
							<div style="margin-left: 10px;margin-top: 10px">
								<span style="font-size: 20px">备注&nbsp;:&nbsp;</span><input value='${remark }' id="tbjzInfomark"/>
							</div>
							<div
								style="margin-bottom: 0px;text-align: center;width:100%;position: absolute;bottom: 10px;">
								<tr>
									<td><a id="btlsjzInfosave2" style="font-size: 16px;">保存</a></td>
								</tr>
							</div>
						</div>
					</div>
			</div>
			<div title="内容" fit="true"> &nbsp;</div>
		</div>
	</div>
	<script type="text/javascript"src="${pageContext.servletContext.contextPath }/js/jssm/pageCounts.js"></script>
	<script type="text/javascript">
	var syym = parseInt("${syym4ks}");
	var currJzewm = "${jz.jzewm}";
	if(isNaN(syym)){
		syym = "";
	}
	var jzInfo = {
		close : function() {
			$("#dlgjzInfo").dialog("close");
		}
	};
	var jzInfoOpt = {
		doSave4Ks : function()
		{
			$.messager.progress();
			var remark = $.trim($("#tbjzInfomark").textbox('getValue'));
			var indexCode = "";
			var prePageNum = 0;
			var saveFlag = "ok";
			var errorObj = null;
			//判断页码顺序
			var allNumBox = $(".pageNumBox");
			var nullCounts = 0;
			var firstPageNum = true;
			var lastNumBox = null;
			$.each(allNumBox, function(index, numBox){
				var currNum = $.trim($(numBox).numberbox("getValue"));
				if(currNum.length > 0){
					currNum = parseInt(currNum);
					if(firstPageNum){
						if(!isNaN(syym) && currNum != syym){//首页页码
							saveFlag = "errorSyym";
							errorObj = numBox;
							return false;//break
						}
						firstPageNum = false;
					}
					if(!isNaN(syym) && currNum < syym){
						saveFlag = "tooSmall";
						errorObj = numBox;
						return false;//break
					}
					if(currNum <= prePageNum){//判断页码顺序，后面页码必须比前面的页码大
						saveFlag = "tooBig";
						errorObj = numBox;
						return false;//break
					} else {
						prePageNum = currNum;
					}
					lastNumBox = numBox;//最后一个页码
				} else {
					nullCounts++;
					currNum = "dadaodai";
				}
				indexCode +=  (currNum + ";");
			});
			if(allNumBox.length == nullCounts){
				$.messager.progress('close');
				$.messager.alert('警告','页码不能为空',"warning");
				return false;
			}
			if(saveFlag == "ok"){
				//页码正常
				if(indexCode.length == 0){
					$.messager.progress('close');
					$.messager.alert("提示","页码不能为空");
					return false;
				}
				$.ajax(
					{ 
						url: '${pageContext.servletContext.contextPath }/ajuan/ksjzUpdata.do', 
						data:{"indexCode" : indexCode,"remark" : remark, "aj_ewm" : "${AJuanCode.aj_ewm}","syym" : syym},
						async : true,
						cache : false,
						type : "post",
						dataType : "json",
						success : function(data) {
							$.messager.progress('close');
							if(data =='success'){
								$('#center').panel('refresh',getContextPath() + "/ajuan/jzBrowse.do?hasdlg=yes&jzEwm="+currJzewm);
							}else if(data == 'noPicError'){
								$.messager.alert("提示","该案卷内容为空","warning",function(){
// 									$(lastNumBox).textbox('textbox').focus();
// 									$(lastNumBox).textbox('textbox').select();
								});
							}else if(data == 'maxPageError'){
								$.messager.alert("提示","最后一个页码超过该案卷文件总数","warning",function(){
									$(lastNumBox).textbox('textbox').focus();
									$(lastNumBox).textbox('textbox').select();
								});
							} 
							else{
								$.messager.alert("错误","保存失败",'',function(){
// 									var messageline = data.split("序号");
// 									var messagelinenum = messageline[1].split("号");
// 									var errtextid = "lsjzInfoline" + (messagelinenum[0]-1);
// 									$("#" + errtextid).textbox('textbox').select();
								});
							}
						},
						error : function(){
							$.messager.progress('close');
							$.messager.alert("提示","系统出错");
						}
					}
				);
			} else {
				$.messager.progress('close');
				if(saveFlag == "errorSyym"){
					//首页页码错误
					$.messager.alert('警告','第一个页码必须为：' + syym,"warning",function(){
						$(errorObj).textbox('textbox').focus();
						$(errorObj).textbox('textbox').select();
					});
				}else if(saveFlag == "tooBig"){
					//页码错误
					$.messager.alert('警告','后面页码必须比前面的页码大',"warning",function(){
						$(errorObj).textbox('textbox').focus();
						$(errorObj).textbox('textbox').select();
					});
				}else if(saveFlag == "tooSmall"){
					//页码太小
					$.messager.alert('警告','页码不能小于首页页码(' + syym + ')',"warning",function(){
						$(errorObj).textbox('textbox').focus();
						$(errorObj).textbox('textbox').select();
					});
				}
			}
		}
	};
		$(function(){
			if(currJzewm == ""){
				return false;
			}
// 			if(!isNaN(syym)){
// 				$("#syymDiv").show();
// 				$('#syymDivNumBox').numberbox({    
// 				    readonly : true,
// 				    width : 90,
// 				    value : syym
// 				});
// 			}else{
// 				$("#syymDiv").hide();
// 				$("#JzInfopcmd").css("margin","21px 0px");
// 			}
			$("#dlgJzInfo").dialog({
				title: '历史卷宗整理导航----卷宗信息', 	
			    width: 1024,    
			    height: 600,    
			    inline:true,
			});
			$("#jzInfodatagrid").datagrid
			(
				{
					resizable : false,
					url : getContextPath() + "/ajuan/VAJuanDList4LS.do",
					queryParams : {
						"pcdm" : "${AJuanCode.pcdm}",
						"jzewm": currJzewm
					},
					singleSelect : true,
					fitColumns : true,
					showHeader : false,
					fit:true,
					onLoadSuccess : function(){
						$(".pageNumBox").numberbox({
							min:1,width:100
						});
					},
					columns : [ [
							{
								width : "13%",
								field : 'mlid',
								title : "序 号",
								halign : "center",
								align : "center",
								formatter : function(value, row, index) {
									if (row.mlmc == null) {
										return '';
									} else {
										return value;
									}
								}
							},
							{
								width : "70.5%",
								field : 'mlmc',
								title : "文 书 名 称",
								halign : "center",
								align : "left",
								formatter : function(value, row, index) {
									if (row.mlmc == null) {
										return '';
									} else {
										return value;
									}
								}
							},
							{
								width : "18.5%",
								field : 'page',
								title : "页 次",
								halign : "center",
								align : "center",
								formatter : function(value, row, index) {
									if (row.page == null) {
										return "<input id='lsjzInfoline"+ index +"' class='pageNumBox'  name='indexlist' value=''>";
									} else {
										return "<input id='lsjzInfoline"+ index +"' class='pageNumBox'  name='indexlist' value=" + value +">";
									}
								}
							}, ] ],
				}
			);
// 			$.ajax({
// 				url : getContextPath() + "/ajuan/VAJuanDList.do",
// 				async : false,
// 				cache : false,
// 				data : {
// 					"pcdm" : ${AJuanCode.pcdm},
// 					"jzewm": '${jz.jzewm}'
// 				},
// 				type : "post",
// 				dataType : "json",
// 				success : function(data) {
// 					var json = pageCounts(data);
// 					$('#jzInfodatagrid').datagrid({
// 						data : json,
// 					});
// 				}
// 			});

		$("#tbjzInfomark").textbox({
			multiline : true,
			width : '93%',
			height : 60,
		});
		
		$("#btlsjzInfosave").linkbutton({
			iconCls : 'icon-save',
			onClick : function() {
				jzInfoOpt.doSave4Ks();
			}

		});
		
		$("#btlsjzInfosave2").linkbutton({
			iconCls : 'icon-save',
			onClick : function() {
				jzInfoOpt.doSave4Ks();
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
			
			$("#jzInfomltab").tabs({    
			    border:false,
			    onSelect:function(title,index){ 
			        if(title == "内容"){
			        	var contentTab = $(this).tabs('getSelected');    
						$(this).tabs('update', {
							tab: contentTab,
							options: {
								title: title,
								cache: true,
								href: "${pageContext.servletContext.contextPath }/ajuan/lsAjzl.do?ajewm=${AJuanCode.aj_ewm}&mldm=${AJuanCode.pcdm}&isls=true&currJzewm="+currJzewm  // 新内容的URL
							}
						});
			        }else if(title == "目录"){
			        	$("#jzInfodatagrid").datagrid("load");
			        }
			    }    
			});
			
		});
		
		
	</script>
</body>
</html>
