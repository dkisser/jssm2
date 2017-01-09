package org.lf.jssm.service.model;

public class ChuCMlpc {
    private String dm;

    private String fydm;

    private String qynd;

    private String zhdm;

    private String lx;

    private String pcmc;

    private String pym;

    private String tybz;

    private String remark;

    private String status;

    private String mc;
    

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm == null ? null : dm.trim();
    }

    public String getFydm() {
        return fydm;
    }

    public void setFydm(String fydm) {
        this.fydm = fydm == null ? null : fydm.trim();
    }

    public String getQynd() {
        return qynd;
    }

    public void setQynd(String qynd) {
        this.qynd = qynd == null ? null : qynd.trim();
    }

    public String getZhdm() {
        return zhdm;
    }

    public void setZhdm(String zhdm) {
        this.zhdm = zhdm == null ? null : zhdm.trim();
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx == null ? null : lx.trim();
    }

    public String getPcmc() {
        return pcmc;
    }

    public void setPcmc(String pcmc) {
        this.pcmc = pcmc == null ? null : pcmc.trim();
    }

    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        this.pym = pym == null ? null : pym.trim();
    }

    public String getTybz() {
        return tybz;
    }

    public void setTybz(String tybz) {
        this.tybz = tybz == null ? null : tybz.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}