<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
}
</style>

</head>
<body>
	<div id="dlgAjjyOuterdiv">
		<div id="lytAjjyOuterdiv" class="easyui-layout" fit="true">   
		    <div data-options="region:'north'" style="height:46px;"><a id="lbAjjyOuteroutputBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save',size:'large'" href="${pageContext.servletContext.contextPath }/statistics/exportAjjyOuterXls.do">导出</a></div>   
		    <div id="chartDialog" data-options="region:'center'" style="padding:5px;background:#eee;">
		    	<!-- 图表 -->
<!-- 			    <div id="chartAjjyOuterdiv0" style="width:950px;margin-top: 40px;margin-left: 20px;"></div> -->
		    </div>   
		</div>  
	</div>
	
<script type="text/javascript">
	
	$("#dlgAjjyOuterdiv").dialog({
		title: "案卷对外借阅情况统计",
		width: 1024,
		height: 600,
		inline:true,
	});
	
	var nowYear = new Date().getFullYear();
	for (var i=0;i<5;i++) {
		var chartAjjyOuterdiv = $("<div id='chartAjjyOuterdiv"+i+"'></div>");
		chartAjjyOuterdiv.appendTo('#chartDialog');
		giveChartCss('chartAjjyOuterdiv'+i,nowYear);
		nowYear--;
	}
	
	function giveChartCss (divName,year) {
		var dataList = null;
		$.ajax ({
			url: getContextPath() + "/statistics/getPictureDataByYear.do",
			cache: false,
			data: {"year":year},
			dataType: "json",
			async: false,
			success: function (result) {
				dataList = result.dataList;
			},
			error: function () {
				alert("error");
			}
		});
		var isNull = true;
		for (var i=0;i<dataList[0].length;i++) {
			if (dataList[0][i]!=0) {
				isNull = false;
				break;
			}
		}
		for (var i=0;i<dataList[1].length;i++) {
			if (dataList[1][i]!=0) {
				isNull = false;
				break;
			}
		}
		if (isNull) {
			return false;	
		}
		$('#'+divName).css("width","950px");
		$('#'+divName).css("margin-left","20px");
		$('#'+divName).css("margin-top","40px");
		$('#'+divName).highcharts({
		    chart: {
		        type: 'column'
		    },
		    title: {
		        text: year+'年卷宗对外借阅情况统计'
		    },
		    xAxis: {
		        categories: [
		            '一月',
		            '二月',
		            '三月',
		            '四月',
		            '五月',
		            '六月',
		            '七月',
		            '八月',
		            '九月',
		            '十月',
		            '十一月',
		            '十二月'
		        ],
		        crosshair: true
		    },
		    yAxis: {
		        min: 0,
		    },
		    tooltip: {
		        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		        '<td style="padding:0"><b>{point.y:.1f} 个</b></td></tr>',
		        footerFormat: '</table>',
		        shared: true,
		        useHTML: true
		    },
		    plotOptions: {
		        column: {
		            pointPadding: 0.2,
		            borderWidth: 0
		        }
		    },
		    series: [{
		        name: '允许打印',
		        data: dataList[0]
		    }, {
		        name: '不允许打印',
		        data: dataList[1]
		    }]
		});
	}
	
</script>
</body>
</html>
