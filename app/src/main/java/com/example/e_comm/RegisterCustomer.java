package com.example.e_comm;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import static android.app.Activity.RESULT_OK;


public class RegisterCustomer  extends Fragment {

    DatabaseHelper db;
    EditText name;
    EditText mobile_no;
    EditText pass;
    EditText conf_pass;
    Button btn_register;
    TextView go_to_login;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register_customer,container,false);

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //db = new DatabaseHelper(RegisterActivity1.getApplicationContext());
        name = view.findViewById(R.id.txt_name1);
        mobile_no = view.findViewById(R.id.txt_mobile_no1);
        pass = view.findViewById(R.id.pass3);
        conf_pass = view.findViewById(R.id.pass4);
        go_to_login = view.findViewById(R.id.txt_view_login1) ;
        btn_register = view.findViewById(R.id.reg_button2);


        go_to_login.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),StartActivity.class);
                startActivity(intent);

            }
        });



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile_no1 = mobile_no.getText().toString().trim();
                String pass1 = pass.getText().toString().trim();
                String conf_pass1 = conf_pass.getText().toString().trim();
                String name1 = name.getText().toString().trim();

                boolean res = db.checkCustomer(mobile_no1);

                if(mobile_no1.isEmpty() || pass1.isEmpty()|| conf_pass1.isEmpty() || name1.isEmpty())
                {
                    Toast.makeText(getActivity(),"Fields can not remain empty",Toast.LENGTH_LONG).show();
                }
                else if(mobile_no1.length()!=10)
                {
                    Toast.makeText(getActivity(),"Mobile number should be of 10 digits",Toast.LENGTH_LONG).show();
                }
                else{


                    if(res == true)
                    {
                        Toast.makeText(getActivity(),"Account has already been created with this mobile number.User another mobile number", Toast.LENGTH_LONG).show();
                    }
                    else if(pass.getText().toString().equals(conf_pass.getText().toString()))
                    {
                        Toast.makeText(getActivity(),"Successful Registration",Toast.LENGTH_SHORT).show();
                        db.addCustomer(name.getText().toString(),pass.getText().toString(),mobile_no.getText().toString());
                        Intent intent1= new Intent(getActivity(),StartActivity.class);
                        startActivity(intent1);
                    }
                    else{
                        Toast.makeText(getActivity(),"Password does not match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
