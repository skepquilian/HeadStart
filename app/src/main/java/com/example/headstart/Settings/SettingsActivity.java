package com.example.headstart.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.headstart.AuthenticateActivities.LoginActivity;
import com.example.headstart.AuthenticateActivities.User;
import com.example.headstart.Home.HomeActivity;
import com.example.headstart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    //private BottomNavigationView bottomNavigationView;
    SwitchCompat modeSwitch, notificationSwitch;
    SharedPreferences sharedPreferences = null;
    ImageView moreImage;
    TextView userName;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button logOut = findViewById(R.id.logoutBtn);
        logOut.setOnClickListener(this);

        userName = findViewById(R.id.usernameTextView);
        moreImage = findViewById(R.id.more_arrow_image);
        modeSwitch = findViewById(R.id.darkModeSwitch);
        notificationSwitch = findViewById(R.id.notifySwitch);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String name = firebaseUser.getDisplayName();
            if (name != null) {
                userName.setText("".concat(name));
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        darkModeToggle();
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
     * Toggle Dark mode switch to Light mode function
     */
    public void darkModeToggle() {
        sharedPreferences = getSharedPreferences("night", 0);
        Boolean boolValue = sharedPreferences.getBoolean("night_mode", true);
        if (boolValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            modeSwitch.setChecked(true);
        }

        modeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                modeSwitch.setChecked(true);
                moreImage.setImageResource(R.drawable.ic_more);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("night_mode", true);
                editor.commit();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                modeSwitch.setChecked(false);
                moreImage.setImageResource(R.drawable.ic_more);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("night_mode", false);
                editor.commit();
            }
        });
    }


    /**
     * Logging out User.
     */
    public void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
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