package com.example.headstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class TruckOwnerRegisterForm extends AppCompatActivity implements View.OnClickListener {


    //For password validation, before user can signup succesfully, user must provide
    //password which contains the list below,
    public static final Pattern PASSWORD_PATTERN
            = Pattern.compile(
                    //you can also changed some based on requirements
                    "^" +
                    "(?=.*[0-9])" +                 // at least 1 digit
                    "(?=.*[a-z])" +                 // at least 1 lower case letter
                    "(?=.*[A-Z])" +                 // at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +            // at least 1 special character
                    "(?=\\S+$)" +                   // no white spaces
                    ".{8,}" +                       // at least 8 characters
                    "$"                             //
    );

    private EditText    editTextOrganizationName, editTextEmail,
                        editTextPhone, editTextPassword, editTextPasswordConfirm;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    public TruckOwnerRegisterForm() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truck_owner_register_activity);

        //TextView
        TextView textViewAppName = findViewById(R.id.appName);
        textViewAppName.setOnClickListener(this);

        TextView textViewLogin   = findViewById(R.id.loginUser);
        textViewLogin.setOnClickListener(this);

        //EditText
        editTextOrganizationName = findViewById(R.id.organizationName);
        editTextEmail            = findViewById(R.id.emailAddress);

        editTextPhone            = findViewById(R.id.phoneNumber);
        editTextPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        editTextPassword         = findViewById(R.id.userPassword);
        editTextPasswordConfirm  = findViewById(R.id.passwordConfirm);

        //register Button
        Button btnRegister = findViewById(R.id.registerButton);
        btnRegister.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

    }

    //Onclick Listener for all fields with this property
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.appName || v.getId() == R.id.loginUser){
            startActivity(new Intent(this, TruckOwnerLoginForm.class));
            return;
        }
        if (v.getId() == R.id.registerButton){
            registerUser();
        }
    }


    //Register User Function....
    private void registerUser() {
        final String organizationName = editTextOrganizationName.getText().toString().trim();
        final String emailAddress     = editTextEmail.getText().toString().trim();
        final String phoneNumber      = editTextPhone.getText().toString().trim();
        String userPassword           = editTextPassword.getText().toString().trim();
        String passwordConfirm        = editTextPasswordConfirm.getText().toString().trim();

        //Now we Need If Statements to validate these Inputs..Check validation
        if (organizationName.isEmpty()){
            editTextOrganizationName.setError("Name is required");
            editTextOrganizationName.requestFocus();
            return;

        }
        //Check if Email is Valid
        if (emailAddress.isEmpty()){
            editTextEmail.setError("Email Field Required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty() || (phoneNumber.length() < 10)){
            editTextPhone.setError("Enter a valid Phone Number");
            editTextPhone.requestFocus();
            return;
        }
        //Password validation
        if (userPassword.isEmpty()){
            editTextPassword.setError("Provide password");
            editTextPassword.requestFocus();
            return;
        }
        if (passwordConfirm.isEmpty()){
            editTextPasswordConfirm.setError("Provide password confirmation");
            editTextPasswordConfirm.requestFocus();
            return;
        }
        if (!PASSWORD_PATTERN.matcher(userPassword).matches()){
            editTextPassword.setError("Password must contain at least 8 char(uppercase & lowercase, special Char, number)..eg.'@Example1'");
            editTextPassword.requestFocus();
            return;
        }
        if (!passwordConfirm.equals(userPassword)){
            editTextPasswordConfirm.setError("Password did not match! Re-enter");
            editTextPasswordConfirm.requestFocus();
        }

        //set progress Bar to visible when register button is clicked
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailAddress,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    //Create Users info with these below
                    User user = new User(organizationName, emailAddress, phoneNumber);

                    //take users info to firebase database
                    FirebaseDatabase.getInstance().getReference("Users")

                            //then give the registered user an id using the below, make id correspond to user when registered
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                            //check if user is registered successfully
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                //Redirect to Login Page
                                startActivity(new Intent(TruckOwnerRegisterForm.this, TruckOwnerLoginForm.class));
                                Toast.makeText(TruckOwnerRegisterForm.this, "Registration successful...Login Now", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(TruckOwnerRegisterForm.this, "Registration Failed. TRY AGAIN", Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else {
                    Toast.makeText(TruckOwnerRegisterForm.this, "Registration Failed. TRY AGAIN", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    //On back press to redirect to login page
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TruckOwnerRegisterForm.this, TruckOwnerLoginForm.class));
    }
}