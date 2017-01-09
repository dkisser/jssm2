<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>档案列表</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui4tdh/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui4tdh/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui4tdh/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui4tdh/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui4tdh/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true" >
    	<div id="tdhCaseListCenter" data-options="region:'center'" style="padding:5px;background:#eee;">
    		<table id="tdhCaseListDg"></table>
    	</div>
    	<div id="caseBrowseDg"></div>
    </div>
    <script type="text/javascript">
    	var caseListOpt = {
    		view : function(index){
    			var row = $("#tdhCaseListDg").datagrid("getRows")[index];
				var title = row.ah + " " + row.jzmc + " " + row.jzlx + " 第" + row.jh + "卷";
	    		$("#caseBrowseDg").dialog({
					title : title,
					width : 1024,
					height : 600,
					closed : false,
					cache : false,
					shadow : true, //显示阴影
					resizable : false, //不允许改变大小
					modal : true, //模式化窗口，锁定父窗口
					inline : true, //是否在父容器中，若不在会出现很多BUG
					href : "${pageContext.servletContext.contextPath}/tdh/caseBrowse4Tdh.do?ewm="+row.ewm,
					onOpen : function(){
						$(this).dialog('move',{    
						  left:30,    
						  top:30
						});
					}
				});
    		}
    	};
    	$(function(){
    		var ah = $.trim("${requestScope.ah4Tdh}");
//     		var fydm = $.trim("${requestScope.fydm4Tdh}");
    		var userId = $.trim("${requestScope.userId4Tdh}");
    		if(ah.length < 1 || userId.length < 1){
    			return false;
    		}
    		$("#tdhCaseListDg").datagrid({
			    url:"${pageContext.servletContext.contextPath}/tdh/getCaseList.do",
			    queryParams: {
					archiveno: ah
				},
			    resizable : false,
				singleSelect : true,
				fitColumns : true,
				fit:true,
				rownumbers : true,
				pagination:true,
				striped: true,
				pageSize:10,
				pageList:[5,10,15],
			    columns:[[    
			        {
			        	field:'ah',title:'案号',align : "center",width:130,
				        	formatter: function(value, row, index) {
			                    var abValue = (value==null?"":value);
			                    return '<span title="' + abValue + '" class="note">' + abValue + '</span>';
			                }
			        },    
			        {
			        	field:'jzmc',title:'卷宗名称',align : "center",width:130,
				        	formatter: function(value, row, index) {
			                    var abValue = (value==null?"":value);
			                    return '<span title="' + abValue + '" class="note">' + abValue + '</span>';
			                }
			        },    
			        {
			        	field:'jzlx',title:'卷宗类型',align : "center",width:130,
				        	formatter: function(value, row, index) {
			                    var abValue = (value==null?"":value);
			                    return '<span title="' + abValue + '" class="note">' + abValue + '</span>';
			                }
			        },
			        {
			        	field:'jh',title:'卷号',align : "center",width:130,
				        	formatter: function(value, row, index) {
			                    var abValue = (value==null?"":value);
			                    return '<span title="' + abValue + '" class="note">' + abValue + '</span>';
			                }
			        },
			        {
			        	field:'opt',title:'操作',align : "center",width:130,
			        		formatter : function(value, row, index) {
        						return '<a href="javascript:void(0);" class="viewBtn" onclick="caseListOpt.view(' + index +')">查看</a>'
							}
					}
			    ]],
			    onLoadSuccess:function(data){
			    	if(data == '' || data.total == 0){
			    		$.messager.alert('警告','电子档案未生成',"info");
			    		return false;
			    	}
					$(".viewBtn").linkbutton({plain:true,height:23, iconCls:'icon-search'});
				}   
			});
    	});
    </script>
  </body>
</html>
