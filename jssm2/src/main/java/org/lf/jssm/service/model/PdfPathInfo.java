package org.lf.jssm.service.model;

public class PdfPathInfo {
	private String jzid;
	private String mlmc;
	private String zml;
	private String path;
	private String zmlId;
	private Integer currentPageNumber;

	public String getJzid() {
		return jzid;
	}

	public void setJzid(String jzid) {
		this.jzid = jzid;
	}

	public String getMlmc() {
		return mlmc;
	}

	public void setMlmc(String mlmc) {
		this.mlmc = mlmc;
	}
	
	public String getZml() {
		return zml;
	}

	public void setZml(String zml) {
		this.zml = zml;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getZmlId() {
		return zmlId;
	}

	public void setZmlId(String zmlId) {
		this.zmlId = zmlId;
	}

	public Integer getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(Integer currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	@Override
	public String toString() {
		return "PdfPathInfo [jzid=" + jzid + ", mlmc=" + mlmc + ", zml=" + zml + ", path=" + path + ", currentPageNumber=" + currentPageNumber + "]";
	}
	
}
