<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>博法案卷云管理平台</title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jssm/JZQuery.js"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.welcomeBg{
	background-image: url(${pageContext.servletContext.contextPath}/images/welcome.jpg);
	background-size:100% 100%;
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${pageContext.servletContext.contextPath}/images/welcome.jpg',sizingMethod='scale');
}
.style7 {font-size: 12px; color: #FFF; }
.STYLE3 {
	font-size: 12px;
	color: #435255;
}

.STYLE4 {font-size: 12px}
.STYLE5 {font-size: 12px; font-weight: bold; }
.link_color{color:#3399CC; text-decoration:none;font-weight:bold;}/*链接设置*/
.link_color:visited{color:#3399CC; text-decoration:none;font-weight:bold;}/*访问过的链接设置*/
.link_color:hover{color:#3399CC; text-decoration:none;font-weight:bold;}/*鼠标放上的链接设置*/
</style>
</head>
<body class="easyui-layout" data-options="border:true,fit:true" >
      	<div style="height:130px;padding: 0px;margin: 0px;overflow: hidden;"
 			data-options="border:true,region:'north',split:false">
			<table  width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr>
				    <td height="57" background="${pageContext.servletContext.contextPath }/images/main_03.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="378" height="57" background="${pageContext.servletContext.contextPath }/images/main_01.gif">&nbsp;</td>
				        <td>&nbsp;</td>
				        <td width="281" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
				          <tr>
				            <td width="33" height="27"><img src="${pageContext.servletContext.contextPath }/images/main_05.gif" width="33" height="27" /></td>
				            <td width="248" background="${pageContext.servletContext.contextPath }/images/main_06.gif"><table width="225" border="0" align="center" cellpadding="0" cellspacing="0">
				              <tr>
				                <td height="17">
				                	<div align="right" ><a href="###" onclick="updatepwd(${currentUser.uid});" ><img style="border:0;" src="${pageContext.servletContext.contextPath }/images/pass.gif" width="69" height="17" /></a></div></td>
				                <td><div align="right"><a href="###" onclick="currUser();"><img style="border:0;" src="${pageContext.servletContext.contextPath }/images/user.gif" width="69" height="17" /></a></div></td>
				                <td><div align="right"><a href="###" onclick="quit();"><img style="border:0;" src="${pageContext.servletContext.contextPath }/images/quit.gif" width="69" height="17" /></a></div></td>
				              </tr>
				            </table></td>
				          </tr>
				        </table></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr>
				    <td height="40" background="${pageContext.servletContext.contextPath }/images/main_10.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr>
				        <td width="194" height="40" background="${pageContext.servletContext.contextPath }/images/main_07.gif">&nbsp;</td>
				        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
				          <tr>
				            <td>&nbsp;</td>
				          </tr>
				        </table></td>
				        <td width="248" background="${pageContext.servletContext.contextPath }/images/main_11.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
				          <tr>
				            <td width="16%" align="right">&nbsp;</td>
				            <td width="75%"><div align="left"><span class="style7" style="font-size: 12px; color: #FFF;">欢迎：${sessionScope.loginInfo.name}（${currentRoleName}）</span></div></td>
				            <td width="9%">&nbsp;</td>
				          </tr>
				        </table></td>
				      </tr>
				    </table></td>
				  </tr>
				  <tr>
				    <td height="30" >
				    	<div style="float: left;margin: 3px;">
					    	<a href="javascript:void(0)" id="xtglBtn">系统管理</a>
					    	<div id="xtglMenu">
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/user/userListUI.do')">用户维护</div>   
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/role/roleListUI.do')">角色维护</div>					    		
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/sys/envListUI.do')">系统环境设置</div>
					    	</div>
				    	</div>
				    	
				    	<div style="float: left;margin: 3px;">
					    	<a href="javascript:void(0)" id="sjzdBtn">数据字典</a>
					    	<div id="sjzdMenu">
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/catalog/yhzwListUI.do')">用户职务维护</div>
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/catalog/jgdmListUI.do')">机构代码维护</div>  
					    		<div class="menu-sep"></div>  
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/catalog/yjflListUI.do')">案卷一级分类维护</div>
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/catalog/ajlxListUI.do')">案卷类型维护</div>   
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/catalog/mlpcListUI.do')">案卷目录批次维护</div>  
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/catalog/ahzListUI.do')">案号字维护</div>  
					    		
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/catalog/wlryListUI.do')">外来人员类型维护</div>  
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/catalog/zjlxListUI.do')">证件类型维护</div>  
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/catalog/yldnListUI.do')">阅览室电脑维护</div>  
					    	</div>
				    	</div>
				    	
				    	<div style="float: left;margin: 3px;">
					    	<a href="javascript:void(0)" id="jssmBtn">扫描管理</a>
					    	<div id="jssmMenu">
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/raw/scanModelUI.do')">扫描模式设置</div> 
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/raw/rawScanUI.do')">即时扫描</div>
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/raw/scanHistoryUI.do')">扫描历史浏览</div>
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/raw/rawPicUploadUI.do')">扫描图片上传</div>
					    		<div class="menu-sep"></div>  
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/raw/ewmPrintUI.do')">二维码打印</div>
					    	</div>
				    	</div>	
				    	
				    	<div style="float: left;margin: 3px;">
					    	<a href="javascript:void(0)" id="ajglBtn">案卷管理</a>
					    	<div id="ajglMenu">
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/ajuan/jzWizardUI.do?jz_type=js')">即时卷宗整理导航</div>
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/ajuan/jzWizardUI.do?jz_type=ls')">历史卷宗整理导航</div>
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/ajuan/jzgdWizardUI.do')">卷宗归档与管理</div>
					    	</div>
				    	</div>			
				    	
				    	<div style="float: left;margin: 3px;">
					    	<a href="javascript:void(0)" id="dzajglBtn">查阅借阅</a>
					    	<div id="dzajglMenu">
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/query/nbcxUI.do')">卷宗内部查询</div>       
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/query/ajjyOuterQueryUI.do')">案卷对外借阅</div>       
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/query/ajjyOuterRegisterUI.do')">案卷对外借阅登记</div>       
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/query/ajjyOuterHistoryUI.do')">案卷对外借阅历史查询</div>       
					    	</div>
				    	</div>
				    	<div style="float: left;margin: 3px;">
					    	<a href="javascript:void(0)" id="tjfxBtn">统计分析</a>
					    	<div id="tjfxMenu">
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/statistics/jzStaticUI.do')">卷宗整理统计</div>
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/statistics/rawStaticUI.do')">扫描工作统计</div> 
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/statistics/ajjyOuterStaticUI.do')">案卷对外借阅情况统计</div>
					    		<div data-options="iconCls:'icon-ok'" onclick="changeCenter('${pageContext.servletContext.contextPath }/statistics/jzLogUI.do')">卷宗操作日志分析</div>  
					    	</div>
				    	</div>
				    </td>
				  </tr>
			</table>
			<div id="pwddialog" style="display: none;overflow:hidden;"></div>
			<div id="currUser" style="display: none;overflow:hidden;"></div>
		</div>
   		<div id="center" class="welcomeBg"
   		 				data-options="region:'center',
	     						split:false,
	     						href:'${pageContext.servletContext.contextPath}/welcome.do'">
	     
	    </div>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/jssm/home.js"></script>						
</body>
</html>
