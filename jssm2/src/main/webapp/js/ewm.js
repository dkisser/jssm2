/**
 * 将Date对象解析成yyyyMMdd格式的字符串
 * @param date
 * @returns {String}
 */
function getDateString(date) {
	try{
		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
			+ (date.getMonth() + 1);
		var year = date.getFullYear();
		return "" + year + month + day;
	}catch(err){
		return "error";
	}
};

/**
 * 用途：判断是否是日期
	输入：date：日期；fmt：日期格式
	返回：如果通过验证返回true,否则返回false
 * @param date
 * @param fmt
 * @returns {Boolean}
 */
function isDate( date, fmt ) {
	if (fmt==null) fmt="yyyyMMdd";
	var yIndex = fmt.indexOf("yyyy");
	if(yIndex==-1) return false;
	var year = date.substring(yIndex,yIndex+4);
	var mIndex = fmt.indexOf("MM");
	if(mIndex==-1) return false;
	var month = date.substring(mIndex,mIndex+2);
	var dIndex = fmt.indexOf("dd");
	if(dIndex==-1) return false;
	var day = date.substring(dIndex,dIndex+2);
	if(!isNumber(year)||year>"2100" || year< "1900") return false;
	if(!isNumber(month)||month>"12" || month< "01") return false;
	if(day>getMaxDay(year,month) || day< "01") return false;
	return true;
};
/**
 * 获得当月的最大天数
 * @param year
 * @param month
 * @returns {String}
 */
function getMaxDay(year,month) {
	if(month==4||month==6||month==9||month==11)
	return "30";
	if(month==2)
	if(year%4==0&&year%100!=0 || year%400==0)
	return "29";
	else
	return "28";
	return "31";
};
/**
 * 用途：检查输入字符串是否符合正整数格式
	输入：
	s：字符串
	返回：
	如果通过验证返回true,否则返回false
 * @param s
 * @returns {Boolean}
 */
function isNumber( s ){
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (s.search(re) != -1) {
	return true;
	} else {
	return false;
	}
};
/**
 * 截取二维码前8位日期字符串
 * @param ewm
 * @returns
 */
function getDate(ewm){
	return ewm.substr(0,8);
};
/**
 * 截取二维码中的法院代码字符串
 * @param ewm
 * @returns
 */
function getFYDM(ewm){
	return ewm.substr(8,4);
};
/**
 * 截取二维码5位序号
 * @param ewm
 * @returns
 */
function getEwmId(ewm){
	return ewm.substr(12,5);
};
/**
 * 截取二维码最后一位，即二维码类型
 * @param ewm
 * @returns
 */
function getEwmType(ewm){
	return ewm.substr(ewm.length-1,1);
};

/**
 * 判断指定二维码是否有效
 * 入参说明ewm：为指定二维码
 * @param ewm
 * @param ewmType
 * @returns {Number}
 */
function isValidEwm(ewm,ewmType){
	if(ewm==null || ewm==undefined || ewm.length != 18){
		return 102;//位数不是18位
	}
	var dateStr = getDate(ewm);//去头八位
	if(!isDate(dateStr,"yyyyMMdd")){
		return 103;//头8位不是时间格式
	}
	if(ewmType != null && ewmType != ""){
		//判断二维码最后一位是否为指定的类型
		var type = getEwmType(ewm);
		if(type != ewmType)
		return 104;
	}
	return 101;//正确
};


