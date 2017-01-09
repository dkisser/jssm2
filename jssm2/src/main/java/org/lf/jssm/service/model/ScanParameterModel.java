package org.lf.jssm.service.model;

/**
 * 扫描参数实体类
 * 
 * @author sunwill
 *
 */
public class ScanParameterModel {

	private Integer scanerId;// 扫描用户id
	private String baseImgPath;// 本地图片存储路径
	private String scanDefaultCover;// 是否提示覆盖，1：是，2：否
	private String scanIdegree;// 镜头旋转角度
	private String imgPreview;// 是否进行图片预览，1：是，2：否
	private String scanningModel;// 扫描模式，1：手动扫描，2：自动扫描
	private String scanInterval;// 自动扫描时，两次扫描之间的时间间隔，单位秒

	public String getScanInterval() {
		return scanInterval;
	}

	public void setScanInterval(String scanInterval) {
		this.scanInterval = scanInterval;
	}

	public String getScanningModel() {
		return scanningModel;
	}

	public void setScanningModel(String scanningModel) {
		this.scanningModel = scanningModel;
	}

	public String getImgPreview() {
		return imgPreview;
	}

	public void setImgPreview(String imgPreview) {
		this.imgPreview = imgPreview;
	}

	public Integer getScanerId() {
		return scanerId;
	}

	public void setScanerId(Integer scanerId) {
		this.scanerId = scanerId;
	}

	public String getBaseImgPath() {
		return baseImgPath;
	}

	public void setBaseImgPath(String baseImgPath) {
		this.baseImgPath = baseImgPath;
	}

	public String getScanDefaultCover() {
		return scanDefaultCover;
	}

	public void setScanDefaultCover(String scanDefaultCover) {
		this.scanDefaultCover = scanDefaultCover;
	}

	public String getScanIdegree() {
		return scanIdegree;
	}

	public void setScanIdegree(String scanIdegree) {
		this.scanIdegree = scanIdegree;
	}

}
