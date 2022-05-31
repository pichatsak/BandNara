package com.app.bandnara.models;

import com.google.firebase.firestore.FieldValue;

public class NotiWebModel {
    private String txtNoti;
    private String statusRead;
    private String userId;
    private FieldValue dateCreate;

    public NotiWebModel() {

    }

    public String getTxtNoti() {
        return txtNoti;
    }

    public void setTxtNoti(String txtNoti) {
        this.txtNoti = txtNoti;
    }

    public String getStatusRead() {
        return statusRead;
    }

    public void setStatusRead(String statusRead) {
        this.statusRead = statusRead;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FieldValue getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(FieldValue dateCreate) {
        this.dateCreate = dateCreate;
    }
}
