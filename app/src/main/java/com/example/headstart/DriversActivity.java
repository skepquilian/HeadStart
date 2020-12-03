package com.example.headstart;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DriversActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }


    @Override
    int getContentViewId() {
        return R.layout.activity_drivers;
    }

    /**
     * menu Item select focus function
     */
    private void updateNavigationBarState(){
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
    }


}