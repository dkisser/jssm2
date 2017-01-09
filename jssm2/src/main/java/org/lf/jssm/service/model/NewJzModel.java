package org.lf.jssm.service.model;

import java.util.Date;

/**
 * 新建卷宗模型
 * 
 * @author sunwill
 *
 */
public class NewJzModel {
	private String id;
	private String ewm;// 卷宗二维码
	private String jzmc;
	private String ah;
	private String spcx1;
	private String spjg1;
	private String spcx2;
	private String spjg2;
	private String gdrq4Db;// 接收DateBox数据
	private Date gdrq;// 储存数据库用
	private Integer gdhs;
	private String yjfl;
	private String jbdm;
	private String qxdm;
	private Integer jh;
	private String tjlb;
	private String jzlx;
	private Integer status;
	private Integer creator_id;
	private Date create_date;
	private String tybz;
	private String remark;
	private String zlfs;// 整理方式
	private Integer syym;// 首页页码
	private String pcdm;// 目录批次代码
	private String pcmc;// 目录批次名称
	private String ajewm;// 目录二维码

	public String getPcmc() {
		return pcmc;
	}

	public void setPcmc(String pcmc) {
		this.pcmc = pcmc == null ? null : pcmc.trim();
	}

	public String getAjewm() {
		return ajewm;
	}

	public void setAjewm(String ajewm) {
		this.ajewm = ajewm == null ? null : ajewm.trim();
	}

	public String getPcdm() {
		return pcdm;
	}

	public void setPcdm(String pcdm) {
		this.pcdm = pcdm == null ? null : pcdm.trim();
	}

	public String getGdrq4Db() {
		return gdrq4Db;
	}

	public void setGdrq4Db(String gdrq4Db) {
		this.gdrq4Db = gdrq4Db == null ? null : gdrq4Db.trim();
	}

	public Date getGdrq() {
		return gdrq;
	}

	public void setGdrq(Date gdrq) {
		this.gdrq = gdrq;
	}

	public Integer getSyym() {
		return syym;
	}

	public void setSyym(Integer syym) {
		this.syym = syym;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getEwm() {
		return ewm;
	}

	public void setEwm(String ewm) {
		this.ewm = ewm == null ? null : ewm.trim();
	}

	public String getJzmc() {
		return jzmc;
	}

	public void setJzmc(String jzmc) {
		this.jzmc = jzmc == null ? null : jzmc.trim();
	}

	public String getAh() {
		return ah;
	}

	public void setAh(String ah) {
		this.ah = ah == null ? null : ah.trim();
	}

	public String getSpcx1() {
		return spcx1;
	}

	public void setSpcx1(String spcx1) {
		this.spcx1 = spcx1 == null ? null : spcx1.trim();
	}

	public String getSpjg1() {
		return spjg1;
	}

	public void setSpjg1(String spjg1) {
		this.spjg1 = spjg1 == null ? null : spjg1.trim();
	}

	public String getSpcx2() {
		return spcx2;
	}

	public void setSpcx2(String spcx2) {
		this.spcx2 = spcx2 == null ? null : spcx2.trim();
	}

	public String getSpjg2() {
		return spjg2;
	}

	public void setSpjg2(String spjg2) {
		this.spjg2 = spjg2 == null ? null : spjg2.trim();
	}

	public Integer getGdhs() {
		return gdhs;
	}

	public void setGdhs(Integer gdhs) {
		this.gdhs = gdhs;
	}

	public String getYjfl() {
		return yjfl;
	}

	public void setYjfl(String yjfl) {
		this.yjfl = yjfl == null ? null : yjfl.trim();
	}

	public String getJbdm() {
		return jbdm;
	}

	public void setJbdm(String jbdm) {
		this.jbdm = jbdm == null ? null : jbdm.trim();
	}

	public String getQxdm() {
		return qxdm;
	}

	public void setQxdm(String qxdm) {
		this.qxdm = qxdm == null ? null : qxdm.trim();
	}

	public Integer getJh() {
		return jh;
	}

	public void setJh(Integer jh) {
		this.jh = jh;
	}

	public String getTjlb() {
		return tjlb;
	}

	public void setTjlb(String tjlb) {
		this.tjlb = tjlb == null ? null : tjlb.trim();
	}

	public String getJzlx() {
		return jzlx;
	}

	public void setJzlx(String jzlx) {
		this.jzlx = jzlx == null ? null : jzlx.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(Integer creator_id) {
		this.creator_id = creator_id;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getTybz() {
		return tybz;
	}

	public void setTybz(String tybz) {
		this.tybz = tybz == null ? null : tybz.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getZlfs() {
		return zlfs;
	}

	public void setZlfs(String zlfs) {
		this.zlfs = zlfs == null ? null : zlfs.trim();
	}
}
