package org.lf.jssm.db.pojo;

public class JScanEnv {
    private String id;

    private String envKey;

    private String envValue;

    private Integer scanerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEnvKey() {
        return envKey;
    }

    public void setEnvKey(String envKey) {
        this.envKey = envKey == null ? null : envKey.trim();
    }

    public String getEnvValue() {
        return envValue;
    }

    public void setEnvValue(String envValue) {
        this.envValue = envValue == null ? null : envValue.trim();
    }

    public Integer getScanerId() {
        return scanerId;
    }

    public void setScanerId(Integer scanerId) {
        this.scanerId = scanerId;
    }
}