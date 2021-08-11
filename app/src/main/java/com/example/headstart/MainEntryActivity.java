package com.example.headstart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.headstart.AuthenticateActivities.LoginActivity;

public class MainEntryActivity extends AppCompatActivity implements View.OnClickListener {

    Button supplierBnt;
    Button customerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        supplierBnt = findViewById(R.id.trackingBtn);
        supplierBnt.setOnClickListener(this);

        customerBtn = findViewById(R.id.customerBtn);
        customerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

          //User clicks as Admin btn redirect to Admin Login Page
        if (v.getId() == R.id.customerBtn) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        // Or if it's tracking option then redirect to tracking page....
//        if (v.getId() == R.id.trackingBtn){
//            startActivity(new Intent(this, Tracker.class));
//        }
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainEntryActivity.this)
                .setTitle("Quit Application")
                .setCancelable(false)
                .setMessage("Are your sure to exit?")
                .setPositiveButton("Yes", (dialog, which) -> ActivityCompat.finishAffinity(MainEntryActivity.this))
                .setNegativeButton("No", (dialogInterface, i) -> {

                });

        builder.show();

    }

}