package com.app.bandnara.keepFireStory;

import com.google.firebase.firestore.FieldValue;

public class ReportsData {
    private String userId;
    private FieldValue dateTime;
    private String reasons;
    private Double locateLat;
    private Double locateLong;
    private String picData;
    private int typeReport;
    private String typeReportName;

    public ReportsData() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FieldValue getDateTime() {
        return dateTime;
    }

    public void setDateTime(FieldValue dateTime) {
        this.dateTime = dateTime;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public Double getLocateLat() {
        return locateLat;
    }

    public void setLocateLat(Double locateLat) {
        this.locateLat = locateLat;
    }

    public Double getLocateLong() {
        return locateLong;
    }

    public void setLocateLong(Double locateLong) {
        this.locateLong = locateLong;
    }

    public String getPicData() {
        return picData;
    }

    public void setPicData(String picData) {
        this.picData = picData;
    }

    public int getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(int typeReport) {
        this.typeReport = typeReport;
    }

    public String getTypeReportName() {
        return typeReportName;
    }

    public void setTypeReportName(String typeReportName) {
        this.typeReportName = typeReportName;
    }
}
