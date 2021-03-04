package com.example.e_comm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText txt_mobile_no;
    EditText txt_password;
    Button login;
    TextView register;

    String TAG="MainActivityLog";
    String type;


    public void create_session(String mobile_no,String type1){

        User user = new User(5,mobile_no,type1);
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.saveSession(user);

      /*  if(type.equals("customer")){

            Intent intent = new Intent(getApplicationContext(), CustomHomeActivity.class);
            startActivity(intent);
        }
        else if(type.equals("seller")){
            Intent intent = new Intent(getApplicationContext(), SellerHomeActivity.class);
            startActivity(intent);*/

    }

  /*  protected void onStart() {
// Started by default when an activity comes to foreground so chech if user is logged in or not.
        super.onStart();

        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int loggedInOrNot = sessionManagement.getSession();

        if(loggedInOrNot!=-1){

           // Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent1);

        }

    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        txt_mobile_no = (EditText)findViewById(R.id.text_mobile_num);
        txt_password = (EditText)findViewById(R.id.txt_password);
        login = (Button)findViewById(R.id.btn_login);
        register = (TextView)findViewById(R.id.txtViewReg);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity1.class);
                    startActivity(registerIntent);
                    //registerIntent.putExtra();
                }
                catch(Exception e)
                {
                    System.out.println("Some error has occured"+e);
                }
            }

        });



        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {



                String mobile_no = txt_mobile_no.getText().toString().trim();
                String password = txt_password.getText().toString().trim();



                if(mobile_no.isEmpty() || password.isEmpty())
                {
                    //Toast.makeText(MainActivity.this,"Please enter the credentials",Toast.LENGTH_LONG).show();
                    Snackbar.make(v,"Please enter the credentials", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                }
                else if(mobile_no.length() != 10)
                {
                    //Toast.makeText(MainActivity.this,"Mobile number should be of 10 digits",Toast.LENGTH_LONG).show();
                    Snackbar.make(v,"Mobile number should be of 10 digits", Snackbar.LENGTH_LONG).setAction("Action",null).show();

                }
                else {

                    if (type.equals("Customer")) {
                        boolean res = db.checkCustomer(mobile_no);

                        if (res) {


                            boolean res1 = db.verifyCustomerPassword(mobile_no, password);
                            if (res1) {

                                create_session(mobile_no,type);
                                //Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
                                Snackbar.make(v,"Successfully logged in", Snackbar.LENGTH_LONG).setAction("Action",null).show();

                                Intent intent1 = new Intent(getApplicationContext(), CustomHomeActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent1);
                            } else {
                               // Toast.makeText(getApplicationContext(), "Invalid phone no or password", Toast.LENGTH_SHORT).show();
                                Snackbar.make(v,"Invalid phone no or password", Snackbar.LENGTH_LONG).setAction("Action",null).show();

                            }

                        }
                        else {
                           // Toast.makeText(MainActivity.this, "Not registered please register yourself", Toast.LENGTH_SHORT).show();
                            Snackbar.make(v,"Not registered please register yourself", Snackbar.LENGTH_LONG).setAction("Action",null).show();

                        }


                    }
                    else if (type.equals("seller")) {
                        boolean res = db.checkSeller(mobile_no);

                        if (res) {
                            boolean res1 = db.verifySellerPassword(mobile_no, password);
                            if (res1) {

                                create_session(mobile_no,type);
                                //Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
                                Snackbar.make(v,"Successfully logged in", Snackbar.LENGTH_LONG).setAction("Action",null).show();

                                Intent intent1 = new Intent(getApplicationContext(), SellerHomeActivity.class);
                                startActivity(intent1);
                            } else {
                                //Toast.makeText(getApplicationContext(), "Invalid phone no or password", Toast.LENGTH_SHORT).show();
                                Snackbar.make(v,"Invalid phone no or password", Snackbar.LENGTH_LONG).setAction("Action",null).show();

                            }

                        } else {
                            //Toast.makeText(MainActivity.this, "Not registered please register yourself", Toast.LENGTH_SHORT).show();
                            Snackbar.make(v,"Not registered please register yourself", Snackbar.LENGTH_LONG).setAction("Action",null).show();

                        }

                    }
                }
            }
        });
    }
}