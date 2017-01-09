<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
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
<script type="text/javascript">
$(function () {
	$("#dlgrawStatic").dialog ({
	  	title: "扫描工作情况统计",
	  	width: 1024,
	  	height: 600,
	    cache: false,    
	    modal: true,
	    inline:true,   
	  });
	
	$("#exportBtn").linkbutton ({
		iconCls: "icon-save",
	});
	
	$("#exportBtn").click (function (){
		window.location=getContextPath()+"/statistics/outputStaticDataToXls.do";
	});
	
	$("#closeBtn").linkbutton ({
		iconCls: "icon-cancel",
	});
	
	$("#closeBtn").click (function () {
		//关闭父窗体
		$("#dlgrawStatic").dialog("close");
	});
	
	var year = new Date().getFullYear();
	var dataList = null;
	$.ajax({
		url: getContextPath() + "/statistics/getPictureData.do",
		dataType: "json",
		cache: false,
		async: false,
		success: function (result) {
			dataList = result;
		}
	});
	$("#picturediv").highcharts({
        title: {
            text: "",
            x: -20 //center
        },
        xAxis: {
            categories: ["一月", "二月", "三月", "四月", "五月", "六月",
                         "七月", "八月", "九月", "十月", "十一月", "十二月"]
        },
        yAxis: {
            title: {
                text: ""
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: "#808080"
            }]
        },
        tooltip: {
            valueSuffix: "次"
        },
        legend: {
            layout: "vertical",
            align: "right",
            verticalAlign: "middle",
            borderWidth: 0
        },
        series: [{
            name: ""+(year-4),
            data: dataList[0]
        }, {
            name: ""+(year-3),
            data: dataList[1]
        }, {
            name: ""+(year-2),
            data: dataList[2]
        }, {
            name: ""+(year-1),
            data: dataList[3]
        }, {
            name: ""+year,
            data: dataList[4]
        }]
    });
    
    $("#year").text((year-4)+"-"+year+"扫描情况统计");

});
</script>
  <!-- dialog窗体 -->
  <div id="dlgrawStatic">
    <!-- layout布局 -->
    <div class="easyui-layout" style="width:100%;height:100%;">
      <div data-options="region:'north'" style="height:40px;"><a href="#" id="exportBtn" style="font-size:15px;margin-top:5px;margin-left:10px;">导出</a></div>   
      <div data-options="region:'center'"style="height:300px;width:98%;" ><div id="picturediv" align="center" ></div><div style = "font-size:20px;"  align="center"><span id = "year"></span></div></div>
      <div data-options="region:'south'" style="height:40px;"><div align="center"><a id="closeBtn" style="font-size:15px;margin-top:5px;">关闭</a></div></div>   
    </div>
  </div>
</body>
</html>
