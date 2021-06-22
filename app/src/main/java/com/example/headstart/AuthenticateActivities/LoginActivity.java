package com.example.headstart.AuthenticateActivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.headstart.Home.HomeActivity;
import com.example.headstart.MainEntryActivity;
import com.example.headstart.R;
import com.example.headstart.Utility.PasswordUtils;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truck_owner_login_activity);

        //TextView
        //forget password
        TextView textViewForgetPassword = findViewById(R.id.forgetPassword);
        textViewForgetPassword.setOnClickListener(this);

        TextView textViewRegister = findViewById(R.id.registerButton);
        textViewRegister.setOnClickListener(this);

        //EditText
        editTextEmail = findViewById(R.id.emailAddress);
        editTextPassword = findViewById(R.id.userPassword);

        //Login Button
        Button btnLogin = findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(this);
        progressBar = findViewById(R.id.dr_progressBar);

        mAuth = FirebaseAuth.getInstance();

    }


    //On click listener
    @Override
    public void onClick(View v) {

        int viewId = v.getId();

        if (viewId == R.id.forgetPassword) {
            startActivity(new Intent(LoginActivity.this, ResetPassword.class));
        } else if (viewId == R.id.loginButton) {
            loginUser();
        } else if (viewId == R.id.registerButton) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }

    }


    //Function To Authenticate user
    private void loginUser() {
        //Get User Email and Password before logging in
        String emailAddress = editTextEmail.getText().toString().trim();
        String userPassword = editTextPassword.getText().toString().trim();

        //We Check for Validation and errors ....both email and password
        if (emailAddress.isEmpty()) {
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            editTextEmail.setError("Enter a Valid Email");
            editTextEmail.requestFocus();
            return;
        }
        //For password
        if (userPassword.isEmpty()) {
            editTextPassword.setError("Please Enter Password");
            editTextPassword.requestFocus();
            return;
        }
        if (!PasswordUtils.PASSWORD_PATTERN.matcher(userPassword).matches()) {
            editTextPassword.setError("Wrong Password'");
            editTextPassword.requestFocus();
            return;
        }

//        Turn progress bar to visible when onclick Login btn..Progress to true when Login button is clicked
        //progressBar.setVisibility(View.VISIBLE);
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View addDialog = getLayoutInflater().inflate(R.layout.loading_dialog, null);
        alertDialogBuilder.setView(addDialog);
        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        mAuth.signInWithEmailAndPassword(emailAddress, userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (mAuth.getCurrentUser().isEmailVerified()) {
                    //Redirect user to Home activity if Login success
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    //Show success message
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Please VERIFY your account ", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
            //progressBar.setVisibility(View.GONE);
            alertDialog.dismiss();

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, MainEntryActivity.class));
        finish();
    }
}