//提示音js
$(function(){
	//载入声音文件
	$('<audio id="tipAudioError"><source id="tipAudioErrorSrc" src="" type="audio/mpeg"></audio>').appendTo('body');
	$('<audio id="tipAudioSuccess"><source id="tipAudioSuccessSrc" src="" type="audio/mpeg"></audio>').appendTo('body');
	var tipAudioErrorUrl = getContextPath() + '/etcs/error.mp3';
	var tipAudioSuccessUrl = getContextPath() + '/etcs/success.mp3';
	$('#tipAudioErrorSrc').attr('src',tipAudioErrorUrl);
	$('#tipAudioSuccessSrc').attr('src',tipAudioSuccessUrl);
});
//播放失败提示音
function playTipAudioError(){
	 $('#tipAudioError')[0].play();
};
//播放成功提示音
function playTipAudioSuccess(){
	$('#tipAudioSuccess')[0].play();
};