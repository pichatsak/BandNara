package com.app.bandnara.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class NotiUserModel {
    private String userId;
    private String statusRead;
    private String txtNoti;
    @ServerTimestamp
    private Date date;

    public NotiUserModel() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatusRead() {
        return statusRead;
    }

    public void setStatusRead(String statusRead) {
        this.statusRead = statusRead;
    }

    public String getTxtNoti() {
        return txtNoti;
    }

    public void setTxtNoti(String txtNoti) {
        this.txtNoti = txtNoti;
    }
}
