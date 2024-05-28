package com.example.chatify.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class chat_room_model {
    String chatRoomId;
    List<String> userids;
    com.google.firebase.Timestamp lastMessageTimestamp;
    String lastMessageSenderId;

    public chat_room_model() {
    }

    public chat_room_model(String chatRoomId, List<String> userids, com.google.firebase.Timestamp lastMessageTimestamp, String lastMessageSenderId) {
        this.chatRoomId = chatRoomId;
        this.userids = userids;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessageSenderId = lastMessageSenderId;
    }



    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public List<String> getUserids() {
        return userids;
    }

    public void setUserids(List<String> userids) {
        this.userids = userids;
    }

    public com.google.firebase.Timestamp getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(Timestamp lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }
}
