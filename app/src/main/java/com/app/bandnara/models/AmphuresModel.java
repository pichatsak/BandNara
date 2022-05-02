package com.app.bandnara.models;

public class AmphuresModel {
    private String ampId;
    private String ampName;
    private String provId;

    public AmphuresModel(String ampId, String ampName, String provId) {
        this.ampId = ampId;
        this.ampName = ampName;
        this.provId = provId;
    }

    public String getAmpId() {
        return ampId;
    }

    public void setAmpId(String ampId) {
        this.ampId = ampId;
    }

    public String getAmpName() {
        return ampName;
    }

    public void setAmpName(String ampName) {
        this.ampName = ampName;
    }

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }
}
