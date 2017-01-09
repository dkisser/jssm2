<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css"href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript"src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript"src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
</head>
<body>
	<script type="text/javascript">
			$(function() {//外边框dialog
		     $info='';
		   	 $.ajax({
			    	  url: getContextPath()+'/catalog/yhzwBrowseInfo.do',
				      dataType: 'json',
				      async:false,
		    		  cache:false,					     
				      success: function( data ) {
				    	  $info=data;
						},
					});
			String.prototype.startWith = function(compareStr){
				return this.indexOf(compareStr) == 0;
			};
			$('#yhzw').combobox({
 		 		data:$info,
				valueField : 'text',
 		 		textField : 'text',
				width:300,
				height:30,
				onSelect:function(record){
					$value=$(this).combobox('getValue');
					for(var i=0;i<$info.length;i++){
						if($info[i].text==$value){							
							alert($info[i].id);
						}
					}
				},
				onChange : function(newValue, oldValue) {
					var json = new Array();
					if(newValue!=''&&newValue!=null){
							$value=newValue.trim();
							for(var i=0,j=0;i<$info.length;i++){
								 $pym=$info[i].pym;
								 if($pym!=null&&$pym.indexOf($value)==0){
										json[j++]=$info[i];
								 }
							}
							$(this).combobox('loadData',json);
						}else{
							$(this).combobox('loadData',$info);
						}
				}
			});

		});
	</script>
	<div id="dg">
		<table>
			<tr height="80">
				<td width="200"></td>
				<td></td>
				<td></td>
			</tr>
			<tr style="font-size: 20px;font-weight: 500;">
				<td></td>
				<td><label>用户职位浏览:</label></td>
				<td  style="font-size: 15px;"><input id="yhzw"></td>
			</tr>
		</table>
	</div>
</body>
</html>
