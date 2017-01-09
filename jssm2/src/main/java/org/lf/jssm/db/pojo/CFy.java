package org.lf.jssm.db.pojo;

/**
 *	C_FY数据表 
 *
 */
public class CFy {
    private String dm;

    private String fjm;

    private String fymc;

    private String pym;

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getFjm() {
        return fjm;
    }

    public void setFjm(String fjm) {
        this.fjm = fjm == null ? null : fjm.trim();
    }

    public String getFymc() {
        return fymc;
    }

    public void setFymc(String fymc) {
        this.fymc = fymc == null ? null : fymc.trim();
    }

    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        this.pym = pym == null ? null : pym.trim();
    }
}