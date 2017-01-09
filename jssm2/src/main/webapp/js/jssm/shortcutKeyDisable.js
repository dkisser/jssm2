// 回车直接提交
document.onkeydown = function(e) {
	var event = window.event || e; //兼容火狐浏览器
//	console.log(event);
	if (e.altKey && event.keyCode == 49) {
		//禁用alt+1
		event.returnValue=false;
	}
	if (e.altKey && event.keyCode == 50) {
		//禁用alt+2
		event.returnValue=false;
	}
	if (e.altKey && event.keyCode == 51) {
		//禁用alt+3
		event.returnValue=false;
	}
	if (event.keyCode == 32) {
		//禁用空格
		event.returnValue=false;
	}
	if (e.altKey) {
		//禁用alt键
		event.returnValue=false;
		return false;
	}
	if(event.keyCode == 13){
		//回车保存
		confirmImg();
	}
};