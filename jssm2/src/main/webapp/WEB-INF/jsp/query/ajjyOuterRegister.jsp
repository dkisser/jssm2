<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<body>
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/style/JZQuery.css">
	<style type="text/css">
		.query_title {
			margin-top: 5px;
			font-size: 13px;
			font-weight: bold;
			color: red;
			height: 13px;
			line-height: 13px;
			background: url('js/easyui/themes/default/images/blank.gif') no-repeat;
			padding: 5px 0 0 5px;
		}
		
		.tableStyWl {
			padding-left: 30px;
		}
		
		.iptStyW {
			padding-right: 18px;
		}
		.dengji{
			margin: auto;
			text-align: center;
			height: 20px;
			line-height: 20px;		
		}
		.radioSty{
			vertical-align:middle; 
			margin-top:-2px; 
			margin-bottom:1px;
		}
		.iptStyW_k{
			width:20px;
		}
	</style>

	<div id="query_Dlg">
		<div id="query_Datagrid"></div>
		<div class="dengji"><span id="dengji_btn" style="height: 20px;line-height: 20px;font-size: 14px;">登记</span></div>
	</div>
	<div id="queryToolsBar" style="height:190px;">
		<form id="wlyrdj_form_Submit" method="post">
			<table class="tableStyWl">
				<tr class="rowSty">
					<td class="textSty">姓名:</td>
					<td class="iptStyW"><input class="xmSty" id="query_rymcIpt" name="rymc" /><span style="text-align:center;color:#FF0000;">&nbsp;*</span></td>
					<td class="iptStyW_k"></td>
					<td class="textSty">工作单位:</td>
					<td class="iptStyW" ><input id="query_gzdwIpt" name="gzdw" /></td>
					<td class="iptStyW_k"></td>
					<td class="textSty">证件:</td>
					<td class="iptStyW" colspan="3"><input id="query_zjlxIpt" name="zjlx" />
					<input id="query_zjhmIpt" name="zjhm" /><span style="color:#FF0000;">&nbsp;*</span></td>
				</tr>
				<tr>
					<td class="textSty">类型:</td>
					<td class="iptStyW"><input id="query_rylxIpt" name="rylx" /></td>
					<td class="iptStyW_k"></td>
					<td class="textSty">联系方式:</td>
					<td class="iptStyW"><input id="query_lxfsIpt" name="lxfs" /></td>
					<td class="iptStyW_k"></td>
					<td class="textSty">使用电脑:</td>
					<td class="iptStyW" style="width:80px;"><input id="query_djdnIpt" name="djdn" /></td>
					<td class="textSty" style="width:60px;">是否打印:</td>
					<td class="iptStyW">
						<span>
							<label name="query_sfdyIptY"  for="query_sfdyIptY"><image style="width:12px;height:12px;" src="${pageContext.servletContext.contextPath}/js/easyui/themes/icons/ok.png" /></label><input class="radioSty" value="1" type="radio" id="query_sfdyIptY" name="print" />
							<label name="query_sfdyIptN"  for="query_sfdyIptN"><image style="width:12px;height:12px;" src="${pageContext.servletContext.contextPath}/js/easyui/themes/icons/no.png" /></label><input class="radioSty" value="0" checked="checked" type="radio" id="query_sfdyIptN" name="print" />
						</span>
					</td>
				</tr>
			</table>
		</form>
		<hr style="height:3px;border:none;border-top:1px solid  #95B8E7;margin-top: 10px;" />
		<form id="query_form_Submit" method="post">
			<table id="tableSty">
				<tr class="rowSty">
					<td class="textSty">年份:</td>
					<td class="iptSty"><input id="query_yearIpt" name="year" /></td>
					<td class="textSty">审判字号:</td>
					<td class="iptSty"><input id="query_cpzhIpt" name="spzh" /></td>
					<td class="textSty">案卷编号:</td>
					<td class="iptSty"><input id="query_bhIpt" name="ajbh" /></td>
					<td class="textSty">卷宗类型:</td>
					<td class="iptSty"><input id="query_jzlxIpt" name="jzlx" /></td>
					<td class="textSty">安全级别:</td>
					<td class="iptSty"><input id="query_aqjbIpt" name="aqjb" /></td>
					<td class="textSty">保管期限:</td>
					<td class="iptSty"><input id="query_bgqxIpt" name="bgqx" /></td>
				</tr>
				<tr class="rowSty">
					<td class="textSty">当事人:</td>
					<td class="iptSty"><input id="query_dsrIpt" name="dsr" /></td>
					<td class="textSty">收卷案由:</td>
					<td class="iptSty"><input id="query_aymsIpt" name="ayms" /></td>
					<td class="textSty">归档年份:</td>
					<td class="iptSty"><input id="query_gdnfIpt" name="gdnf" /></td>
					<td class="textSty">卷宗状态:</td>
					<td class="iptSty"><input id="query_jzztIpt" name="jzzt" /></td>
					<td class="textSty">归档号数:</td>
					<td class="iptSty"><input id="query_gdhsIpt" name="gdhs" /></td>
					<td></td>
					<td></td>
				</tr>
			</table>
			<table class="tableSty1">
				<tr class="rowSty1">
					<td class="textSty1">立案日期:</td>
					<td class="iptSty1 dateSty"><input id="query_larqFIpt" name="larqFStr" /></td>
					<td class="textSty1 fromto">到:</td>
					<td class="iptSty1 dateSty"><input id="query_larqTIpt" name="larqTStr" /></td>
					<td class="textSty1">结案日期:</td>
					<td class="iptSty1 dateSty"><input id="query_jarqFIpt" name="jarqFStr" /></td>
					<td class="textSty1 fromto">到:</td>
					<td class="iptSty1 dateSty"><input id="query_jarqTIpt" name="jarqTStr" /></td>
					<td class="textSty1 fmewmSty">封面二维码:</td>
					<td class="iptSty1 fmewmStyIpt"><input id="query_fmewmIpt" name="fmEwm" /></td>
					<td><a class="btnSty" id="query_findBtn">查询</a>&nbsp;<a class="btnSty" id="query_resetBtn">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- ------------------------------------------------------------------------------------------------------ -->


	<script>
		$("#query_rymcIpt").textbox({
			width : 120,
			required:true,
			inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
				keyup: function(event){ 
				if(event.keyCode == 13) {
					ajjyOuterRegisterWlry();
				}
				}
			})
			
		});
		
		$("#query_rylxIpt").combobox({
			width : 120,
			panelMaxHeight:81,
			valueField:'id',    
		    textField:'text',
		    editable:false,
			url:getContextPath()+"/query/ajjyOuter_rylx.do",
			onLoadSuccess: function () { 
                var data = $('#query_rylxIpt').combobox('getData');
                if (data.length > 0) {
                    $('#query_rylxIpt').combobox('select', data[2].id);
                } 
            },
            inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
				keyup: function(event){ 
				if(event.keyCode == 13) {
					ajjyOuterRegisterWlry();
				}
				}
			})
		});

		$("#query_zjlxIpt").combobox({
			width : 130,
			panelMaxHeight:81,
			valueField:'id',    
		    textField:'text',
		    editable:false,
			url:getContextPath()+"/query/ajjyOuter_zllx.do",
			onLoadSuccess: function () { 
                var data = $('#query_zjlxIpt').combobox('getData');
                if (data.length > 0) {
                    $('#query_zjlxIpt').combobox('select', data[0].id);
                } 
            },
            inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
				keyup: function(event){ 
				if(event.keyCode == 13) {
					ajjyOuterRegisterWlry();
				}
				}
			})
		});
		
		$("#query_zjhmIpt").textbox({
			width:220,
			required: true,
			invalidMessage:'证件号码不能为空！',
			inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
				keyup: function(event){ 
				if(event.keyCode == 13) {
					ajjyOuterRegisterWlry();
				}
				}
			})
		});
		
		$("#query_gzdwIpt").textbox({
			width : 200,
			inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
				keyup: function(event){ 
				if(event.keyCode == 13) {
					ajjyOuterRegisterWlry();
				}
				}
			})
		});

		$("#query_lxfsIpt").numberbox({
			width : 200,
			inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
				keyup: function(event){ 
				if(event.keyCode == 13) {
					ajjyOuterRegisterWlry();
				}
				}
			})
		});
		
		$("#query_djdnIpt").combobox({
			width : 130,
			panelMaxHeight:81,
			valueField:'id',    
		    textField:'text',
		    editable:false,
			url:getContextPath()+"/query/ajjyOuter_yldn.do",
			onLoadSuccess: function () { 
                var data = $('#query_djdnIpt').combobox('getData');
                if (data.length > 0) {
                    $('#query_djdnIpt').combobox('select', data[0].id);
                } 
            },
            inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
				keyup: function(event){ 
				if(event.keyCode == 13) {
					ajjyOuterRegisterWlry();
				}
				}
			})
		});

		$("#query_remarkIpt").textbox({
			width : '100%',
			inputEvents: $.extend({},$.fn.textbox.defaults.inputEvents,{
				keyup: function(event){ 
				if(event.keyCode == 13) {
					ajjyOuterRegisterWlry();
				}
				}
			})
		});
		
		$("#dengji_btn").linkbutton({
			iconCls:'icon-ok',
			onClick:function(){
				ajjyOuterRegisterWlry();
			},
		});
		

		
		$("#query_Dlg").dialog({
			title : '案卷对外借阅登记',
			width : 1024,
			height : 600,
			modal : true,
			inline : true,
		});
		jzQueryInit();
		enterEvent(ajjyOuterRegisterSubmit);
		
		objMethod = {
			browse : function(index) {
				var datagridData = $("#query_Datagrid").datagrid('getData').rows[index];
				var ah = datagridData.ah;
				var jzewm = datagridData.jzewm;
				var jzBrowseDia = $("<div id=\"nbcxBrowseDia\"></div>");
				jzBrowseDia.appendTo("body");
				$("#nbcxBrowseDia").dialog({
					width : 1024,
					title : ah,
					href : getContextPath() + "/ajuan/jzBrowse.do?hasdlg=no&jzEwm=" + jzewm,
					height : 600,
					closed : false,
					cache : false,
					shadow : true,
					resizable : false,
					modal : true,
					inline : true,
					onClose : function() {
						jzBrowseDia.remove();
					},
				});
			},

		};

		$("#query_resetBtn").linkbutton({
			iconCls : 'icon-undo',
			onClick : function() {
				JzQueryReset();
				$("#query_rymcIpt").textbox('clear');
				$("#query_gzdwIpt").textbox('clear');
				$("#query_zjhmIpt").textbox('clear');
				$("#query_lxfsIpt").textbox('clear');
			}
		});

		$("#query_findBtn").linkbutton({
			iconCls : 'icon-search',
			onClick : function() {
				ajjyOuterRegisterSubmit();
			}
		});
		
		$("#query_Datagrid").datagrid(
				{
					border : false,
					noheader : true,
					width : '100%',
					height : '95%',
					toolbar : '#queryToolsBar',
					columns : [ [{
						field:'rownumber',
						title:'序号',
						resizable:false,
						width:"8%",
						align:'center',
						formatter:function(value, row, index){
							return (index+1)+' <input type="checkbox" class="checkDatagrid" id="check_'+index+'" value='+row.jzewm+'>';
						}
					}, 
					 {
						field : 'ah',
						title : '案号',
						resizable : false,
						width : "23%",
						align : 'center',
						formatter : function(value, row, index) {
							if (value != null) {
								var abValue = value;
								var content = '<a href="#" title="' + value + '" class="note"  style="text-decoration:none;color:#000">' + abValue + '</a>';
								return content;
							} else {
								return '';
							}
						}
					}, {
						field : 'jzlx',
						title : '卷宗类型',
						resizable : false,
						width : "10%",
						align : 'center'
					}, {
						field : 'jh',
						title : '卷号',
						resizable : false,
						width : "10%",
						align : 'center'
					}, {
						field : 'aqjb',
						title : '安全级别',
						resizable : false,
						width : "10%",
						align : 'center'
					}, {
						field : 'status',
						title : '归档状态',
						resizable : false,
						width : "10%",
						align : 'center',
						formatter : function(value, row, index) {
							if (row.status == '0') {
								return "未归档";
							} else if (row.status == '1') {
								return "归档";
							} else {
								return null;
							}
						}
					}, {
						field : 'gdrq',
						title : '归档日期',
						resizable : false,
						width : "10%",
						align : 'center'
					}, {
						field : 'gdhs',
						title : '归档号数',
						resizable : false,
						width : "10%",
						align : 'center'
					}, {
						field : 'method',
						title : '操作',
						width : "10%",
						resizable : false,
						align : 'center',
						formatter : function(value, row, index) {
							if (row.ah != null) {
								return '<a href="#" class="browseBtn"  onclick="objMethod.browse(' + index + ')" style="text-decoration:none;color:#000">浏览</a>';
							} else {
								return null;
							}
						}
					}, ] ],
					onLoadSuccess : function(data) {
						$(this).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();//隐藏底部工具栏
						if (data.total == 0) {
							var body = $(this).data().datagrid.dc.body2;
							body.find('table tbody').append(
									'<tr><td colspan="9" width="' + body.width()
											+ '" style="color:red;height: 25px; text-align: center;border-style: dotted;border-width: 0px 0px 0px 0px;">没有相关记录！</td></tr>');
						} 

						$(".browseBtn").linkbutton({
							plain : true,
							height : 20,
							iconCls : 'icon-search'
						});
						$(".note").tooltip({
							onShow : function() {
								$(this).tooltip('tip').css({
									boxShadow : '1px 1px 3px #292929'
								});
							}
						});
					},
					onBeforeLoad : function() {
						$(this).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
					},
					checkbox:true,
					loadMsg : '正在加载数据...',
					pagination : true,
					pagePosition : 'bottom',
					pageSize : 10,
					pageList : [ 10, 15, 20 ],
					singleSelect : true,
				});
		function ajjyOuterRegisterSubmit(){
			$.messager.progress();
			$('#query_form_Submit').form('submit', {
				url : getContextPath() + "/query/formSubmit.do",
				onSubmit : function(param) {
					var isValid = $(this).form('validate');
					if (!isValid) {
						$.messager.alert('提示', '请按要求输入查询条件');
						$.messager.progress('close');
						return isValid;
					}else{
						$.ajax({
							url : getContextPath() + "/query/ajjyOuter_IsOver.do",
							async : false,
							cache : false,
							data : $('#query_form_Submit').serialize(),
							type : "post",
							dataType : "json",
							success : function(data) {
								if(data == 'error'){
									$.messager.alert('提示', '当前仅显示头<span style="color:#EA1F00">10条</span>满足条件的卷宗。如果其中没有您想要的结果，请重新输入查询条件!');
									return false;
								}
							}
						});
						return true;
					}
					
				},
				success : function(data) {
					var status = data.slice(1, data.length - 1);
					if (status == 'success') {
						$("#query_Datagrid").datagrid({
							url : getContextPath() + "/query/query_Datagrid.do",
						});
					}
					$.messager.progress('close');
				}
			});
		}
		
		function ajjyOuterRegisterWlry(){
			$.messager.progress();
			/**表单验证
			*/
			var isValid = $("#wlyrdj_form_Submit").form('validate');
			if(!isValid){
				$.messager.alert('提示','请按要求填写登记信息');  
				$.messager.progress('close');
				return false;
			}
			/**判断当前电脑是否被占用
			*/
			$.ajax({
				url : getContextPath() + "/query/ajjyOuter_checkDjdn.do",
				async : false,
				cache : false,
				data : {
					"djdn":$("#query_djdnIpt").combobox('getValue')
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					if(data=='no'){
						$.messager.alert('提示','"'+$("#query_djdnIpt").combobox('getText')+'"'+'已被占用,请选择其它电脑！','info',function(){
							$("#query_djdnIpt").combobox('showPanel');
						});  
						$.messager.progress('close');
						return false;
					}else{
						/**判断是否勾选卷宗
						*/
							var datagridData = $("#query_Datagrid").datagrid('getData').rows;
							var length = datagridData.length;
							var jzewm ='';
							var count = 0;
							for(var i = 0; i < length; i++){
								if($("#check_"+i).is(':checked')==true){
									jzewm+=$("#check_"+i).attr('value')+';';
									count = count + 1;
								}
							}
							if(count == 0){
								$.messager.alert('提示', '请勾选卷宗！');
								$.messager.progress('close');
								return false;
							}
							var param=$.param({'jzewm':jzewm,'creatorId':'${loginInfo.userId}','status':'1'}) 
										+ '&' + $('#wlyrdj_form_Submit').serialize();
							$.ajax({
								url : getContextPath() + "/query/ajjyOuter_jzewm.do",
								async : false,
								cache : false,
								data : param,
								type : "post",
								dataType : "json",
								success : function(data) {
									if(data == 'success'){
										$.messager.alert('提示', '登记成功！');
										$.messager.progress('close');
										$("#query_Dlg").dialog('close');
									}
								}
							});
					}
				}
			});
		}
	</script>
</body>
</html>