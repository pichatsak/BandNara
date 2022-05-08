package com.app.bandnara.keepFireStory;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class LandMarkModel {
    private String keyId;
    private String landName;
    private String landDetail;
    private String landCate;
    @ServerTimestamp
    private Date dateUpdate;

    public LandMarkModel() {

    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }

    public String getLandDetail() {
        return landDetail;
    }

    public void setLandDetail(String landDetail) {
        this.landDetail = landDetail;
    }

    public String getLandCate() {
        return landCate;
    }

    public void setLandCate(String landCate) {
        this.landCate = landCate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
