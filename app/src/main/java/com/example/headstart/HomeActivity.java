package com.example.headstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId== R.id.nav_home){
                    //home activity
                    return true;
                }
                else if (itemId == R.id.nav_trucks){
                    //truck activity
                    startActivity(new Intent(HomeActivity.this, TrucksActivity.class));
                    return true;
                }
                else if (itemId == R.id.nav_map){
                    //map activity
                    startActivity(new Intent(HomeActivity.this, MapActivity.class));
                    return true;
                }
                else if (itemId == R.id.nav_drivers){
                    //drivers activity
                    startActivity(new Intent(HomeActivity.this, DriversActivity.class));
                    return true;
                }
                else if (itemId == R.id.nav_settings){
                    //settings activity
                    startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    //onclick listener
    @Override
    public void onClick(View v) {

    }

}