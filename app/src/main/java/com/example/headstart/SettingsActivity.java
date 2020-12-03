package com.example.headstart;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button btnLogout = findViewById(R.id.logoutBtn);
        btnLogout.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_settings;
    }

    //onclick listener
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logoutBtn){
            logoutUser();
        }
    }

    /**
     * menu Item select focus function
     */
    private void updateNavigationBarState(){
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
    }

    /**
     * Logging out User.
     */
    public void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(SettingsActivity.this, TruckOwnerLoginForm.class));
        Toast.makeText(SettingsActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
    }
}