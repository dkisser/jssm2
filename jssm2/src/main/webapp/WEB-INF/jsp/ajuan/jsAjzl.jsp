<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>案卷内部查阅</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${pageContext.servletContext.contextPath }/js/swh_admin.js"></script>
  </head>
  <body>
  	<%--主窗口 --%>
	<div class="easyui-layout" data-options="fit:true">
	    <div data-options="region:'west',split : true,border:false,minWidth:285" style="width:290px;height:100%;">
	    	<div class="easyui-layout" data-options="fit:true">
	    		<div data-options="region:'north',split : false,border:false," style="width:100%;height:69px;background-color: #f4f4f4;text-align: center;">
	    			<div style="padding: 3px;margin: 0 auto;">
	    				<table>
	    					<tr>
	    						<td><label>标签：</label></td>
	    						<td><input id="picLabelCurrName" name="picLabelCurrName" style="width: 210px" ></td>
	    					</tr>
	    					<tr>
	    						<td colspan="2" align="left">
<%-- 	    							<a href="javascript:void(0);" id="lsuploadPicLabelBtn" title="批量添加" class="easyui-tooltip" ></a> --%>
	    							<a href="javascript:void(0);" id="addPicBtn" title="添加图片" class="easyui-tooltip" ></a>
	    							<a href="javascript:void(0);" id="deletePicBtn" title="删除图片" class="easyui-tooltip" ></a>
	    							<a href="javascript:void(0);" id="addPicLabelBtn" title="添加标签" class="easyui-tooltip" ></a>
	    							<a href="javascript:void(0);" id="deletePicLabelBtn" title="删除标签" class="easyui-tooltip" ></a>
	    						</td>
	    					</tr>
	    				</table>
	    				
	    			</div>
	    		</div>
	    		<div data-options="region:'center',border:false," style="padding:0;margin: 0">
	    			<ul id="ajzlTree"></ul>
	    		</div>
	    	</div>
	    </div>
	    <div id="ajzlCenter"  data-options="region:'center',border:true," style="padding:0;margin: 0"></div>
		<%-- 根目录右键菜单 --%>
		<div id="rootRightClickMenu" class="easyui-menu" style="width:150px;display: none;">
			<div data-options="iconCls:'icon-add'">添加图片</div>
		</div>
		<%--图片右键菜单 --%>
		<div id="picRightClickMenu" class="easyui-menu" style="width:150px;display: none;">
			<div data-options="iconCls:'icon-add'">添加图片</div>
			<div data-options="iconCls:'icon-remove'">删除图片</div>
			<div data-options="iconCls:'icon-label-add'">添加标签</div>
			<div data-options="iconCls:'icon-label-delete'">删除标签</div>
		</div>
	</div>
	<script type="text/javascript">
	var currNode_id = '';//当前节点id
	var currAjEwm = '${zjzlInfo.aj_ewm}';
	var currMldm = '${zjzlInfo.mldm}';
	var currPicLabId = '全部';
	var shiftKeyDowm = false;
	var starEndNode = [null,null];
	var isls = "${isls}";
	if(isls == "true"){
		currAjEwm = "${ajewm4ls}";
		currMldm = "${mldm4ls}";
	}
	var rootMlName = "${rootMlName}";
	function getNbbh(node){
		var ewmInfo = node.text.split(".jpg")[0];
		ewmInfo = ewmInfo.substring(ewmInfo.length-4);
		return ewmInfo;
	};
	var mlzlOpt = {
		getNodeIdString : function(nodes){
			//获得节点id拼接字符串
			var nodeIds = "";
			$.each(nodes, function(index, node){
				if($.trim(node.id).length > 0){
					nodeIds = nodeIds + node.id + ";";
				}
			});
			nodeIds = nodeIds.substring(0, nodeIds.length-1);//去除末尾分号
			return nodeIds;
		},
		//初始化添加图片对话框
		addPicDialogInit : function(title){
			var addPicDialog=$("<div id=\"addPicDialog\"></div>");
			addPicDialog.appendTo("body");
			$("#addPicDialog").dialog({
				title : title,
				width : 540,
				height : 400,
				closed : false,
				cache : false,
				shadow : true, //显示阴影
				resizable : false, //不允许改变大小
				modal : true, //模式化窗口，锁定父窗口
				inline : true, //是否在父容器中，若不在会出现很多BUG
				onLoad : function(){
					$('#addPicEwm0').textbox('textbox').focus();
				},
				queryParams: {
				    "aj_ewm" : currAjEwm,
				    "mldm" : currMldm,
				    "currNode_id" : currNode_id,
				    "isls" : isls
				},
				onClose : function(){
					addPicDialog.remove();
				},
			});
			$("#addPicDialog").dialog("open").dialog("refresh","${pageContext.servletContext.contextPath }/ajuan/jsAjzlAddPicUI.do");
		},
		deletePic : function(node){
			//删除单个文件
			var msg = "确定要删除 "+node.text.split(".jpg")[0]+" 吗？";
			$.messager.confirm('删除', msg, function(r){
				if(r){
					mlzlOpt.doDeletePic(node.id);
				}
			});
		},
		deleteMultiPics : function(nodes){
			if(nodes == null || (nodes.length == 1 && nodes[0].text == rootMlName)){
				$.messager.alert('','请至少选择一张图片','warning');
				return false;
			}
			//删除多个文件
			var msg = '请注意：此操作将删除 <span style="color: red;font-weight:bolder;">所有勾选的文件</span>，确定要继续吗？';
			$.messager.confirm('删除', msg, function(r){
				if(r){
					var nodeIds = mlzlOpt.getNodeIdString(nodes);
					mlzlOpt.doDeletePic(nodeIds);
				}
			});
		},
		doDeletePic : function(nodeIds){
			//执行删除操作
			$.messager.progress();
			$.ajax({
				url: "${pageContext.servletContext.contextPath}/ajuan/jsAjzlDeletePic.do",
				async:true,		//是否异步请求
				cache:false,	//是否缓存
				type:"post",	//请求方式
				dataType:"text",	//返回数据类型
				data : {"nodeIds" : nodeIds,"isls" : isls},
				success:function(data){
					$.messager.progress('close');
					if(data == '"success"'){
						$('#ajzlTree').tree('reload');//刷新树目录
					}else{
						$.messager.alert('','删除失败','warning');
					}
				},
				error : function(){
					$.messager.progress('close');
					$.messager.alert('错误','系统异常','warning');
				}
			});
		},
		addPicLabel : function(node){
			//添加标签
			mlzlOpt.doAddPicLabel(node.id);
		},
		addMultiPicLabel : function(nodes){
			//为多个节点添加标签
			var msg = '确定要为<span style="color: red;font-weight:bolder;">所有勾选的文件</span>添加标签吗？';
			$.messager.confirm('添加标签', msg, function(r){
				if(r){
					var nodeIds = mlzlOpt.getNodeIdString(nodes);
					mlzlOpt.doAddPicLabel(nodeIds);
				}
			});
		},
		doAddPicLabel : function(nodeIds){
			//图片添加标签
			var addPicLabelDialog=$("<div id=\"addPicLabelDialog\"></div>");
			addPicLabelDialog.appendTo("body");
			$("#addPicLabelDialog").dialog({
				title : "添加标签",
				width : 450,
				height : 150,
				closed : false,
				cache : false,
				shadow : true, //显示阴影
				resizable : false, //不允许改变大小
				modal : true, //模式化窗口，锁定父窗口
				inline : false, //是否在父容器中，若不在会出现很多BUG
				queryParams: {
					"aj_ewm" : currAjEwm,
				    "mldm" : currMldm,
				    "nodeIds" : nodeIds
				},
				onClose : function(){
					addPicLabelDialog.remove();
				},
			});
			$("#addPicLabelDialog").dialog("open").dialog("refresh","${pageContext.servletContext.contextPath }/ajuan/ajzlAddPicLabelUI.do");
		},
		
		deletePicLabel : function(node){
			//删除单个文件
			var msg = "确定要删除 "+node.text.split(".jpg")[0]+"文件的标签吗？";
			$.messager.confirm('删除标签', msg, function(r){
				if(r){
					mlzlOpt.doDeletePicLabel(node.id);
				}
			});
		},
		deleteMultiPicLabel : function(nodes){
			//删除多个文件
			var msg = '确定要删除 <span style="color: red;font-weight:bolder;">所有勾选的文件</span>标签？';
			$.messager.confirm('删除标签', msg, function(r){
				if(r){
					var nodeIds = mlzlOpt.getNodeIdString(nodes);
					mlzlOpt.doDeletePicLabel(nodeIds);
				}
			});
		},
		doDeletePicLabel : function(nodeIds){
			//执行删除操作
			$.messager.progress();
			$.ajax({
				url: "${pageContext.servletContext.contextPath}/ajuan/ajzlDelPicLabel.do",
				async:true,		//是否异步请求
				cache:false,	//是否缓存
				type:"post",	//请求方式
				dataType:"text",	//返回数据类型
				data : {"nodeIds" : nodeIds},
				success:function(data){
					$.messager.progress('close');
					if(data == '"success"'){
						$('#ajzlTree').tree('reload');//刷新树目录
						$('#picLabelCurrName').combobox('reload');//重新加载下拉框
					}else{
						$.messager.alert('','删除失败','warning');
					}
				},
				error : function(){
					$.messager.progress('close');
					$.messager.alert('错误','系统异常','warning');
				}
			});
		},
		checkAllSection : function(section){
			//将指定区域的所有节点选中
			if(section[0]==undefined || section[0] == null ||
				section[1]==undefined || section[1]==null){
				return false;
			}
			if(section[0].id == section[1].id){
				return false;
			}
			var starNbbh = getNbbh(section[0]);
			var endNbbh = getNbbh(section[1]);
			var root = $('#ajzlTree').tree('getRoot');
			$('#ajzlTree').tree('uncheck',root.target);
			var rootChildren = root.children;
			if(starNbbh < endNbbh){
				mlzlOpt.doCheckAllSection(rootChildren,starNbbh,endNbbh);
			}else{
				mlzlOpt.doCheckAllSection(rootChildren,endNbbh,starNbbh);
			}
		},
		doCheckAllSection : function(rootChildren,minNbbh,maxNbbh){
			$.each(rootChildren, function(index, node){
				var currNbbh = getNbbh(node);
				if(currNbbh >= minNbbh && currNbbh <= maxNbbh){
					$('#ajzlTree').tree('check', node.target);
				}
			});
		}
	};
	$(function(){
		window.document.onkeydown = function(e){
			var theEvent = e || window.event;
			var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
			if(code == 16){
				//shift键按下
				shiftKeyDowm = true;
			}
		};
		window.document.onkeyup = function(e){
			var theEvent = e || window.event;
			var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
			if(code == 16){
				//shift键松开
				shiftKeyDowm = false;
				starEndNode[1] = null;
			}
		};
		if(isls == "false"){
			//设置dialog标题
			var currMlmc = "${zjzlInfo.mlmc}";
			if(currMlmc.length > 35){
				currMlmc = currMlmc.substring(0, 35) + "...";
			}
			var currJzType = "${currJzType}";
			var starttitle = "";
			if(currJzType == "js"){
				starttitle = "即时案卷整理： ";
			}else if(currJzType == "ls"){
				starttitle = "历史案卷整理： ";
			}
			var title = starttitle + '${zjzlInfo.pcmc}' + '--'+ currMlmc;
			$('#ajzlDialog').dialog('setTitle',title);
		}
		//标签下拉框
		$('#picLabelCurrName').combobox({
			valueField: 'id',
			textField: 'text',
			editable : false,
			prompt : '选择标签',
			url : "${pageContext.servletContext.contextPath }/ajuan/lsAjzlGetAllLab.do",
			queryParams: {
				"aj_ewm" : currAjEwm,
				"mldm" : currMldm,
				"combo4Query" : "1"
			},
			onSelect : function(record){
				currPicLabId = record.id;
				$('#ajzlTree').tree('reload');//刷新树目录
			},
			onLoadSuccess : function(){
				currPicLabId = $(this).combobox('getValue');
				$('#ajzlTree').tree('reload');//刷新树目录
			},
		});
		//工具栏添加图片
		$("#addPicBtn").linkbutton({
			iconCls: 'icon-add',
			plain : true,
			onClick : function(){
				var selectedNode = $('#ajzlTree').tree('getSelected');
				if(selectedNode == null){
					$('#ajzlTree').tree('select',$('#ajzlTree').tree('getRoot').target);
				}
				mlzlOpt.addPicDialogInit('添加图片');
			},
		});
		
		//工具栏删除图片
		$("#deletePicBtn").linkbutton({
			iconCls: 'icon-remove',
			plain : true,
			onClick : function(){
				var ckeckedNodes = $('#ajzlTree').tree('getChecked');
				if(ckeckedNodes == null || ckeckedNodes.length == 0){
					var node = $('#ajzlTree').tree('getSelected');
					$('#ajzlTree').tree('check',node.target);
					ckeckedNodes = $('#ajzlTree').tree('getChecked');
				}
				//批量删除
				mlzlOpt.deleteMultiPics(ckeckedNodes);
			},
		});
		//工具栏添加标签按钮
		$("#addPicLabelBtn").linkbutton({
			iconCls: 'icon-label-add',
			plain : true,
			onClick : function(){
				var node = $('#ajzlTree').tree('getSelected');
				$('#ajzlTree').tree('check',node.target);
				var ckeckedNodes = $('#ajzlTree').tree('getChecked');
				if(ckeckedNodes == null || ckeckedNodes.length < 2){
					//单个节点添加标签
					if(rootMlName == ckeckedNodes[0].text){
						$.messager.alert('','根目录无法添加标签','info');
						return false;
					}
					mlzlOpt.addPicLabel(ckeckedNodes[0]);
				}else{
					//批量添加标签
					mlzlOpt.addMultiPicLabel(ckeckedNodes);
				}
			},
		});
		//工具栏删除标签按钮
		$("#deletePicLabelBtn").linkbutton({
			iconCls: 'icon-label-delete',
			plain : true,
			onClick : function(){
				var node = $('#ajzlTree').tree('getSelected');
				$('#ajzlTree').tree('check',node.target);
				var ckeckedNodes = $('#ajzlTree').tree('getChecked');
				if(ckeckedNodes == null || ckeckedNodes.length < 2){
					if(rootMlName == ckeckedNodes[0].text){
						$.messager.alert('','根目录没有标签','info');
						return false;
					}
					mlzlOpt.deletePicLabel(ckeckedNodes[0]);
				}else{
					mlzlOpt.deleteMultiPicLabel(ckeckedNodes);
				}
			},
		});
		//图片整理时，工具栏上传图片按钮
		$("#lsuploadPicLabelBtn").linkbutton({
			iconCls: 'icon-add',
			plain : true,
			onClick : function(){
				var ajzlPicUploadDilog=$("<div id=\"ajzlPicUploadDilog\"></div>");
				ajzlPicUploadDilog.appendTo("body");
				$("#ajzlPicUploadDilog").dialog({
					title : '批量添加',
					width : 512,
					height : 300,
					closed : false,
					cache : false,
					shadow : true, //显示阴影
					resizable : false, //不允许改变大小
					modal : true, //模式化窗口，锁定父窗口
					inline : true, //是否在父容器中，若不在会出现很多BUG
					queryParams: {
						"aj_ewm" : currAjEwm,
					    "mldm" : currMldm,
					    "currNode_id" : currNode_id,
					    "isls" : isls
					},
					onClose : function(){
						ajzlPicUploadDilog.remove();
					},
					href : "${pageContext.servletContext.contextPath }/ajuan/lsAjzlPicUploadUI.do"
				});
			},
		});
		//初始化树目录deletePicBtn
		$('#ajzlTree').tree({
		    url : '${pageContext.servletContext.contextPath }/ajuan/lsAjzlTree.do',
		    queryParams : {
		    	"aj_ewm" : currAjEwm,
		    	"mldm" : currMldm,
		    	"zmlid" : currPicLabId,
		    	"isls" : isls
		    },
		    animate : false,//展开动画
		    lines : true,//虚线连接
		    checkbox : true,//显示复选框
		    onlyLeafCheck : false,//是否只有叶节点显示复选框
		    onBeforeLoad : function(node, param){
		    	param.zmlid = currPicLabId;//刷新标签id
		    },
		    onLoadError : function(arg){
		    	$.messager.alert('','查询目录结构出错','info');
		    },
		    onLoadSuccess : function(){
				$('#ajzlTree').tree('expandAll');
				$('#ajzlCenter').panel('clear');
				var rootNode = $('#ajzlTree').tree('getRoot');
				var rootChildren = rootNode.children;
				if(rootChildren!=null && rootChildren!=''){
					$('#ajzlTree').tree('select', rootChildren[rootChildren.length - 1].target);
				}else{
					$('#ajzlTree').tree('select',rootNode.target);
				}
			},
		    onClick: function(node){
		    	currNode_id = node.id;//获得当前节点id
		    	if($('#ajzlTree').tree('isLeaf',node.target) && node.text != rootMlName){
					var url = '${pageContext.servletContext.contextPath }/ajuan/showLsZlImg.do?currPicEwm='+currNode_id+'&isls='+isls;
					$('#ajzlCenter').panel('refresh',url);
		    	} else {
		    		//点击根节点，则展开目录
	    			$('#ajzlTree').tree('expand',node.target);
		    	}
			},
			onCheck : function(node, checked){
				currNode_id = node.id;//获得当前节点id
				$(this).tree('select',node.target);
			},
			dnd : true,//开启拖动
			onBeforeDrop : function (target, source, point)
		    {
		    	if(!$(this).tree('isLeaf', target))
		    	{//根节点不能进行拖动操作
		    		return false;
		    	}
		        if (point === 'append')
		        {//取消拖入叶节点，生成子目录的功能
		        	return false;
		        }
		    },
		    onDrop : function(target, source, point){
		    	var targetId = $(this).tree('getNode', target).id;
		    	var sourceId = source.id;
		    	$.messager.progress();
				$.ajax({
					url: "${pageContext.servletContext.contextPath}/ajuan/ajzlUpdatePicOrder.do",
					async:true,		//是否异步请求
					cache:false,	//是否缓存
					type:"post",	//请求方式
					dataType:"text",	//返回数据类型
					data : {"sourceId" : sourceId,"targetId":targetId,"point":point,"isls":isls},
					success:function(data){
						$.messager.progress('close');
						//刷新树目录
						$('#ajzlTree').tree('reload');
					},
					error : function(){
						$.messager.progress('close');
						$.messager.alert('错误','系统异常','warning');
						$('#ajzlTree').tree('reload');//刷新树目录
					}
				});
		    },
		});
	});
	</script>
  </body>
</html>
