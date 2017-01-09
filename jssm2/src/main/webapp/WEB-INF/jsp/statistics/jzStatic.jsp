<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>卷宗整理统计</title>
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



	<div id="dlgJzStatic">
		<div class="easyui-layout" style="width:100%;height:100%;">   
		    <div data-options="region:'north'" style="height:40px;">
			    <a href="${pageContext.servletContext.contextPath }/statistics/exportJzStaticXls.do" id="jzStaticExport"
				style="font-size:15px;margin-top:5px;margin-left:10px;"></a>
		    </div>   
		    <div data-options="region:'south'" style="height:40px;">
				<div align = center><a href = "javascript:void(0)" id = "closeJzStatic" style = "font-size:15px;margin-top:5px;"></a></div>
		    </div>   
		    <div data-options="region:'center'"style="height:300px;" >
		    	<div align="center" style="margin-top:20px;">
					<div id="jzStatic1" style="width:700px;"></div>
					<div style = "font-size:20px;">${jzStatistics0.year}年卷宗整理情况统计</div>
					<div id="jzStatic2" style="width:700px;"></div>
					<div style = "font-size:20px;">${jzStatistics1.year}年卷宗整理情况统计</div>
					<div id="jzStatic3" style="width:700px;"></div>
					<div style = "font-size:20px;">${jzStatistics2.year}年卷宗整理情况统计</div>
					<div id="jzStatic4" style="width:700px;"></div>
					<div style = "font-size:20px;">${jzStatistics3.year}年卷宗整理情况统计</div>
					<div id="jzStatic5" style="width:700px;"></div>
					<div style = "font-size:20px;">${jzStatistics4.year}年卷宗整理情况统计</div>
				</div>
		    </div>   
		</div>  
	</div>
	<script type="text/javascript">
		$("#dlgJzStatic").dialog({
			title : '卷宗整理情况统计',
			width : 1024,
			height : 600,
			inline : true,
	    	modal: true,
	    	cache:false,
		});
		
		$("#jzStaticExport").linkbutton({
			iconCls : 'icon-print',
			text:'导出',
		});
		
		$("#closeJzStatic").linkbutton({
			iconCls : 'icon-cancel',
			text:'关闭',
			onClick:function(){
				$("#dlgJzStatic").dialog("close");
			}
		});
		

		$("#jzStatic1").highcharts(
						{
							chart : {
								type : 'column'
							},
							title : {
								text : ''
							},
							xAxis : {
								categories : [ '1月', '2月', '3月', '4月', '5月',
										'6月', '7月', '8月', '9月', '10月', '11月',
										'12月' ],
								crosshair : true
							},
							yAxis : {
								min : 0,
								title : {
									text : '卷宗 (宗)'
								},
							},
							tooltip : {
								headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
								pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
										+ '<td style="padding:0"><b>{point.y} 宗</b></td></tr>',
								footerFormat : '</table>',
								shared : true,
								useHTML : true
							},
							plotOptions : {
								column : {
									pointPadding : 0,
									borderWidth : 0
								}
							},
							series : [
									{
										name : '已归档',
										data : ${jzStatistics0.ygd}
									},
									{
										name : '未归档',
										data : ${jzStatistics0.wgd}
									} ]
						});
		$("#jzStatic2")
				.highcharts(
						{
							chart : {
								type : 'column'
							},
							title : {
								text : ''
							},
							xAxis : {
								categories : [ '1月', '2月', '3月', '4月', '5月',
										'6月', '7月', '8月', '9月', '10月', '11月',
										'12月' ],
								crosshair : true
							},
							yAxis : {
								min : 0,
								title : {
									text : '卷宗 (宗)'
								},
							},
							tooltip : {
								headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
								pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
										+ '<td style="padding:0"><b>{point.y} 宗</b></td></tr>',
								footerFormat : '</table>',
								shared : true,
								useHTML : true
							},
							plotOptions : {
								column : {
									pointPadding : 0,
									borderWidth : 0
								}
							},
							series : [
									{
										name : '已归档',
										data : ${jzStatistics1.ygd}
									},
									{
										name : '未归档',
										data : ${jzStatistics1.wgd}
									} ]
						});
		$("#jzStatic3")
				.highcharts(
						{
							chart : {
								type : 'column'
							},
							title : {
								text : ''
							},
							xAxis : {
								categories : [ '1月', '2月', '3月', '4月', '5月',
										'6月', '7月', '8月', '9月', '10月', '11月',
										'12月' ],
								crosshair : true
							},
							yAxis : {
								min : 0,
								title : {
									text : '卷宗 (宗)'
								},
							},
							tooltip : {
								headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
								pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
										+ '<td style="padding:0"><b>{point.y} 宗</b></td></tr>',
								footerFormat : '</table>',
								shared : true,
								useHTML : true
							},
							plotOptions : {
								column : {
									pointPadding : 0,
									borderWidth : 0
								}
							},
							series : [
									{
										name : '已归档',
										data : ${jzStatistics2.ygd}
									},
									{
										name : '未归档',
										data : ${jzStatistics2.wgd}
									} ]
						});
		$("#jzStatic4")
				.highcharts(
						{
							chart : {
								type : 'column'
							},
							title : {
								text : ''
							},
							xAxis : {
								categories : [ '1月', '2月', '3月', '4月', '5月',
										'6月', '7月', '8月', '9月', '10月', '11月',
										'12月' ],
								crosshair : true
							},
							yAxis : {
								min : 0,
								title : {
									text : '卷宗 (宗)'
								},
							},
							tooltip : {
								headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
								pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
										+ '<td style="padding:0"><b>{point.y} 宗</b></td></tr>',
								footerFormat : '</table>',
								shared : true,
								useHTML : true
							},
							plotOptions : {
								column : {
									pointPadding : 0,
									borderWidth : 0
								}
							},
							series : [
									{
										name : '已归档',
										data : ${jzStatistics3.ygd}
									},
									{
										name : '未归档',
										data : ${jzStatistics3.wgd}
								}]
						});
		$("#jzStatic5")
				.highcharts(
						{
							chart : {
								type : 'column'
							},
							title : {
								text : ''
							},
							xAxis : {
								categories : [ '1月', '2月', '3月', '4月', '5月',
										'6月', '7月', '8月', '9月', '10月', '11月',
										'12月' ],
								crosshair : true
							},
							yAxis : {
								min : 0,
								title : {
									text : '卷宗 (宗)'
								},
							},
							tooltip : {
								headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
								pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
										+ '<td style="padding:0"><b>{point.y} 宗</b></td></tr>',
								footerFormat : '</table>',
								shared : true,
								useHTML : true
							},
							plotOptions : {
								column : {
									pointPadding : 0,
									borderWidth : 0
								}
							},
							series : [
									{
										name : '已归档',
										data : ${jzStatistics4.ygd}
									},
									{
										name : '未归档',
										data : ${jzStatistics4.wgd}
									} ]
						});
	</script>

</body>
</html>