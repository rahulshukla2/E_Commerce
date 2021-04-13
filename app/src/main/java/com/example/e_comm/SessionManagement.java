package com.example.e_comm;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public final String SHARED_PRE_NAME = "shared_pref";
    public final String SESSION_KEY = "session_key";
    public final String NAME = "name";
    public final String TYPE = "user_type";
    public final String MOBILENO = "mobile_no";
    public final String ID = "user_id";



    public SessionManagement(Context context)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void saveSession(User user){
            int id = user.getId();
            String  mobile_no = user.getMobile_no();
            String type = user.getType();
            String name= user.getName();
        //editor = sharedPreferences.edit();
            editor.putInt(SESSION_KEY,id).commit();
            editor.putString(MOBILENO,mobile_no).commit();
            editor.putString(TYPE,type).commit();
            editor.putString(NAME,name).commit();



    }

    public String getName(){

        return sharedPreferences.getString(NAME,"");
    }


    public String getMOBILENO(){

        return sharedPreferences.getString(MOBILENO,"");
    }

    public String getType(){

        return sharedPreferences.getString(TYPE,"");
    }
    public int getSession(){
//return user id whose session is saved.
       return sharedPreferences.getInt(SESSION_KEY,-1);

    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
