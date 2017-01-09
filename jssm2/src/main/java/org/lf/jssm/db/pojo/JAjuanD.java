package org.lf.jssm.db.pojo;

/**
 * 案卷整理实体类
 * 
 * @author sunwill
 *
 */
public class JAjuanD {
	private String ewm;

	private String aj_ewm;

	private String mldm;

	private String zmlid;

	private String zml;

	private Integer nbbh;

	private Integer startNbbh;// 起始内部编号

	private Integer endNbbh;// 结束内部编号

	public Integer getStartNbbh() {
		return startNbbh;
	}

	public void setStartNbbh(Integer startNbbh) {
		this.startNbbh = startNbbh;
	}

	public Integer getEndNbbh() {
		return endNbbh;
	}

	public void setEndNbbh(Integer endNbbh) {
		this.endNbbh = endNbbh;
	}

	public String getEwm() {
		return ewm;
	}

	public void setEwm(String ewm) {
		this.ewm = ewm == null ? null : ewm.trim();
	}

	public String getAj_ewm() {
		return aj_ewm;
	}

	public void setAj_ewm(String aj_ewm) {
		this.aj_ewm = aj_ewm == null ? null : aj_ewm.trim();
	}

	public String getMldm() {
		return mldm;
	}

	public void setMldm(String mldm) {
		this.mldm = mldm == null ? null : mldm.trim();
	}

	public String getZmlid() {
		return zmlid;
	}

	public void setZmlid(String zmlid) {
		this.zmlid = zmlid == null ? null : zmlid.trim();
	}

	public String getZml() {
		return zml;
	}

	public void setZml(String zml) {
		this.zml = zml == null ? null : zml.trim();
	}

	public Integer getNbbh() {
		return nbbh;
	}

	public void setNbbh(Integer nbbh) {
		this.nbbh = nbbh;
	}
}