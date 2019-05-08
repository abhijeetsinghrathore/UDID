package com.example.abhijeetsingh.udid;

import android.content.Context;
import android.content.SharedPreferences;

//to save the token
public class SharedPrefManager {

    private static final String SHARED_PREF_NAME="sharedPrefForUDID"; //any name can be used for the file
    private static final String KEY_ACCESS_TOKEN="token";  // key name to store data in editor object

    private static Context mCtx;
    private static SharedPrefManager mInstance;

    private SharedPrefManager(Context context){
        mCtx=context;
    }

    public  static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance==null)
            mInstance=new SharedPrefManager(context);
        return mInstance;
    }

    public boolean storeToken(String token){
        //It creates an XML file in data/data/application-package-name with the passed name and
        //MODE_PRIVATE - (default mode) so that the file can be accessed by the calling application only.
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //To save data in SharedPreferences we call edit() function of SharedPreferences class which returns Editor class object.
        SharedPreferences.Editor editor=sharedPreferences.edit();
        //Editor class provides putString function to save the data.
        //It stores data as a key value pair : Keyname - KEY_ACCESS_TOKEN and key value - token
        editor.putString(KEY_ACCESS_TOKEN,token);
        //to apply the changes
        editor.apply();

        return true;
    }

    public String getToken(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //Keyname i.e. KEY_ACCESS_TOKEN  is passed to get value of token and
        // null is passed as a default value in case no value was saved for KEY_ACCESS_TOKEN
        return sharedPreferences.getString(KEY_ACCESS_TOKEN,null);

    }

}