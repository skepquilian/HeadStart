package com.example.headstart.Settings;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences sharedPref;
    public SharedPref(Context context) {
        sharedPref = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }
    /**
     * Toggle Dark mode switch to Light mode function
     */
    public void darkModeToggle(Boolean state) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("night_mode", state);
        editor.apply();
    }
    // this method will load the Night Mode State
    public Boolean loadNightModeState (){
        Boolean state = sharedPref.getBoolean("night_mode",false);
        return  state;
    }

}
