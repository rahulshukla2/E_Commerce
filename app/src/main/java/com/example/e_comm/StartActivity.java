package com.example.e_comm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    DatabaseHelper db;
    RadioGroup radioGroup;
    Button next;
    RadioButton type;
    public static final String TAG = "StartActivity";

    protected void onStart() {
// Started by default when an activity comes to foreground so chech if user is logged in or not.
        super.onStart();

        Log.d(TAG,"came here outside of if else");
        SessionManagement sessionManagement = new SessionManagement(StartActivity.this);
        int loggedInOrNot = sessionManagement.getSession();
        String type1 = sessionManagement.getType();
        Log.d(TAG,"came here type1 is "+type1);
        if(type1.equals(""))
        {
            Log.d(TAG,"it is empty I guess");
        }
        if(loggedInOrNot!=-1 && type1.equals("Customer")){

            Log.d(TAG,"came here inside of if else customer");
            // Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(getApplicationContext(), CustomHomeActivity.class);
            startActivity(intent1);

        }
        else if(loggedInOrNot!=-1 && type1.equals("seller")){

            Log.d(TAG,"came here inside of if else seller");
            // Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(getApplicationContext(), SellerHomeActivity.class);
            startActivity(intent1);

        }
        else if(loggedInOrNot!=-1 && type1.equals("admin")){

            Log.d(TAG,"came here inside of if else seller");
            // Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(getApplicationContext(), AdminHomeActivity.class);
            startActivity(intent1);

        }



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        db = new DatabaseHelper(this);
        radioGroup = findViewById(R.id.radioGroup2);
        next = findViewById(R.id.button);
       // type = findViewById(R.id.radioGroup2);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                int selectedId = radioGroup.getCheckedRadioButtonId();

                type = findViewById(selectedId);

                if(selectedId==-1){
                    Toast.makeText(getApplicationContext(),"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else {

                    String name = type.getText().toString();
                    if (name.equals("Customer")) {

                        intent.putExtra("type", "Customer");
                        startActivity(intent);
                    } else {

                        intent.putExtra("type", "seller");
                        startActivity(intent);
                    }

                    // Toast.makeText(MainActivity.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}