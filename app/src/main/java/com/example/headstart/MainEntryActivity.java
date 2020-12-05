package com.example.headstart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.headstart.AuthenticateActivities.TruckOwnerLoginForm;
import com.example.headstart.AuthenticateActivities.User;

public class MainEntryActivity extends AppCompatActivity implements View.OnClickListener {

    Button supplierBnt;
    Button customerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        supplierBnt = findViewById(R.id.supplierbtn);
        supplierBnt.setOnClickListener(this);

        customerBtn = findViewById(R.id.customerbtn);
        customerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /**
         * User clicks supplier btn redirect to Login Page
         */
        if (v.getId() == R.id.supplierbtn) {
            startActivity(new Intent(this, TruckOwnerLoginForm.class));
        }
        // Or if user is customer then redirect to customer page....
        /*if (v.getId() == R.id.customerbtn){
            startActivity(new Intent());
        }*/
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainEntryActivity.this)
                .setTitle("Quit Application")
                .setCancelable(false)
                .setMessage("Are your sure want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(MainEntryActivity.this);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        builder.show();

    }

}