package org.lf.jssm.db.pojo;

public class JEnv {
    private Short id;

    private String envKey;

    private String envValue;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
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
}