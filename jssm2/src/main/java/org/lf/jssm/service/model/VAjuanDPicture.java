package org.lf.jssm.service.model;

/**
 * 封装v_ajuand中图片的信息
 * 
 * @author Administrator
 *
 */
public class VAjuanDPicture {
	private String mlmc;// 目录名称
	private String zml;// 子目录
	private String picEwm;// 图片二维码
	private Integer picVersion;// 图片版本号
	private String pageNumber;// 图片页次

	public String getMlmc() {
		return mlmc;
	}

	public void setMlmc(String mlmc) {
		this.mlmc = mlmc == null ? null : mlmc.trim();
	}

	public String getZml() {
		return zml;
	}

	public void setZml(String zml) {
		this.zml = zml == null ? null : zml.trim();
	}

	public String getPicEwm() {
		return picEwm;
	}

	public void setPicEwm(String picEwm) {
		this.picEwm = picEwm == null ? null : picEwm.trim();
	}

	public Integer getPicVersion() {
		return picVersion;
	}

	public void setPicVersion(Integer picVersion) {
		this.picVersion = picVersion;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber == null ? null : pageNumber.trim();
	}

}
