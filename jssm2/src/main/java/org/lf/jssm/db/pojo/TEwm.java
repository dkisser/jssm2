package org.lf.jssm.db.pojo;

import java.util.Date;
/**
 * 二维码生成记录实体类
 * @author sunwill
 *
 */
public class TEwm {
    private String id;

    private Date createDate;

    private Integer lastId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getLastId() {
        return lastId;
    }

    public void setLastId(Integer lastId) {
        this.lastId = lastId;
    }
}