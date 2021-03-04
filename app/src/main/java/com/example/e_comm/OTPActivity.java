package com.example.e_comm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OTPActivity extends AppCompatActivity {

    EditText txt_otp;
    Button btn_login;
    String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        txt_otp = (EditText)findViewById(R.id.otpnum);
        btn_login = (Button)findViewById(R.id.btn_login) ;
        Intent intent = getIntent();
         otp = intent.getStringExtra("otpforlogin");


         btn_login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(txt_otp.getText().toString().equals(otp)){
                     Toast.makeText(getApplicationContext(),"Successfully logged in",Toast.LENGTH_SHORT).show();
                     Intent intent1= new Intent(getApplicationContext(),CustomHomeActivity.class);
                     startActivity(intent1);
                 }
                 else
                 {
                     Toast.makeText(getApplicationContext(),"Invalid otp",Toast.LENGTH_SHORT).show();
                     Intent intent1= new Intent(getApplicationContext(),MainActivity.class);
                     startActivity(intent1);
                 }

             }
         });

    }
}