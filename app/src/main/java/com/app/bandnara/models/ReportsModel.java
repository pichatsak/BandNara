package com.app.bandnara.models;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class ReportsModel {
    private String userId;
    private String dateTime;
    private String reasons;
    private String status;
    private int typeReport;
    private String typeReportName;

    public ReportsModel() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
