document.onkeydown = function(e) {
	var event = window.event || e; //兼容火狐浏览器
//	console.log(event);
	if (e.altKey && event.keyCode == 49) {
		//启动高拍仪，alt+1
		StartDevice();
	}
	if (e.altKey && event.keyCode == 50) {
		//扫描，alt+2
		DoCaptureImage();
	}
	if (e.altKey && event.keyCode == 51) {
		//关闭高拍仪，alt+3
		CloseDevice();
	}
	if (e.altKey) {
		//禁用alt键
		event.returnValue=false;
		return false;
	}
	if (event.keyCode == 32) {
		//扫描，空格键
		DoCaptureImage();
	}
};