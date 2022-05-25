package com.app.bandnara.models;

public class ItemRegis {
    private String keyId;
    private String nameRegis;
    private String ageRegis;
    private int typeRegis;
    private String dateRegis;
    private String statusRegis;
    private int typeData;

    public ItemRegis() {

    }

    public int getTypeData() {
        return typeData;
    }

    public void setTypeData(int typeData) {
        this.typeData = typeData;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getNameRegis() {
        return nameRegis;
    }

    public void setNameRegis(String nameRegis) {
        this.nameRegis = nameRegis;
    }

    public String getAgeRegis() {
        return ageRegis;
    }

    public void setAgeRegis(String ageRegis) {
        this.ageRegis = ageRegis;
    }

    public int getTypeRegis() {
        return typeRegis;
    }

    public void setTypeRegis(int typeRegis) {
        this.typeRegis = typeRegis;
    }

    public String getDateRegis() {
        return dateRegis;
    }

    public void setDateRegis(String dateRegis) {
        this.dateRegis = dateRegis;
    }

    public String getStatusRegis() {
        return statusRegis;
    }

    public void setStatusRegis(String statusRegis) {
        this.statusRegis = statusRegis;
    }
}
