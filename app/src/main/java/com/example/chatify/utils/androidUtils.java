package com.example.chatify.utils;

import android.content.Intent;

import com.example.chatify.model.userModel;

public class androidUtils {
    public static void passUserModelAsIntent(Intent intent, userModel model){
        intent.putExtra("username",model.getUsername());
        intent.putExtra("phone",model.getPhone());
        intent.putExtra("userId",model.getUserId());
    }
    public static userModel getUserModelFromIntent(Intent intent){
        userModel usermodel=new userModel();
        usermodel.setUsername((intent.getStringExtra("username")));
        usermodel.setPhone(intent.getStringExtra("phone"));
        usermodel.setUserId(intent.getStringExtra("userId"));
        return usermodel;
    }
}
