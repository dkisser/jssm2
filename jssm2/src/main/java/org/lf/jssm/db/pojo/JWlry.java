package org.lf.jssm.db.pojo;

import java.util.Date;

public class JWlry {
    private String id;

    private String rymc;

    private String rylx;

    private String zjlx;

    private String gzdw;

    private String lxfs;

    private String djdn;

    private Integer creatorId;

    private Date ksrq;

    private Date jsrq;

    private String status;

    private String remark;
    
    private String print;
    
    private String zjhm;
    
    public String getZjhm() {
		return zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm == null ? null : zjhm.trim();
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print == null ? null : print.trim();
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRymc() {
        return rymc;
    }

    public void setRymc(String rymc) {
        this.rymc = rymc == null ? null : rymc.trim();
    }

    public String getRylx() {
        return rylx;
    }

    public void setRylx(String rylx) {
        this.rylx = rylx == null ? null : rylx.trim();
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx == null ? null : zjlx.trim();
    }

    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw == null ? null : gzdw.trim();
    }

    public String getLxfs() {
        return lxfs;
    }

    public void setLxfs(String lxfs) {
        this.lxfs = lxfs == null ? null : lxfs.trim();
    }

    public String getDjdn() {
        return djdn;
    }

    public void setDjdn(String djdn) {
        this.djdn = djdn == null ? null : djdn.trim();
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getKsrq() {
        return ksrq;
    }

    public void setKsrq(Date ksrq) {
        this.ksrq = ksrq;
    }

    public Date getJsrq() {
        return jsrq;
    }

    public void setJsrq(Date jsrq) {
        this.jsrq = jsrq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}