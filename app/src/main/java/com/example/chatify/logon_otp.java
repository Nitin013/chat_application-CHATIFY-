package com.example.chatify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class logon_otp extends AppCompatActivity {
    EditText editext;
    String verfication_code;
    PhoneAuthProvider.ForceResendingToken ResendingToken;
    Button otp_button;
    TextView resend_otp;
    Long timeOutSec=60L;
    String phoneNumber;
    ProgressBar p_bar;
    FirebaseAuth mAuth=  FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon_otp);
        editext =findViewById(R.id.edit_text);
        otp_button=findViewById(R.id.otp_button);
        resend_otp=findViewById(R.id.resend_otp);
        p_bar=findViewById(R.id.p_bar);
        phoneNumber=getIntent().getExtras().getString("phone");
        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
        sendOtp(phoneNumber,false);
        otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp=editext.getText().toString();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verfication_code,enteredOtp);
                signIn(credential);
                setInProgress(true);
            }
        });
        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp(phoneNumber,true);
            }
        });

    }
    void sendOtp(String phoneNumber, boolean isResend){
        seartResendTimer();
        setInProgress(true);
        PhoneAuthOptions.Builder builder= PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(phoneNumber).setTimeout(timeOutSec, TimeUnit.SECONDS)
                .setActivity(this).setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signIn(phoneAuthCredential);
                        setInProgress(false);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(logon_otp.this, "otp varification failed", Toast.LENGTH_SHORT).show();
                        setInProgress(false);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verfication_code=s;
                        ResendingToken=forceResendingToken;
                        Toast.makeText(logon_otp.this, "otp sent successfully", Toast.LENGTH_SHORT).show();
                        setInProgress(false);
                    }
                });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(ResendingToken).build());
        }else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }
    void seartResendTimer(){
        resend_otp.setEnabled(false);
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeOutSec--;
                resend_otp.setText("resend otp in "+timeOutSec+"seconds");
                if(timeOutSec<=0){
                    timeOutSec=60l;
                    timer.cancel();
                    runOnUiThread(()->{
                        resend_otp.setEnabled(true);
                    });
                }
            }
        },0,1000);
    }
    void setInProgress(boolean inProgress){
        if(inProgress){
            p_bar.setVisibility(View.VISIBLE);
            otp_button.setVisibility(View.GONE);
        }
        else{
            p_bar.setVisibility(View.GONE);
            otp_button.setVisibility(View.VISIBLE);
        }
    }
    void signIn(PhoneAuthCredential phoneAuthCredential){
             setInProgress(true);
             mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     setInProgress(false);
                     if(task.isSuccessful()){
                         Intent intent= new Intent(logon_otp.this,loign_user_name.class);
                         intent.putExtra("phone",phoneNumber);
                         startActivity(intent);
                     }
                     else{
                         Toast.makeText(logon_otp.this, "otp verification failed", Toast.LENGTH_SHORT).show();
                     }
                 }
             });
    }
}