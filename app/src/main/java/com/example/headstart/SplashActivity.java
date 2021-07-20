package com.example.headstart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.headstart.AuthenticateActivities.LoginActivity;
import com.example.headstart.Home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //counter
        long secondsCount = 2000;

        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(() -> {
            if (firebaseUser != null){
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }
            else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();

        }, secondsCount);
    }
}
