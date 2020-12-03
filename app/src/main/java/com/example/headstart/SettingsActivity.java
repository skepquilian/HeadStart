package com.example.headstart;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends BaseActivity {

    Button btnLogout;

    @Override
    int getContentViewId() {
        return R.layout.activity_settings;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_settings;
    }

    /**
     * Logging out User.
     */
    public void logoutUser(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(SettingsActivity.this, TruckOwnerLoginForm.class));
        Toast.makeText(SettingsActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
    }
}