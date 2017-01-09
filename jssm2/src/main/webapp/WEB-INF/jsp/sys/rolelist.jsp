<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  </head>  
  <body>
  <div id="roelListdialog">
    <table id="tbrolelist"></table>
    <div id="bar"><a id="btn" href="#" class="easyui-linkbutton" onclick="objRole.add()" data-options="iconCls:'icon-add',plain:'true',">新增</a></div>
  </div>
  <script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/jssm/rolelist.js"/>
  </body>
</html>
