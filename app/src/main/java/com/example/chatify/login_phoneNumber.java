package com.example.chatify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hbb20.CountryCodePicker;

public class login_phoneNumber extends AppCompatActivity {
CountryCodePicker countryCode;
EditText phone;
Button send_otp;
ProgressBar progress1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_number);
        countryCode=findViewById(R.id.country_code);
        phone=findViewById(R.id.otp_textbar);
        send_otp=findViewById(R.id.send_otp);
        progress1=findViewById(R.id.progrss);

        progress1.setVisibility(View.GONE);
        countryCode.registerCarrierNumberEditText(phone);
        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!countryCode.isValidFullNumber()){
                    phone.setError(("phone number is not valid"));
                    return;
                }
                Intent intent=new Intent(login_phoneNumber.this,logon_otp.class);
                intent.putExtra("phone",countryCode.getFullNumberWithPlus());
                startActivity(intent);
            }
        });
    }
}