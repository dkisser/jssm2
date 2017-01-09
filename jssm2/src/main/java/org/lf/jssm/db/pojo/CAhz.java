package org.lf.jssm.db.pojo;

public class CAhz {
    private String dm;		//代码

    private String ahz;		//案号字编号

    private String yjfl;	//一级分类编号

    private String cbts;	//承办庭审编号

    private String pym;		//拼音码

    private String tybz;	//停用标志

    private String remark;	//备注
    
    private String yjflms;	//一级分类描述
    
    private String cbtsms;	//承办庭审描述
    
    

    public String getYjflms() {
		return yjflms;
	}

	public void setYjflms(String yjflms) {
		this.yjflms = yjflms;
	}

	public String getCbtsms() {
		return cbtsms;
	}

	public void setCbtsms(String cbtsms) {
		this.cbtsms = cbtsms;
	}

	public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm == null ? null : dm.trim();
    }

    public String getAhz() {
        return ahz;
    }

    public void setAhz(String ahz) {
        this.ahz = ahz == null ? null : ahz.trim();
    }

    public String getYjfl() {
        return yjfl;
    }

    public void setYjfl(String yjfl) {
        this.yjfl = yjfl == null ? null : yjfl.trim();
    }

    public String getCbts() {
        return cbts;
    }

    public void setCbts(String cbts) {
        this.cbts = cbts == null ? null : cbts.trim();
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