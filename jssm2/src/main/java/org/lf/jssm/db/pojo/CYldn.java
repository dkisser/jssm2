package org.lf.jssm.db.pojo;

public class CYldn {
    private String dm;

    private String mc;

    private String pym;

    private String tybz;

    private String remark;

    private String ip;

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm == null ? null : dm.trim();
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc == null ? null : mc.trim();
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}