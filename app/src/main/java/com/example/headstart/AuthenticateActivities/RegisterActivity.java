package com.example.headstart.AuthenticateActivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.headstart.R;
import com.example.headstart.Utility.PasswordUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextOrganizationName, editTextEmail,
            editTextPhone, editTextPassword, editTextPasswordConfirm;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truck_owner_register_activity);

        //TextView
        TextView textViewAppName = findViewById(R.id.appName);
        textViewAppName.setOnClickListener(this);

        TextView textViewLogin = findViewById(R.id.loginUser);
        textViewLogin.setOnClickListener(this);

        //EditText
        editTextOrganizationName = findViewById(R.id.organizationName);
        editTextEmail = findViewById(R.id.emailAddress);

        editTextPhone = findViewById(R.id.phoneNumber);
        editTextPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        editTextPassword = findViewById(R.id.userPassword);
        editTextPasswordConfirm = findViewById(R.id.passwordConfirm);

        //register Button
        Button btnRegister = findViewById(R.id.registerButton);
        btnRegister.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

    }

    //Onclick Listener for all fields with this property
    @Override
    public void onClick(View v) {

        int view = v.getId();

        if (view == R.id.appName || view == R.id.loginUser) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (view == R.id.registerButton) {
            registerUser();
        }
    }


    //Register User Function....
    private void registerUser() {
        final String organizationName = editTextOrganizationName.getText().toString().trim();
        final String emailAddress = editTextEmail.getText().toString().trim();
        final String phoneNumber = editTextPhone.getText().toString().trim();
        String userPassword = editTextPassword.getText().toString().trim();
        String passwordConfirm = editTextPasswordConfirm.getText().toString().trim();

        //Now we Need If Statements to validate these Inputs..Check validation
        if (organizationName.isEmpty()) {
            editTextOrganizationName.setError("Name is required");
            editTextOrganizationName.requestFocus();
            return;

        }
        //Check if Email is Valid
        if (emailAddress.isEmpty()) {
            editTextEmail.setError("Email Field Required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty() || (phoneNumber.length() < 10)) {
            editTextPhone.setError("Enter a valid Phone Number");
            editTextPhone.requestFocus();
            return;
        }
        //Password validation
        if (userPassword.isEmpty()) {
            editTextPassword.setError("Provide password");
            editTextPassword.requestFocus();
            return;
        }
        if (passwordConfirm.isEmpty()) {
            editTextPasswordConfirm.setError("Provide password confirmation");
            editTextPasswordConfirm.requestFocus();
            return;
        }
        if (!PasswordUtils.PASSWORD_PATTERN.matcher(userPassword).matches()) {
            editTextPassword.setError("Password must contain at least 8 char(uppercase & lowercase, special Char, number)..eg.'@Example1'");
            editTextPassword.requestFocus();
            return;
        }
        if (!passwordConfirm.equals(userPassword)) {
            editTextPasswordConfirm.setError("Password did not match! Re-enter");
            editTextPasswordConfirm.requestFocus();
            return;
        }

        //set progress Bar to visible when register button is clicked
        // progressBar.setVisibility(View.VISIBLE);
        final AlertDialog alertDialog;
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View addDialog = getLayoutInflater().inflate(R.layout.loading_dialog, null);
        alertDialogBuilder.setView(addDialog);
        alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        mAuth.createUserWithEmailAndPassword(emailAddress, userPassword).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                //Create Users info with these below
                final User user = new User(organizationName, emailAddress, phoneNumber);

                //then get the registered user ID using the below, make id correspond to user when registered
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(mAuth.getCurrentUser().getUid())
                        //set name,email&password to the current user
                        .setValue(user);

                //send email verification if user is registered successfully
                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //check if user is registered successfully
                        if (task.isSuccessful()) {
                            //Redirect to Login Page if registration is successful
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            Toast.makeText(RegisterActivity.this, "Account registered...VERIFY email Now", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration Failed. TRY AGAIN", Toast.LENGTH_LONG).show();
                        }
                        //progressBar.setVisibility(View.GONE);
                        alertDialog.dismiss();
                    }
                });
            } else {
                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                // progressBar.setVisibility(View.GONE);
                alertDialog.dismiss();
            }
        });
    }

    //On back press to redirect to login page
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}