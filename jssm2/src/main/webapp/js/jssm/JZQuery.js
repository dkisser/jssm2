function jzQueryInit() {
	/**扩展验证框功能
	 *
	 */
	$.extend($.fn.validatebox.defaults.rules, {
				ewmValidatebox : {
					validator : function(value, param) {
						var pat = /^\d{17}F$/;
						return pat.test(value);
					},
					message : '请输入18位长以F结尾的二维码'
				}
				,
			});

	/*
		获取当前的年月日信息
	 */
	var currentDate = new Date();
	var year = currentDate.getFullYear();
	var month = currentDate.getMonth() + 1;
	var day = currentDate.getDate();
	var formDate = (year - 5) + "-" + month + "-" + day;
	var toDate = year + "-" + month + "-" + day;

	$("#query_yearIpt").numberbox({
				validType : 'length[4,4]',
				invalidMessage : '请输入有效年份！',
				width : 80
				,
			});

	$("#query_cpzhIpt").combobox({
				width : 140,
				valueField : 'text',
				textField : 'text',
				panelMaxHeight : 81,
				url : getContextPath() + "/query/query_ahzInfo.do"
				,
			});

	$("#query_bhIpt").numberbox({
				width : 100
				,
			});

	$("#query_jzlxIpt").combobox({
				width : 80,
				valueField : 'text',
				textField : 'text',
				panelHeight : 50,
				editable : false,
				panelMaxHeight : 81,
				url : getContextPath() + "/query/query_jzlxInfo.do"
				,
			});

	$("#query_aqjbIpt").combobox({
				width : 80,
				valueField : 'text',
				textField : 'text',
				editable : false,
				panelMaxHeight : 81,
				url : getContextPath() + "/query/query_aqjbInfo.do"
				,
			});

	$("#query_bgqxIpt").combobox({
				width : 60,
				valueField : 'text',
				textField : 'text',
				editable : false,
				panelMaxHeight : 81,
				url : getContextPath() + "/query/query_bgqxInfo.do"
				,
			});

	$("#query_dsrIpt").textbox({
				width : 80
				,
			});

	$("#query_aymsIpt").textbox({
				width : 140
				,
			});

	$("#query_jzztIpt").combobox({
				width : 80,
				valueField : 'text',
				textField : 'text',
				panelHeight : 50,
				editable : false,
				panelMaxHeight : 81,
				url : getContextPath() + "/query/query_jzztInfo.do"
				,
			});

	$("#query_gdnfIpt").numberbox({
				width : 100,
				validType : 'length[4,4]',
				invalidMessage : '请输入有效年份！'
				,
			});

	$("#query_gdhsIpt").numberbox({
				width : 80,
				validType : 'length[1,4]',
				invalidMessage : '请输入4位以内有效数字！'
				,
			});

	$("#query_larqFIpt").datebox({
				editable : false,
				value : formDate,
				width : 100,
				onChange : function(newValue, oldValue) {
					var date = new Date(newValue);
					if (date.getTime() > currentDate.getTime()) {
						$.messager.alert('提示', '选择的日期不能超过当前日期,请重新选择');
						$(this).datebox('setValue', formDate);
						return false;
					}
					var larqT = $("#query_larqTIpt").datebox('getValue');
					var larqTDate = new Date(larqT);
					if (date.getTime() > larqTDate.getTime()) {
						$.messager.alert('提示', '立案起始时间必须在结束时间之前');
						$(this).datebox('setValue', formDate);
					}
				}
			});

	$("#query_larqTIpt").datebox({
				width : 100,
				value : toDate,
				editable : false,
				onChange : function(newValue, oldValue) {
					var date = new Date(newValue);
					if (date.getTime() > currentDate.getTime()) {
						$.messager.alert('提示', '选择的日期不能超过当前日期,请重新选择');
						$(this).datebox('setValue', toDate);
						return false;
					}
					var larqF = $("#query_larqFIpt").datebox('getValue');
					var larqFDate = new Date(larqF);
					if (date.getTime() < larqFDate.getTime()) {
						$.messager.alert('提示', '立案结束时间必须在起始时间之后');
						$(this).datebox('setValue', toDate);
						return false;
					}
				}
			});

	$("#query_jarqFIpt").datebox({
				editable : false,
				width : 100,
				value : formDate,
				onChange : function(newValue, oldValue) {
					var date = new Date(newValue);
					if (date.getTime() > currentDate.getTime()) {
						$.messager.alert('提示', '选择的日期不能超过当前日期,请重新选择');
						$(this).datebox('setValue', formDate);
						return false;
					}
					var jarqT = $("#query_jarqTIpt").datebox('getValue');
					var jarqTDate = new Date(jarqT);
					if (date.getTime() > jarqTDate.getTime()) {
						$.messager.alert('提示', '结案起始时间必须在结束时间之前');
						$(this).datebox('setValue', formDate);
						return false;
					}
				}
			});

	$("#query_jarqTIpt").datebox({
				editable : false,
				width : 100,
				value : toDate,
				onChange : function(newValue, oldValue) {
					var date = new Date(newValue);
					if (date.getTime() > currentDate.getTime()) {
						$.messager.alert('提示', '选择的日期不能超过当前日期,请重新选择');
						$(this).datebox('setValue', toDate);
						return false;
					}
					var jarqF = $("#query_jarqFIpt").datebox('getValue');
					var jarqFDate = new Date(jarqF);
					if (date.getTime() < jarqFDate.getTime()) {
						$.messager.alert('提示', '结案结束时间必须在起始时间之后');
						$(this).datebox('setValue', toDate);
						return false;
					}
				}
			});

	$("#query_fmewmIpt").textbox({
				validType : 'ewmValidatebox',
				width : 170
				,
			});

}

/**
 * 回车事件
 * func 为回车事件的回调函数
 * @param {} func
 */
function enterEvent(func) {
	//获得页面中id为query_form_Submit下所有的input框以id为query_开头的dom对象数组
	var input = $('#query_form_Submit input[id^="query_"]');
	$.each( input, function(index,htmlObject){
		//遍历每个dom对象并转化为jq对象设置回车事件
	  $(htmlObject).textbox('textbox').keydown(function(e){
	  		if(e.keyCode == 13){
	  			func();
	  		}
	  })
	});
}
function JzQueryReset() {
	$("#query_yearIpt").numberbox('clear');
	$("#query_cpzhIpt").combobox('clear');
	$("#query_bhIpt").numberbox('clear');
	$("#query_jzlxIpt").combobox('clear');
	$("#query_aqjbIpt").combobox('clear');
	$("#query_bgqxIpt").combobox('clear');
	$("#query_dsrIpt").textbox('clear');
	$("#query_aymsIpt").textbox('clear');
	$("#query_jzztIpt").combobox('clear');
	$("#query_gdnfIpt").numberbox('clear');
	$("#query_gdhsIpt").numberbox('clear');
	$("#query_larqFIpt").datebox('reset');
	$("#query_larqTIpt").datebox('reset');
	$("#query_jarqFIpt").datebox('reset');
	$("#query_jarqTIpt").datebox('reset');
	$("#query_fmewmIpt").textbox('clear');
}
