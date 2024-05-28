package com.example.chatify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.chatify.model.userModel;
import com.example.chatify.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class loign_user_name extends AppCompatActivity {
    EditText userName;
    Button userName_button;
    ProgressBar p_bar;
    String phoneNumber;
    userModel user_model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loign_user_name);
        userName=findViewById(R.id.username);
        userName_button=findViewById(R.id.username_button);
        p_bar=findViewById(R.id.p_bar);
        phoneNumber=getIntent().getExtras().getString("phone");
        getuserName();
        userName_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setuserName();
            }
        });

    }
    void setInProgress(boolean inProgress){
        if(inProgress){
            p_bar.setVisibility(View.VISIBLE);
            userName_button.setVisibility(View.GONE);
        }
        else{
            p_bar.setVisibility(View.GONE);
            userName_button.setVisibility(View.VISIBLE);
        }
    }
    void setuserName(){

           String  username=userName.getText().toString();

           if(username.isEmpty()|| username.length()<3){
               userName.setError("userName length should be at least 3 chars");
               return;
           }
        setInProgress(true);
           if(user_model!=null){
               user_model.setUsername(username);
           }else{
               user_model= new userModel(phoneNumber,username, Timestamp.now(), FirebaseUtil.currentUserId());
           }
           FirebaseUtil.currentUserData().set(user_model).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   setInProgress(false);
                   if(task.isSuccessful()){
                       Intent intent=new Intent(loign_user_name.this, MainActivity.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);
                   }
               }
           });
          
    }
   void getuserName(){
          setInProgress(true);
          FirebaseUtil.currentUserData().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  setInProgress(false);
                  if(task.isSuccessful()){
                   user_model =task.getResult().toObject(userModel.class);
                     if(user_model!=null){
                         userName.setText(user_model.getUsername());
                         Log.d("uid",FirebaseUtil.currentUserId().toString());

                     }
                  }
              }
          });
    }
}