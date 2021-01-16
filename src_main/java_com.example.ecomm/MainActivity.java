package com.example.e_comm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.util.Random;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText txt_mobile_no;
    Button btn_send_otp;
    TextView register;
    int random_num;
    String TAG="MainActivityLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        txt_mobile_no = (EditText)findViewById(R.id.text_mobile_num);
        btn_send_otp = (Button)findViewById(R.id.btn_send_otp);
        register = (TextView)findViewById(R.id.txtViewReg);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                    //registerIntent.putExtra();
                }
                catch(Exception e)
                {
                    System.out.println("Some error has occred"+e);
                }
            }

        });

        btn_send_otp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String mobile_no = txt_mobile_no.getText().toString().trim();

                Boolean res = db.checkUser(mobile_no);

                if(res == true)
                {
                    //Toast.makeText(MainActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();

                    Random rand = new Random();
                    random_num = rand.nextInt(999999);

                    String otp1 = Integer.toString(random_num);
                    Log.d(TAG,otp1);
                    Intent registerIntent = new Intent( getApplicationContext(), OTPActivity.class);
                    registerIntent.putExtra("otpforlogin",otp1);
                    startActivity(registerIntent);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Not registered please register yourself",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}