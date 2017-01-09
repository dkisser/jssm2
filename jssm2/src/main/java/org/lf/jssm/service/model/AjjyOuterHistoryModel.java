package org.lf.jssm.service.model;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 案卷借阅历史查询
 * @author Administrator
 *
 */
public class AjjyOuterHistoryModel {
	private String rymc;
	private Date ksrq;
	private String yldn;
	private String ah;
	private String jzlx;
	private String jh;
	private String aqjb;
	private String status;
	private Date gdrq;
	private String gdhs;
	private String zjhm;
	private String jy_ksrq;
	private String jy_gdrq;
	private String print;
	
	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getJy_ksrq() {
		return jy_ksrq;
	}

	public void setJy_ksrq(String jy_ksrq) {
		this.jy_ksrq = jy_ksrq;
	}

	public String getJy_gdrq() {
		return jy_gdrq;
	}

	public void setJy_gdrq(String jy_gdrq) {
		this.jy_gdrq = jy_gdrq;
	}

	public String getZjhm() {
		return zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getRymc() {
		return rymc;
	}

	public void setRymc(String rymc) {
		this.rymc = rymc;
	}

	public Date getKsrq() {
		return ksrq;
	}

	public void setKsrq(Date ksrq) {
		this.ksrq = ksrq;
		this.jy_ksrq = convertDate(ksrq);
	}

	public String getYldn() {
		return yldn;
	}

	public void setYldn(String yldn) {
		this.yldn = yldn;
	}

	public String getAh() {
		return ah;
	}

	public void setAh(String ah) {
		this.ah = ah;
	}

	public String getJzlx() {
		return jzlx;
	}

	public void setJzlx(String jzlx) {
		this.jzlx = jzlx;
	}

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public String getAqjb() {
		return aqjb;
	}

	public void setAqjb(String aqjb) {
		this.aqjb = aqjb;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Date getGdrq() {
		return gdrq;
	}

	public void setGdrq(Date gdrq) {
		this.gdrq = gdrq;
		if(gdrq != null){
			this.jy_gdrq = convertDate(this.gdrq);
		}
	}

	public String getGdhs() {
		return gdhs;
	}

	public void setGdhs(String gdhs) {
		this.gdhs = gdhs;
	}

	private String convertDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		return dateStr;
	}

}
