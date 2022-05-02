package com.app.bandnara.models;

public class ProvincesModel {
    private String id;
    private String provName;

    public ProvincesModel(String id, String provName) {
        this.id = id;
        this.provName = provName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }
}
