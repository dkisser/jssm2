package org.lf.jssm.db.pojo;

public class CSpzh {
    private String dm;

    private String yjfl;

    private String zh;

    private String pym;

    private String tybz;

    private String remark;

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm == null ? null : dm.trim();
    }

    public String getYjfl() {
        return yjfl;
    }

    public void setYjfl(String yjfl) {
        this.yjfl = yjfl == null ? null : yjfl.trim();
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh == null ? null : zh.trim();
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
}