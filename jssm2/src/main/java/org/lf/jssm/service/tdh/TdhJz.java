package org.lf.jssm.service.tdh;

/**
 * 通达海卷宗信息实体类
 * 
 * @author sunwill
 *
 */
public class TdhJz {
	private String ewm;
	private String jzmc;
	private String ah;
	private Integer jh;
	private String jzlx;

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

	public Integer getJh() {
		return jh;
	}

	public void setJh(Integer jh) {
		this.jh = jh;
	}

	public String getJzlx() {
		return jzlx;
	}

	public void setJzlx(String jzlx) {
		this.jzlx = jzlx == null ? null : jzlx.trim();
	}

}