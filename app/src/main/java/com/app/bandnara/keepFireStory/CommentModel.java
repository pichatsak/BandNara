package com.app.bandnara.keepFireStory;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class CommentModel {
    private String commentId;
    private String txtComment;
    private String userId;
    private String landMarkKey;
    private String nameUser;
    private long dateCreate;

    public CommentModel() {

    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getTxtComment() {
        return txtComment;
    }

    public void setTxtComment(String txtComment) {
        this.txtComment = txtComment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLandMarkKey() {
        return landMarkKey;
    }

    public void setLandMarkKey(String landMarkKey) {
        this.landMarkKey = landMarkKey;
    }
}
