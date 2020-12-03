package com.example.headstart;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    /**
     * Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
     */
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId== R.id.nav_home){
            //home activity
            startActivity(new Intent(this, HomeActivity.class));
            return true;
        }
        else if (itemId == R.id.nav_trucks){
            //truck activity
            startActivity(new Intent(this, TrucksActivity.class));
            return true;
        }
        else if (itemId == R.id.nav_map){
            //map activity
            startActivity(new Intent(this, MapActivity.class));
            return true;
        }
        else if (itemId == R.id.nav_drivers){
            //drivers activity
            startActivity(new Intent(this, DriversActivity.class));
            return true;
        }
        else if (itemId == R.id.nav_settings){
            //settings activity
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return false;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        //Menu menu = bottomNavigationView.getMenu();
        //MenuItem menuItem = menu.getItem(0);
        //menuItem.setChecked(true);
        MenuItem item = bottomNavigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

}

