package org.lf.jssm.service.model;
/**
 * 封装批次代码和案卷二维码信息
 * @author Administrator
 *
 */
public class AJuanCode {
	private String pcdm;
	private String aj_ewm;
	
	public String getPcdm() {
		return pcdm;
	}
	public void setPcdm(String pcdm) {
		this.pcdm = pcdm;
	}
	public String getAj_ewm() {
		return aj_ewm;
	}
	public void setAj_ewm(String aj_ewm) {
		this.aj_ewm = aj_ewm;
	}
}
