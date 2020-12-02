package com.example.headstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_home){
                    //home activity
                    startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
                    return true;
                }
                else if (item.getItemId() == R.id.nav_trucks){
                    //truck activity
                    startActivity(new Intent(SettingsActivity.this, TrucksActivity.class));
                    return true;
                }
                else if (item.getItemId() == R.id.nav_map){
                    //map activity
                    startActivity(new Intent(SettingsActivity.this, MapActivity.class));
                    return true;
                }
                else if (item.getItemId() == R.id.nav_drivers){
                    //drivers activity
                    startActivity(new Intent(SettingsActivity.this, DriversActivity.class));
                    return true;
                }
                else if (item.getItemId() == R.id.nav_settings){
                    //settings activity
                    return true;
                }
                return false;
            }
        });


        btnLogout = findViewById(R.id.logoutBtn);
        btnLogout.setOnClickListener(this);
    }

    //onclick listener
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logoutBtn) {
            logoutUser();
        }
    }


    //Logging out User.
    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(SettingsActivity.this, TruckOwnerLoginForm.class));
        Toast.makeText(SettingsActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
    }
}