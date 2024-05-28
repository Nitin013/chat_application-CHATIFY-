
package com.example.chatify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.chatify.utils.FirebaseUtil;

public class splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            // Start the main activity
            if(FirebaseUtil.isLoggedIn()){
                startActivity(new Intent(splash_activity.this,MainActivity.class));
            }
            else{
                startActivity(new Intent(splash_activity.this, login_phoneNumber.class));
            }



            // Close this activity
            finish();
        }, 1000);
    }
}