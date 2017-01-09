package org.lf.jssm.db.pojo;

public class JJzjy {
    private String id;

    private String wlryId;

    private String jzewm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWlryId() {
        return wlryId;
    }

    public void setWlryId(String wlryId) {
        this.wlryId = wlryId == null ? null : wlryId.trim();
    }

    public String getJzewm() {
        return jzewm;
    }

    public void setJzewm(String jzewm) {
        this.jzewm = jzewm == null ? null : jzewm.trim();
    }
}