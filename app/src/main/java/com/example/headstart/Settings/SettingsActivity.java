package com.example.headstart.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.headstart.AuthenticateActivities.TruckOwnerLoginForm;
import com.example.headstart.Home.HomeActivity;
import com.example.headstart.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    //private BottomNavigationView bottomNavigationView;
    SwitchCompat modeSwitch, notificationSwitch;
    SharedPreferences sharedPreferences = null;
    ImageView moreImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button logOut = findViewById(R.id.logoutBtn);
        logOut.setOnClickListener(this);

        moreImage = findViewById(R.id.more_arrow_image);
        modeSwitch = findViewById(R.id.darkModeSwitch);
        notificationSwitch = findViewById(R.id.notifySwitch);


        sharedPreferences = getSharedPreferences("night", 0);
        Boolean boolValue = sharedPreferences.getBoolean("night_mode", true);
        if (boolValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            modeSwitch.setChecked(true);
            moreImage.setImageResource(R.drawable.ic_more);
        }

        modeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    modeSwitch.setChecked(true);
                    moreImage.setImageResource(R.drawable.ic_more);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", true);
                    editor.commit();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    modeSwitch.setChecked(false);
                    moreImage.setImageResource(R.drawable.ic_more);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode", false);
                    editor.commit();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
     */
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


    /**
     * Onclick listener
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.logoutBtn) {
            logoutUser();
        }
    }


    /**
     * Logging out User.
     */
    public void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(SettingsActivity.this, TruckOwnerLoginForm.class));
        Toast.makeText(SettingsActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
        Log.i("Logging User Out", "Successfully Signed out");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
        finish();
    }
}