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
</style>
	<div id="dlgjzNew_fmMain">
		<div class="easyui-layout" fit="true">   
		    <div data-options="region:'center'" style="padding-top: 15px;">
			    <form enctype="multipart/form-data" id="formjzNew_fmMain" method="post">
					<table align="center">
						<tr>
							<td style="text-align: right">卷宗名称:</td>
							<td><input id="tbjzNew_fmJZMC" type="text" style="width:165px" name="jzmc"></td>
			    			<td style="text-align: right">卷宗类型:</td>
							<td><input id="cbjzNew_fmJZLX" type="text" name="jzlx" style="width:130px"></td>
						</tr>
						<tr>
							<td style="text-align: right">审判程序:</td>
							<td><input id="cbjzNew_fmSPCX1" type="text" style="width:60px" name="spcx1"></td>
							<td style="text-align: right">审判结果:裁定</td>
							<td><input id="cbjzNew_fmSPJG1" type="text" style="width:130px" name="spjg1"></td>
						</tr>
						<tr>
							<td style="text-align: right">审判程序:</td>
							<td><input id="cbjzNew_fmSPCX2" type="text" style="width:60px" name="spcx2"></td>
							<td style="text-align: right">审判结果:裁定</td>
							<td><input id="cbjzNew_fmSPJG2" type="text" style="width:130px" name="spjg2"></td>
						</tr>
						<tr>
							<td style="text-align: right">安全级别:</td>
							<td><input id="cbjzNew_fmBMJB" name="jbdm" style="width:75px"></td>
			    			<td style="text-align:right;">卷号：</td>
			    			<td><input id="gdJh" name="jh" style="width : 75px" /></td>
						</tr>
						<tr>
							<td style="text-align:right;">目录名称：</td>
			    			<td><input id="cbjzNew_mlpcmc" type="text" style="width:165px" ></td>
			    			<td style="text-align:right;">目录二维码：</td>
			    			<td><input id="ajewm" name="ajewm" type="text" style="width:200px" /></td>
						</tr>
			    		<tr>
			    			<td style="text-align:right;">备注：</td>
			    			<td colspan="3"><input id="remark" name="remark" type="text" ></td>
			    		</tr>
					</table>		
				</form>
		    </div>   
			<div data-options="region:'south'" style="height:50px;padding:auto;text-align: center;padding-top: 3px">
				<a href="javascript:void(0);" id="createBtn" >创建卷宗</a>
		    </div>
		</div>
		<div id="setPageNumDig" style="display: none;padding-top: 17px;">
			<form id="pageNumForm" method="post" >
	    		<table align="center" style="text-align: center;">
	    			<tr>
	    				<td>首页页码： </td>
	    				<td>
				    		<input type="text" id="firstPageNum" />
	    				</td>
	    			</tr>
	    			<tr><td colspan="2"> &nbsp;</td></tr>
	    			<tr><td colspan="2"> &nbsp;</td></tr>
	    			<tr>
	    				<td colspan="2" style="text-align: center;"><a href="javascript:void(0);" id="setPageNumBtn" >确定</a></td>
	    			</tr>
	    		</table>
			</form>
    	</div> 
	</div>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/ewm.js"></script>
	<script type="text/javascript">
		var jzewm = "${currJzewm}";
		var currAh = "${currAh}";
		var flagcombox1 = false;
		var flagcombox2 = false;
		var flagcombox1_s = false;
		var flagcombox2_s = false;
		var jzNewNrOpt = {
			selectMlEwm : function(){
				$("#ajewm").textbox('textbox').select();
				return false;
			},
			doCreateJz : function(syym){
		    	$("#formjzNew_fmMain").form('submit', {
				    url:getContextPath()+"/ajuan/createJsjzWizard.do",
				    onSubmit: function(param){
				    	var validate = $(this).form("validate");
				    	if(validate==false){
				    		$.messager.progress('close');
				    		$.messager.alert('警告','请安要求填写表单','info');
				    		return false;
				    	}
						param.pcdm = $('#cbjzNew_mlpcmc').combobox('getValue');
						param.pcmc = $('#cbjzNew_mlpcmc').combobox('getText');
						param.syym = syym;
						param.ewm = jzewm; 
	        			param.ah= currAh;  
	        			param.txspjg1 = $.trim($('#cbjzNew_fmSPJG1').combobox('getText'));
	        			param.txspjg2 = $.trim($('#cbjzNew_fmSPJG2').combobox('getText'));
				    },    
				    success:function(data){
				    	$.messager.progress('close');
// 				    	console.log(data);
				    	if(data == "" || data.indexOf("Exception") >= 0){
							$.messager.alert('','创建卷宗失败');
							return false;
						}
						var result = eval('(' + data + ')');
						if(result.successMsgInfo == "newJzSuccess"){
							$.messager.alert('成功','新建卷宗成功','info',function(){
								$('#center').panel('refresh',"${pageContext.servletContext.contextPath }/ajuan/jsjzWizard_info.do?jzewm="+jzewm);
							});
							return false;
						}else if(result.msgInfo == "mlEwmNotExsit"){
							$.messager.alert('','该目录二维码对应的图片文件不存在',"warning",function(){
								jzNewNrOpt.selectMlEwm();
							});
						}else if(result.msgInfo == "mlEwmIsUsed"){
							$.messager.alert('','该目录二维码已被其它案卷使用',"warning",function(){
								jzNewNrOpt.selectMlEwm();
							});
						}else if(result.msgInfo == "ewmFormatError"){
							$.messager.alert('','该目录二维码格式错误',"warning",function(){
								jzNewNrOpt.selectMlEwm();
							});
						}else if(result.msgInfo == "jhError"){
							$.messager.alert('','卷号已存在');
						}else if(result.msgInfo == "saveJzError"){
							$.messager.alert('','保存卷宗信息出错');
						}
						/*-即时整理不上传图片
						else if(result.msgInfo == "mlCountError"){
							$.messager.alert('','目录图片必须有且仅有一张');
						}else if(result.msgInfo == "fmCountError"){
							$.messager.alert('','封面图片必须有且仅有一张');
						}*/
				    },    
				});
			},
			doCreateJzConfirm : function(syym,jh)
			{
				$.messager.progress();
				if(!isNaN(jh) && jh > 1){
					$.messager.confirm('确认','您确认首页页码为 '+ syym +' 吗？设置后将无法修改！',function(r){
						if (r){
							jzNewNrOpt.doCreateJz(syym);
						} else {
							$.messager.progress('close');
						}
					});
				} else {
					jzNewNrOpt.doCreateJz(syym);
				}
			},
			createJz : function()
			{
				var ajewmVal = $.trim($("#ajewm").textbox("getValue"));
				var ewmCheck = isValidEwm(ajewmVal,"M");
				if(ewmCheck == 102){
					$.messager.progress('close');
					$.messager.alert('','二维码长度必须为18位','info',function(){jzNewNrOpt.selectMlEwm();});
					return false;
				}
				if(ewmCheck == 103){
					$.messager.progress('close');
					$.messager.alert('','二维码头8位必须为日期字符串','info',function(){jzNewNrOpt.selectMlEwm();});
					return false;
				};
				if(ewmCheck == 104){
					$.messager.progress('close');
					$.messager.alert('','目录二维码必须以“M”结尾','info',function(){jzNewNrOpt.selectMlEwm();});
					return false;
				};
				$.messager.confirm('确认','您确认要创建该卷宗吗？',function(r){
				    if (r){
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
				    	var jh = $('#gdJh').numberbox('getValue');
				    	if(jh > 1){
				    		$("#setPageNumDig").dialog({
								title : "请设置第 "+jh+" 卷的首页页码",
								width : 350,
								height : 180,
								closed : false,
								closable : true,//显示关闭按钮
								cache : false,
								shadow : true, //显示阴影
								resizable : false, //不允许改变大小
								modal : true, //模式化窗口，锁定父窗口
								inline : true, //是否在父容器中，若不在会出现很多BUG
								onOpen : function(){
									$("#firstPageNum").numberbox({
										width : 165,
										value : 1,
										min : 1,
										max : 99999,
										required: true,
										onChange: function(newValue,oldValue){
											var validate = $("#pageNumForm").form("validate");
									    	if(!validate){
									    		return validate;
									    	}
									    	$("#setPageNumDig").dialog("close");
									    	jzNewNrOpt.doCreateJzConfirm(newValue,jh);
										}
									});
									$("#setPageNumBtn").linkbutton({    
									    iconCls: 'icon-save',
									    onClick: function(){
											var validate = $("#pageNumForm").form("validate");
									    	if(!validate){
									    		return validate;
									    	}
									    	$("#setPageNumDig").dialog("close");
									    	jzNewNrOpt.doCreateJzConfirm($("#firstPageNum").numberbox("getValue"),jh);
									    }
									});
								},
							});
							
				    	} else {
				    		jzNewNrOpt.doCreateJzConfirm(1,jh);
				    	}
					}else{
						$.messager.progress('close');
					}
				});
			}
		};
		
		$(function(){
			if(jzewm == "" || currAh == ""){
				$.messager.alert('警告','读取案号和二维码失败');   
				return false;
			}
			var currJzType = "${currJzType}";
			$('#dlgjzNew_fmMain').dialog({
				title: "即时卷宗整理导航--新建卷宗："+currAh,
				modal: true,
				shadow:true, 
			    width : 550,
				height : 310,
			    inline:true,
			    closable:false,		
			});
			//卷宗名称
			$('#tbjzNew_fmJZMC').textbox({
				prompt:'请输入案件名称（必填）',
				required:true,
				value:"民事一审诉讼卷宗"
			});
			
			//选择目录批次
			$('#cbjzNew_mlpcmc').combobox({
				valueField: 'id',
				textField: 'text',
				panelHeight:'auto',
				editable : false,
				url:'${pageContext.servletContext.contextPath }/ajuan/getMlpc.do',
			});
			//卷宗类型
			$('#cbjzNew_fmJZLX').combobox({
				valueField: 'text',
				textField: 'text',
				required:true,
				editable:false,
			    url: "${pageContext.servletContext.contextPath }/ajuan/getJZLX.do",
				panelHeight:'auto',
			});
			//审判程序1
			$('#cbjzNew_fmSPCX1').combobox({ 
				valueField: 'text',
				textField: 'text',
				panelHeight:'auto',
				editable:false,
				url:"${pageContext.servletContext.contextPath }/ajuan/getSPCX.do",
				onSelect:function(record){
					if(record.id=="0"){
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
			});
			//审判程序2
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
			});
			//审判结果1
			$('#cbjzNew_fmSPJG1').combobox({ 
				valueField: 'text',
				textField: 'text',
				validType:'length[1,30]',
				url:"${pageContext.servletContext.contextPath }/ajuan/getSPJG.do",
				disabled:true,
				onSelect:function(){
					flagcombox1_s = true;
				},
			});
			//审判结果2
			$('#cbjzNew_fmSPJG2').combobox({ 
				valueField: 'text',
				textField: 'text',
				validType:'length[1,30]',
				url:"${pageContext.servletContext.contextPath }/ajuan/getSPJG.do",
				disabled:true,
				onSelect:function(){
					flagcombox2_s = true;
				},
			});
			//卷宗分类
			$('#gdJzfl').combobox({
				url : getContextPath() + '/ajuan/getGdJzfl.do?yjflFlag=ks',
				valueField : 'id',
				textField : 'text',
				value:'003',
				editable:false
			});
			//卷号
			$('#gdJh').numberbox({
				value:'1',
				required: true,
				max:999,
				prompt:'必填',
			});
			//安全级别
			$('#cbjzNew_fmBMJB').combobox({ 
				valueField: 'text',
				textField: 'text',
				panelHeight:'auto',
				editable:false,
				url:"${pageContext.servletContext.contextPath }/ajuan/getAQJB.do",
				required:true,
			});
			//创建卷宗按钮
	    	$("#createBtn").linkbutton({    
			    iconCls: 'icon-add',
			    size:'large',
			    onClick: function(){
			    	jzNewNrOpt.createJz();
			    }
			});
			//目录二维码
			$("#ajewm").textbox({
				prompt:'请输入目录二维码（必填）', 
				required: true,
				validType:'length[8,18]'
			});
			$("#ajewm").textbox("textbox").css("ime-mode", "disabled");//禁止中文输入法
			$("#ajewm").textbox('textbox').focus();
			//备注
	    	$("#remark").textbox({    
				prompt:'请输入案件备注',
				width:"100%",
				height:50,
				multiline : true,
				validType:'length[0,33]'
			});
	    	$("#dlgjzNew_fmMain").bind("keydown",function(e){
				var theEvent = e || window.event;    
				var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
				if (code == 13) {
					jzNewNrOpt.createJz();
				}
			});
			
		});
	</script>
 </body>
</html>
