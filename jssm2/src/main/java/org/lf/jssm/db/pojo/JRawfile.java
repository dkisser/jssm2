package org.lf.jssm.db.pojo;

import java.util.Date;

/**
 * 原始文档实体类
 * 
 * @author sunwill
 *
 */
public class JRawfile {
	private String ewm;// 二维码

	private String scanYear;// 扫描年

	private String scanMonth;// 扫描月

	private String scanDay;// 扫描日

	private Long fileSize;// 文件大小

	private String fileSuffix;// 文件后缀

	private Date modifyDate;// 修改日期

	private Integer fileStatus;// 状态，1：临时存储，2：存储，3：归档

	private Integer scanerId;// 扫描人id

	private String errorMsg;// 错误信息

	private Integer fileVersion;// 文件版本

	public Integer getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(Integer fileVersion) {
		this.fileVersion = fileVersion;
	}

	public Integer getScanerId() {
		return scanerId;
	}

	public void setScanerId(Integer scanerId) {
		this.scanerId = scanerId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getEwm() {
		return ewm;
	}

	public void setEwm(String ewm) {
		this.ewm = ewm == null ? null : ewm.trim();
	}

	public String getScanYear() {
		return scanYear;
	}

	public void setScanYear(String scanYear) {
		this.scanYear = scanYear == null ? null : scanYear.trim();
	}

	public String getScanMonth() {
		return scanMonth;
	}

	public void setScanMonth(String scanMonth) {
		this.scanMonth = scanMonth == null ? null : scanMonth.trim();
	}

	public String getScanDay() {
		return scanDay;
	}

	public void setScanDay(String scanDay) {
		this.scanDay = scanDay == null ? null : scanDay.trim();
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix == null ? null : fileSuffix.trim();
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
	}
}