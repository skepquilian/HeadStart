package com.example.headstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


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
        startActivity(new Intent(HomeActivity.this, TruckOwnerLoginForm.class));
        Toast.makeText(HomeActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
    }
}