<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>博法案卷云管理平台登录</title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
<style type="text/css">
<!--
html {height:100%;}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	height:100%;
	overflow:auto;
}
.STYLE3 {font-size: 12px; color: #adc9d9; }
-->
</style></head>

<body>
	<table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td bgcolor="#1075b1">&nbsp;</td>
	  </tr>
	  <tr>
	    <td height="608" background="${pageContext.servletContext.contextPath }/images/login/login_03.gif">
		<form id="loginForm" method="post">
	    <table width="847" border="0" align="center" cellpadding="0" cellspacing="0">
	      <tr>
	        <td height="318" background="${pageContext.servletContext.contextPath }/images/login/login_04.gif">&nbsp;</td>
	      </tr>
	      <tr>
	        <td height="84"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="381" height="84" background="${pageContext.servletContext.contextPath }/images/login/login_06.gif">&nbsp;</td>
	            <td width="162" valign="middle" background="${pageContext.servletContext.contextPath }/images/login/login_07.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td width="44" height="24" valign="bottom"><div align="right"><span class="STYLE3">用户</span></div></td>
	                <td width="10" valign="bottom">&nbsp;</td>
	                <td height="24" colspan="2" valign="bottom">
	                  <div align="left">
	                    <input class="easyui-validatebox" data-options="required:true,validType:'length[1,26]'"
	                    	type="text" name="uname" id="uname" style="width:100px; height:17px; background-color:#87adbf; border:solid 1px #153966; font-size:12px; color:#283439; "/>
	                  </div></td>
	              </tr>
	              <tr>
	                <td height="24" valign="bottom"><div align="right"><span class="STYLE3">密码</span></div></td>
	                <td width="10" valign="bottom">&nbsp;</td>
	                <td height="24" colspan="2" valign="bottom"><input id="upw" type="password" class="easyui-validatebox" data-options="required:true,validType:'length[1,40]'"
	                	name="upw" style="width:100px; height:17px; background-color:#87adbf; border:solid 1px #153966; font-size:12px; color:#283439; " /></td>
	              </tr>
	              <tr></tr>
	            </table></td>
	            <td width="26"><img src="${pageContext.servletContext.contextPath }/images/login/login_08.gif" width="26" height="84"></td>
	            <td width="67" background="${pageContext.servletContext.contextPath }/images/login/login_09.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
	              <tr>
	                <td height="25"><div align="center"><img src="${pageContext.servletContext.contextPath }/images/login/dl.gif" width="57" height="20" onclick="login();"></div></td>
	              </tr>
	              <tr>
	                <td height="25"><div align="center"><img src="${pageContext.servletContext.contextPath }/images/login/cz.gif" width="57" height="20" onclick="reset();"></div></td>
	              </tr>
	            </table></td>
	            <td width="211" background="${pageContext.servletContext.contextPath }/images/login/login_10.gif">&nbsp;</td>
	          </tr>
	        </table></td>
	      </tr>
	      <tr>
	        <td background="${pageContext.servletContext.contextPath }/images/login/login_11.gif">&nbsp;</td>
	      </tr>
	    </table>
	</form>
	    </td>
	  </tr>
	  <tr>
	    <td bgcolor="#152753">&nbsp;</td>
	  </tr>
	</table>
<script type="text/javascript">
	//登录
	function login() {
		$.messager.progress();	// 显示进度条
		$('#loginForm').form('submit', {    
		    url:'${pageContext.servletContext.contextPath}/user/login.do',
		    onSubmit: function(){
		    	//提交前进行表单验证
		    	var isValid = $(this).form('validate');
		    	if(!isValid){
		    		$.messager.progress('close');
		    	}
				return isValid;	// 返回false终止表单提交
		    },    
		    success:function(data){
		    	$.messager.progress('close');
		    	if(data == null || data == ''){
		    		$.messager.alert('警告','登录失败');
		    		$("#upw").select();
		    		return false;
		    	}
		        var data = eval('(' + data + ')');  // change the JSON string to javascript object
		        if (data.userId){
		        	//验证通过，跳转页面
		        	window.location.href='${pageContext.servletContext.contextPath }/home.do';
		        }
		    }    
		}); 
	}
	//重置
	function reset(){
		$('#loginForm').form('clear');
	}
	// 回车直接提交
	document.onkeydown = function(e) {
		var event = window.event || e; //兼容火狐浏览器
		if (event.keyCode == 13) {
				login();
		}
	};
</script>
</body>
</html>
