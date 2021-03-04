package com.example.e_comm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText name;
    EditText mobile_no;
    EditText pass;
    EditText conf_pass;
    Button btn_register;
    Button btn_verify_mo;
    TextView go_to_login;
    EditText otp_for_reg;
    int random_num;
    String otp_random;
    String TAG="RegisterActivityLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        name = (EditText)findViewById(R.id.txt_name);
        mobile_no = (EditText)findViewById(R.id.txt_mobile_no);
        pass = (EditText)findViewById(R.id.pwd1);
        conf_pass = (EditText)findViewById(R.id.pwd2);
        btn_verify_mo = (Button)findViewById(R.id.btn_verify);
        go_to_login = (TextView)findViewById(R.id.txt_view_login) ;
        btn_register = (Button)findViewById(R.id.btn_reg);




        go_to_login.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });


        btn_verify_mo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile_no1 = mobile_no.getText().toString().trim();

                Boolean res = db.checkCustomer(mobile_no1);
                if(res == true)
                {
                    Toast.makeText(getApplicationContext(),"Account has already been created with this mobile number.User another mobile number", Toast.LENGTH_LONG).show();
                }
                else if(pass.getText().toString().equals(conf_pass.getText().toString()))
                {
                    Random rand = new Random();
                    random_num = rand.nextInt(999999);
                    otp_random = Integer.toString(random_num);
                    //System.out.println("The otp is "+random_num);
                   // Log.d(TAG,otp_random);
                    String msg = "The otp is "+otp_random;
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(mobile_no1,null,msg,pi,null);
                    Toast.makeText(getApplicationContext(),"OTP sent successfully",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp_for_reg = findViewById(R.id.txt_otp);

                if(otp_for_reg.getText().toString().equals(otp_random)){
                    Toast.makeText(getApplicationContext(),"Successful Registration",Toast.LENGTH_SHORT).show();
                    db.addCustomer(name.getText().toString(),pass.getText().toString(),mobile_no.getText().toString());
                    Intent intent1= new Intent(getApplicationContext(),CustomHomeActivity.class);
                    startActivity(intent1);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid otp",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}