package org.lf.jssm.db.pojo;

import java.util.Date;

public class JAjuan {
    private String jzid;

    private String ewm;
    
    private String pcdm;

    private String mc;

    private Integer creatorId;

    private Date createDate;

    private Short status;

    private String pym;

    private String remark;


    public String getJzid() {
		return jzid;
	}

	public void setJzid(String jzid) {
		this.jzid = jzid;
	}

	public String getEwm() {
		return ewm;
	}

	public void setEwm(String ewm) {
		this.ewm = ewm;
	}

	public String getPcdm() {
        return pcdm;
    }

    public void setPcdm(String pcdm) {
        this.pcdm = pcdm == null ? null : pcdm.trim();
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc == null ? null : mc.trim();
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        this.pym = pym == null ? null : pym.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}