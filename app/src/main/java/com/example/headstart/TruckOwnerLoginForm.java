package com.example.headstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TruckOwnerLoginForm extends AppCompatActivity implements View.OnClickListener{

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

        TextView textViewRegister = findViewById(R.id.registerUser);
        textViewRegister.setOnClickListener(this);

        //EditText
        editTextEmail       = findViewById(R.id.emailAddress);
        editTextPassword    = findViewById(R.id.userPassword);

        //Login Button
        Button btnLogin = findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

    }


    //On click listener
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.forgetPassword){
            startActivity(new Intent(TruckOwnerLoginForm.this, ResetPassword.class));
            return;
        }
        if (v.getId() == R.id.loginButton) {
            loginUser();
            return;
        }
        if (v.getId() == R.id.registerUser) {
            startActivity(new Intent(TruckOwnerLoginForm.this, TruckOwnerRegisterForm.class));
        }

    }


    //Function To Authenticate user
    private void loginUser() {
        //Get User Email and Password before logging in
        String emailAddress = editTextEmail.getText().toString().trim();
        String userPassword = editTextPassword.getText().toString().trim();

        //We Check for Validation and errors ....both email and password
        if (emailAddress.isEmpty()){
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            editTextEmail.setError("Enter a Valid Email");
            editTextEmail.requestFocus();
            return;
        }
        //For password
        if (userPassword.isEmpty()){
            editTextPassword.setError("Please Enter Password");
            editTextPassword.requestFocus();
            return;
        }
        if (userPassword.length() < 8){
            editTextPassword.setError("Minimum characters must be 8");
            editTextPassword.requestFocus();
        }

        //Turn progress bar to visible when onclick Login btn..Progress to true when Login button is clicked
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(emailAddress, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //Redirect user to Home activity if Login success
                    startActivity(new Intent(TruckOwnerLoginForm.this, HomeActivity.class));
                    //Show success message
                    Toast.makeText(TruckOwnerLoginForm.this, "Login Successful", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(TruckOwnerLoginForm.this, "Login Failed.  TRY AGAIN", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    public void onBackPressed() {
        super.onBackPressed();
    }
}