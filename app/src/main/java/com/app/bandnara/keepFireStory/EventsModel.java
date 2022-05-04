package com.app.bandnara.keepFireStory;

import com.google.firebase.firestore.FieldValue;

public class EventsModel {
    private String keyId;
    private String eventTitle;
    private String eventDetail;
    private FieldValue dateCreate;
    private FieldValue dateUpdate;

    public EventsModel() {

    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }

    public FieldValue getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(FieldValue dateCreate) {
        this.dateCreate = dateCreate;
    }

    public FieldValue getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(FieldValue dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
