package org.lf.utils.servlet;

import org.lf.utils.StringUtils;


/**
 * 案件信息
 * 
 * @author asus
 *
 */
public class CaseInfo {

	private String ahdm;// 案件标识
	private String ah;// 案号
	private String fydm;// 法院分级码
	private String fydmms;// 法院名称
	private String dz;// 案件代字
	private String ajlx;// 案件类型
	private String larq;// 立案日期
	private String jarq;// 结案日期
	private String sxjmrq;// 审限届满日期
	private String saay;// 收案案由
	private String saayms;// 收案案由描述
	private String ayms;// 案由描述
	private String dsr;// 当事人
	private String jafs;// 结案方式
	private String jafsms;// 结案方式描述
	private String cbbm1;// 承办部门
	private String cbbmms;// 承办部门名称
	private String cbr;// 承办人代码
	private String cbrms;// 承办人名称
	private String cbrbs;// ?
	private String zt;// 案件状态
	private String ajztms;// 阶段名称
	private String spz;// 审判长
	private String spzms;// 审判长名称
	private String spzbs;// ?
	private String hytcy;// 合议庭成员
	private String sjy;// 书记员
	private String sjyms;// 书记员名称
	private String sjybs;// ?
	private String cpwsxh;// ?
	private String ysfydm;// ?
	private String ysfyms;// ?
	private String bdje;// 立案标的金额
	private String ajmc;// 案件名称
	private String sxqsrq;// ?

	private String sfzhm;// 身份证号码


	public String getSfzhm() {
		return sfzhm;
	}

	public void setSfzhm(String sfzhm) {
		this.sfzhm = sfzhm;
	}

	public String getAhdm() {
		return ahdm;
	}

	public void setAhdm(String ahdm) {
		this.ahdm = ahdm;
	}

	public String getAh() {
		return ah;
	}

	public void setAh(String ah) {
		this.ah = ah;
	}

	public String getFydm() {
		return fydm;
	}

	public void setFydm(String fydm) {
		this.fydm = fydm;
	}

	public String getFydmms() {
		return fydmms;
	}

	public void setFydmms(String fydmms) {
		this.fydmms = fydmms;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public String getAjlx() {
		return ajlx;
	}

	public void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}

	public String getLarq() {
		//对时间字符串进行格式化
		return StringUtils.strDtateFmt(larq);
	}

	public void setLarq(String larq) {
		this.larq = larq;
	}

	public String getJarq() {
		return jarq;
	}

	public void setJarq(String jarq) {
		this.jarq = jarq;
	}

	public String getSxjmrq() {
		return sxjmrq;
	}

	public void setSxjmrq(String sxjmrq) {
		this.sxjmrq = sxjmrq;
	}

	public String getSaay() {
		return saay;
	}

	public void setSaay(String saay) {
		this.saay = saay;
	}

	public String getSaayms() {
		return saayms;
	}

	public void setSaayms(String saayms) {
		this.saayms = saayms;
	}

	public String getAyms() {
		return ayms;
	}

	public void setAyms(String ayms) {
		this.ayms = ayms;
	}

	public String getDsr() {
		return dsr;
	}

	public void setDsr(String dsr) {
		this.dsr = dsr;
	}

	public String getJafs() {
		return jafs;
	}

	public void setJafs(String jafs) {
		this.jafs = jafs;
	}

	public String getJafsms() {
		return jafsms;
	}

	public void setJafsms(String jafsms) {
		this.jafsms = jafsms;
	}

	public String getCbbm1() {
		return cbbm1;
	}

	public void setCbbm1(String cbbm1) {
		this.cbbm1 = cbbm1;
	}

	public String getCbbmms() {
		return cbbmms;
	}

	public void setCbbmms(String cbbmms) {
		this.cbbmms = cbbmms;
	}

	public String getCbr() {
		return cbr;
	}

	public void setCbr(String cbr) {
		this.cbr = cbr;
	}

	public String getCbrms() {
		return cbrms;
	}

	public void setCbrms(String cbrms) {
		this.cbrms = cbrms;
	}

	public String getCbrbs() {
		return cbrbs;
	}

	public void setCbrbs(String cbrbs) {
		this.cbrbs = cbrbs;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getAjztms() {
		return ajztms;
	}

	public void setAjztms(String ajztms) {
		this.ajztms = ajztms;
	}

	public String getSpz() {
		return spz;
	}

	public void setSpz(String spz) {
		this.spz = spz;
	}

	public String getSpzms() {
		return spzms;
	}

	public void setSpzms(String spzms) {
		this.spzms = spzms;
	}

	public String getSpzbs() {
		return spzbs;
	}

	public void setSpzbs(String spzbs) {
		this.spzbs = spzbs;
	}

	public String getHytcy() {
		return hytcy;
	}

	public void setHytcy(String hytcy) {
		this.hytcy = hytcy;
	}

	public String getSjy() {
		return sjy;
	}

	public void setSjy(String sjy) {
		this.sjy = sjy;
	}

	public String getSjyms() {
		return sjyms;
	}

	public void setSjyms(String sjyms) {
		this.sjyms = sjyms;
	}

	public String getSjybs() {
		return sjybs;
	}

	public void setSjybs(String sjybs) {
		this.sjybs = sjybs;
	}

	public String getCpwsxh() {
		return cpwsxh;
	}

	public void setCpwsxh(String cpwsxh) {
		this.cpwsxh = cpwsxh;
	}

	public String getYsfydm() {
		return ysfydm;
	}

	public void setYsfydm(String ysfydm) {
		this.ysfydm = ysfydm;
	}

	public String getYsfyms() {
		return ysfyms;
	}

	public void setYsfyms(String ysfyms) {
		this.ysfyms = ysfyms;
	}

	public String getBdje() {
		return bdje;
	}

	public void setBdje(String bdje) {
		this.bdje = bdje;
	}

	public String getAjmc() {
		return ajmc;
	}

	public void setAjmc(String ajmc) {
		this.ajmc = ajmc;
	}

	public String getSxqsrq() {
		return sxqsrq;
	}

	public void setSxqsrq(String sxqsrq) {
		this.sxqsrq = sxqsrq;
	}

}