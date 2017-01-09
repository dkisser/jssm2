package org.lf.jssm.db.pojo;

public class CZjlx {
    private String dm;

    private String mc;

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
}