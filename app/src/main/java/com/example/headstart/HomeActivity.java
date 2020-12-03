package com.example.headstart;


import android.view.View;

public class HomeActivity extends BaseActivity implements View.OnClickListener{


    @Override
    int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }


    @Override
    public void onClick(View v) {

    }
}