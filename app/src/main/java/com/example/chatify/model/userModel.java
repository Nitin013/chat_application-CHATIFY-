package com.example.chatify.model;

import com.google.firebase.Timestamp;

public class userModel {
    private String phone;
    private String username;
    private Timestamp createdTimeStamp;
    private  String userId;
    public userModel(){

    }

    public userModel(String phone, String username, Timestamp createdTimeStamp,String userid) {
        this.phone = phone;
        this.username = username;
        this.createdTimeStamp = createdTimeStamp;
        this.userId=userid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public Timestamp getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }
}
