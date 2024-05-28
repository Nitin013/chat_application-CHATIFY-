package com.example.chatify.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil {
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }
    public static DocumentReference currentUserData(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }
    public static boolean isLoggedIn(){
        if(currentUserId()!=null){
            return true;
        }
        return false;
    }
    public static CollectionReference alluserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static DocumentReference getChatRoomReference(String chatRoomId){
        return FirebaseFirestore.getInstance().collection("chatRooms").document(chatRoomId);

    }
    public static String getChatRoomId(String userId1,String userId2){
        if(userId1.hashCode()<userId2.hashCode()){
            return userId1+" "+userId2;

        }
        else{
            return userId2+" "+userId1;
        }
    }
    public static CollectionReference getChatroomMessageReference(String chatroomId){
        return getChatRoomReference(chatroomId).collection("chats");
    }
}
