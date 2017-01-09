package org.lf.jssm.service.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

//此model用于  卷宗操作日志
public class LJzParam {
	private String id;
	private String jzewm;
	private String operation;
	// 表中无此字段，辅助日志查询
	private Date beginDate;
	// 表中无此字段，辅助日志查询
	private Date endDate;
	private Integer operator_id;
	private Logger logger = Logger.getLogger(JZQueryParam.class);
	private String year;
	private String spzh;
	private String ajbh;
	private String jzlx;
	private String aqjb;
	private String dsr;
	private String ayms;
	private String jzzt;
	private String gdhs;
	private String larqFStr;
	private String larqTStr;
	private String jarqFStr;
	private String jarqTStr;
	private Date larqF;
	private Date larqT;
	private Date jarqF;
	private Date jarqT;
	private String fmEwm;
	private String bgqx;
	private String gdnf;
	private String uid;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		Date date = null;
		try {
			if (beginDate != null) {
				beginDate = beginDate + " " + "00:00:00";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(beginDate);
			}
		} catch (ParseException e) {
			logger.info("beginDate 日期转换出错！");
		}
		this.beginDate = date;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		Date date = null;
		try {
			if (endDate != null) {
				endDate = endDate + " " + "23:59:59";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(endDate);
			}
		} catch (ParseException e) {
			logger.info("endDate 日期转换出错！");
		}
		this.endDate = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJzewm() {
		return jzewm;
	}

	public void setJzewm(String jzewm) {
		this.jzewm = jzewm.trim() == "" ? null : jzewm;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation.trim() == "" ? null : operation;
	}

	public Integer getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(Integer operator_id) {
		this.operator_id = operator_id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGdnf() {
		return gdnf;
	}

	public void setGdnf(String gdnf) {
		this.gdnf = gdnf.trim().length() == 0 ? null : gdnf;
	}

	public String getBgqx() {
		return bgqx;
	}

	public void setBgqx(String bgqx) {

		this.bgqx = bgqx.trim().length() == 0 ? null : bgqx;
	}

	public Date getLarqF() {
		return larqF;
	}

	public Date getLarqT() {
		return larqT;
	}

	public Date getJarqF() {
		return jarqF;
	}

	public Date getJarqT() {
		return jarqT;
	}

	public void setLarqF(Date larqF) {
		this.larqF = larqF;
	}

	public void setLarqT(Date larqT) {
		this.larqT = larqT;
	}

	public void setJarqF(Date jarqF) {
		this.jarqF = jarqF;
	}

	public void setJarqT(Date jarqT) {
		this.jarqT = jarqT;
	}

	public String getLarqFStr() {
		return larqFStr;
	}

	public void setLarqFStr(String larqFStr) {
		this.larqFStr = larqFStr.trim().length() == 0 ? null : larqFStr;
		if (larqFStr != null) {
			this.larqF = convertDate(larqFStr);
		}
	}

	public String getLarqTStr() {
		return larqTStr;
	}

	public void setLarqTStr(String larqTStr) {
		this.larqTStr = larqTStr.trim().length() == 0 ? null : larqTStr;
		if (larqTStr != null) {
			this.larqT = convertDate(larqTStr);
		}
	}

	public String getJarqFStr() {
		return jarqFStr;
	}

	public void setJarqFStr(String jarqFStr) {
		this.jarqFStr = jarqFStr.trim().length() == 0 ? null : jarqFStr;
		if (jarqFStr != null) {
			this.jarqF = convertDate(jarqFStr);
		}
	}

	public String getJarqTStr() {
		return jarqTStr;
	}

	public void setJarqTStr(String jarqTStr) {
		this.jarqTStr = jarqTStr.trim().length() == 0 ? null : jarqTStr;
		if (jarqTStr != null) {
			this.jarqT = convertDate(jarqTStr);
		}
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year.trim().length() == 0 ? null : year;
	}

	public String getSpzh() {
		return spzh;
	}

	public void setSpzh(String spzh) {
		this.spzh = spzh.trim().length() == 0 ? null : spzh;
	}

	public String getAjbh() {
		return ajbh;
	}

	public void setAjbh(String ajbh) {
		this.ajbh = ajbh.trim().length() == 0 ? null : ajbh;
	}

	public String getJzlx() {
		return jzlx;
	}

	public void setJzlx(String jzlx) {
		this.jzlx = jzlx.trim().length() == 0 ? null : jzlx;
	}

	public String getAqjb() {
		return aqjb;
	}

	public void setAqjb(String aqjb) {
		this.aqjb = aqjb.trim().length() == 0 ? null : aqjb;
	}

	public String getDsr() {
		return dsr;
	}

	public void setDsr(String dsr) {
		this.dsr = dsr.trim().length() == 0 ? null : dsr;
	}

	public String getAyms() {
		return ayms;
	}

	public void setAyms(String ayms) {
		this.ayms = ayms.trim().length() == 0 ? null : ayms;
	}

	public String getJzzt() {
		return jzzt;
	}

	public void setJzzt(String jzzt) {
		this.jzzt = jzzt.trim().length() == 0 ? null : (jzzt.equals("归档") ? "1" : "0");
	}

	public String getGdhs() {
		return gdhs;
	}

	public void setGdhs(String gdhs) {
		this.gdhs = gdhs.trim().length() == 0 ? null : gdhs;
	}

	public String getFmEwm() {
		return fmEwm;
	}

	public void setFmEwm(String fmEwm) {
		this.fmEwm = fmEwm.trim().length() == 0 ? null : fmEwm;
	}

	private Date convertDate(String dateStr) {
		if (dateStr == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.info("convertDate 日期转换出错！");
		}
		return date;
	}
}
