package com.example.headstart.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.headstart.AuthenticateActivities.TruckOwnerLoginForm;
import com.example.headstart.Home.HomeActivity;
import com.example.headstart.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

//    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button logOut = findViewById(R.id.logoutBtn);
        logOut.setOnClickListener(this);
//
//        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //updateNavigationBarState();
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

//    /**
//     * Bottom Navigation bar
//     */
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        int itemId = item.getItemId();
//
//        if (itemId == R.id.nav_home) {
//            //home activity
//            startActivity(new Intent(this, HomeActivity.class));
//            return true;
//        } else if (itemId == R.id.nav_trucks) {
//            //truck activity
//            startActivity(new Intent(this, TrucksActivity.class));
//            return true;
//        } else if (itemId == R.id.nav_map) {
//            //truck activity
//            return true;
//        } else if (itemId == R.id.nav_drivers) {
//            //drivers activity
//            startActivity(new Intent(this, DriversActivity.class));
//            return true;
//        } else if (itemId == R.id.nav_settings) {
//            //settings activity
//            startActivity(new Intent(this, SettingsActivity.class));
//            return true;
//        }
//        return false;
//    }
//
//
//    /**
//     * menu Item select focus function
//     */
//    private void updateNavigationBarState() {
//        Menu menu = bottomNavigationView.getMenu();
//        MenuItem menuItem = menu.getItem(4);
//        menuItem.setChecked(true);
//
//        bottomNavigationView.setBackground(null);
//        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
//    }

    /**
     * Logging out User.
     */
    public void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(SettingsActivity.this, TruckOwnerLoginForm.class));
        Toast.makeText(SettingsActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
        Log.i("Logging User Out","Successfully Signed out");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
        finish();
    }
}