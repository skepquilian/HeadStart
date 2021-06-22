package com.example.headstart.AuthenticateActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.headstart.R;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmailReset;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //Button
        Button resetBtn = findViewById(R.id.resetButton);
        resetBtn.setOnClickListener(this);

        //EditText
        editTextEmailReset = findViewById(R.id.emailAddress);

        progressBar = findViewById(R.id.dr_progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.resetButton) {
            resetPassword();
        }

    }

    private void resetPassword() {
        //Get the email address
        String emailAddress = editTextEmailReset.getText().toString().trim();

        //Check validations
        if (emailAddress.isEmpty()) {
            editTextEmailReset.setError("Email Field Required");
            editTextEmailReset.requestFocus();
            return;
        }
        //Check if Email is Valid
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            editTextEmailReset.setError("Please provide valid email");
            editTextEmailReset.requestFocus();
        }

        //set progressBar to Visible when Reset BTN is Clicked
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(task -> {
            //Check if Task is Successful
            if (task.isSuccessful()) {
                Toast.makeText(ResetPassword.this, "Password Request Sent....Check Email", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ResetPassword.this, "Password Request Failed....TRY AGAIN!!!", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        }).addOnFailureListener(e -> {
            //get and show proper error message
            Toast.makeText(ResetPassword.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    //On back press to redirect to login page
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ResetPassword.this, LoginActivity.class));
        finish();
    }
}