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

    public SessionManagement(Context context)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PRE_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void saveSession(User user){
            int id = user.getId();
            String  name = user.mobile_no;
            String type = user.type;
        //editor = sharedPreferences.edit();
            editor.putInt(SESSION_KEY,id).commit();
            editor.putString(NAME,name).commit();
            editor.putString(TYPE,type).commit();



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
