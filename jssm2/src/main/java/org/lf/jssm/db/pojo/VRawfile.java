package org.lf.jssm.db.pojo;

import java.util.Date;

public class VRawfile {
	private Integer scanerId;

	private Date modifyDate;

	private String year;

	private String month;

	private String day;

	private String hour;

	private String rawJpg;

	private Date startDate;

	private Date endDate;

	private Integer fileVersion;// 文件版本

	public Integer getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(Integer fileVersion) {
		this.fileVersion = fileVersion;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getScanerId() {
		return scanerId;
	}

	public void setScanerId(Integer scanerId) {
		this.scanerId = scanerId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year == null ? null : year.trim();
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month == null ? null : month.trim();
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day == null ? null : day.trim();
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour == null ? null : hour.trim();
	}

	public String getRawJpg() {
		return rawJpg;
	}

	public void setRawJpg(String rawJpg) {
		this.rawJpg = rawJpg == null ? null : rawJpg.trim();
	}
}