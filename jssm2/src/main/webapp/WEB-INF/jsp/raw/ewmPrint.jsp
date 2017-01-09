<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>二维码打印</title>
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
    <div id="ewmPrintDialog">
    	<div class="easyui-layout" data-options="fit:true">   
		    <div data-options="region:'center'" style="padding-top: 55px;">
		    	<form id="ewmPrintForm" method="post">
			    	<table align="center">
			    		<tr>
			    			<td>类型： </td>
			    			<td><input id="ewmType" name="ewmType" ></td>
			    			<td>&nbsp;</td>
			    		</tr>
			    		<tr><td colspan="3"> &nbsp;</td></tr>
			    		<tr>
			    			<td>生成PDF文件： </td>
			    			<td>
	    						<div style="text-align: left;"><input id="printPdfYes" type="radio" onclick="ewmPrintOpt.changeToTb();"
	    							 name="printModel" value="1"><label for="printPdfYes">是</label></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input onclick="ewmPrintOpt.changeToCb();"
	    							 id="printPdfNo" type="radio" name="printModel" value="2"><label for="printPdfNo">否</label></input></div>
						    </td>
						    <td>&nbsp;</td>
			    		</tr>
			    		<tr><td colspan="3"> &nbsp;</td></tr>
			    		<tr>
			    			<td>页数： </td>
			    			<td><div id="pageNumbersDiv"><input id="pageNumbers" name="pageNumbers" >
			    				</div><div id="pageNumbers4PdfDiv" style="display: none;"><input id="pageNumbers4Pdf" name="pageNumbers4Pdf" ></div></td>
			    			<td>&nbsp;</td>
			    		</tr>
			    	</table>
		    	</form>
		    </div>   
		    <div data-options="region:'south',split:false" style="height:60px;padding-top: 4px;">
		    	<table  align="center">
    				<tr>
    					<td>
    						<a href="javascript:void(0);" id="toEwmPrint" >确定</a>
					    </td>
    				</tr>
    			</table>
		    </div>   
		</div>  
    </div>
    <script type="text/javascript">
    //判断是否是正整数
    function IsNum(s)
	{
	    if(s!=null){
	        var r,re;
	        //re = /\d*/i; //\d表示数字,*表示匹配多个数字
	        re = /^\+?[1-9][0-9]*$/;
	        r = s.match(re);
	        return (r==s)?true:false;
	    }
	    return false;
	};
    var ewmPrintOpt = {
    	toEwmGrid : function(){
    		//生成二维码
    		try{
				$.messager.progress();
				var isValid = $("#ewmPrintForm").form('validate');
				if (!isValid){
					$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
					return isValid;	// 返回false终止表单提交
				}
				var printModel = $('input:radio[name=printModel]:checked').val();//是否生成pdf
				var pageNum = "";
				if(printModel == "1"){
					//生成pdf文件
					pageNum = $.trim($("#pageNumbers4Pdf").textbox("getValue"));
					if(!IsNum(pageNum)){
						$.messager.progress('close');
						$.messager.alert('','页数必须是正整数','',function(){
							$("#pageNumbers4Pdf").textbox("textbox").select();
						});
						return false;
					}
				}else if(printModel == "2"){
					//不生成pdf
					pageNum = $.trim($("#pageNumbers").combobox("getValue"));
				}
				var ewmType = $('#ewmType').combobox("getValue");//二维码类型
				var url = "${pageContext.servletContext.contextPath}/raw/ewmGrid.do?ewmType="+ewmType
					+"&pageNumbers="+pageNum+"&printModel="+printModel;
				window.open(url);
				$.messager.progress('close');
    		}catch(err){
    			$.messager.progress('close');
    		}
    	},
    	changeToTb : function(){
    		//生成pdf，页数可输入
    		$("#pageNumbers").combobox("select","1");
    		$("#pageNumbers4Pdf").textbox("setValue","1");
    		$("#pageNumbersDiv").hide();
    		$("#pageNumbers4PdfDiv").show();
    	},
    	changeToCb : function(){
    		//不生成pdf，页数只能下拉选
    		$("#pageNumbers4Pdf").textbox("setValue","1");
    		$("#pageNumbers").combobox("select","1");
    		$("#pageNumbers4PdfDiv").hide();
    		$("#pageNumbersDiv").show();
    	}
    };
    $(function(){
    	//主窗口
    	$('#ewmPrintDialog').dialog(
	    	{
				title : '二维码打印',
				width : 512,
				height : 300,
				closed : false,
				cache : false,
				shadow : true, //显示阴影
				resizable : false, //不允许改变大小
				modal : true, //模式化窗口，锁定父窗口
				inline : true, //是否在父容器中，若不在会出现很多BUG
			}
		);
		//类型下拉框
		$('#ewmType').combobox(
			{
				valueField:"id",
    			textField:"text",
    			width:180,
				editable : false,//不可编辑
				data: 
				[{
					"id": "Y",
					"text": "页面",
					"selected":true
				},
				{
					"id": "F",
					"text": "封面"
				},
				{
					"id": "M",
					"text": "目录"
				}],
			}
		);
		//页数下拉框
		$('#pageNumbers').combobox(
			{
				valueField:"id",
    			textField:"text",
    			width:180,
				editable : false,//不可编辑
				data: 
				[{
					"id": "1",
					"text": "1",
					"selected":true
				},
				{
					"id": "2",
					"text": "2"
				},
				{
					"id": "3",
					"text": "3"
				},
				{
					"id": "4",
					"text": "4"
				},
				{
					"id": "5",
					"text": "5"
				}],
			}
		);
		//页数输入框
		$("#pageNumbers4Pdf").textbox({
   			width : 180,
   			required : true,
   			validType : "length[1,4]",
   			value : "1",
    	});
		//默认选中不生成pdf
		$('#printPdfNo').attr('checked',true);
		//确定按钮
		$('#toEwmPrint').linkbutton({    
		    iconCls: 'icon-save',
		    plain : false,
		    size : "large",
		    onClick : function(){
		    	ewmPrintOpt.toEwmGrid();
		    },
		});
    });
    </script>
  </body>
</html>
