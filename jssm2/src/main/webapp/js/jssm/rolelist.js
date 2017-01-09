var objRole = {
	edit : function() {
		var divlgRolelistInfo = $("<div id=\"dlgRolelistInfo\"></div>");
		divlgRolelistInfo.appendTo("body");
		$("#dlgRolelistInfo").dialog({
					title : '修改角色信息',
					width : 512,
					height : 300,
					href : getContextPath() + "/role/roleInfo.do?isNew=" + "no",
					closed : false,
					modal : true,
					onClose : function() {
						divlgRolelistInfo.remove();
					}
				});
	},

	add : function() {
		var divlgRolelistInfo = $("<div id=\"dlgRolelistInfo\"></div>");
		divlgRolelistInfo.appendTo("body");
		$("#dlgRolelistInfo").dialog({
					title : '修改角色信息',
					width : 512,
					height : 300,
					href : getContextPath() + "/role/roleInfo.do?isNew="+ "yes",
					closed : false,
					modal : true,
					onClose : function() {
						divlgRolelistInfo.remove();
					}
				});
	}

};
			$('#roelListdialog').dialog({
						title : '角色管理',
						width : 1024,
						height : 600,
						closed : false,
						cache : false,
						shadow : true, // 显示阴影
						resizable : false, // 不允许改变大小
						modal : true, // 模式化窗口，锁定父窗口
						inline : true, // 是否在父容器中，若不在会出现很多BUG
					});

			$('#tbrolelist').datagrid({// 数据表格
				width : "100%",
				height : "100%",
				singleSelect : true,
				pagination : true,
				striped : true,
				rownumbers : true,
				toolbar : '#bar',
				pageSize : '15',
				pageList : ['15', '20'],
				url : getContextPath() + '/role/roleMeunList.do',
				onLoadSuccess : function() {// 加载完后将edit渲染为button
					$('.edit_button').linkbutton({
								iconCls : 'icon-edit',
								height : 22,
								plain : true,
							});
					$(".note").tooltip({
								trackMouse : true,
								onShow : function() {
									$(this).tooltip('tip').css({
												width : '300',
												boxShadow : '1px 1px 3px #292929'
											});
								},
							});
					
				},
				columns : [[{
							field : 'role_id',
							title : '编号',
							align : 'center',
							hidden : true,
							resizable : false
							,
						}, {
							field : 'role_name',
							title : '名称',
							align : 'center',
							width : '10%',
							resizable : false
							,
						}, {
							field : 'status',
							title : '状态',
							align : 'center',
							width : '10%',
							resizable : false,
							formatter : function(value, row, index) {
								if (value == 1) {
									return '激活';
								} else {
									return '未激活';
								}

							}
						}, {
							field : 'priv_list',
							title : '权限',
							align : 'left',
							width : '63%',
							resizable : false,
							formatter : function(value, row, index) {
								var abValue = (value == null) ? "" : value;
								var content = '<span title="' + value
										+ '" class="note">' + abValue
										+ '</span>';
								return content;
							}
						}, {
							field : 'edit',
							title : '操作',
							align : 'center',
							width : '15%',
							resizable : false,
							formatter : function(value, row, index) {
								return '<a  href="#" class="edit_button" onclick="objRole.edit()">修改</a>';
							}

						}]]

			});

