package com.example.headstart.Drivers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.headstart.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class AddDriverActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editFirstName, editLastName, editPhoneNumber,
            editEmail, editDriverID, editVehicleID;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        editFirstName = findViewById(R.id.driverFirstName);
        editLastName = findViewById(R.id.driverLastName);

        editPhoneNumber = findViewById(R.id.driverPhoneNumber);
        editPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        editEmail = findViewById(R.id.driverEmailAddress);
        editDriverID = findViewById(R.id.driver_Id);
        editVehicleID = findViewById(R.id.vehicle_Id);

        floatingActionButton = findViewById(R.id.addDriver_Btn);
        floatingActionButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int itemID = v.getId();

        if (itemID == R.id.addDriver_Btn) {
            //function to add driver to database
            addDriver();
        }
    }

    private void addDriver() {
        String driverFirstName = editFirstName.getText().toString().trim();
        String driverLastName = editLastName.getText().toString().trim();
        String driverPhone = editPhoneNumber.getText().toString().trim();
        String driverEmail = editEmail.getText().toString().trim();
        String driverID = editDriverID.getText().toString().trim();
        String vehicleID = editVehicleID.getText().toString().trim();

        //Now we Need If Statements to validate these Inputs..Check validation
        if (driverFirstName.isEmpty()) {
            editFirstName.setError("FirstName is required");
            editFirstName.requestFocus();
        }
        else if (driverLastName.isEmpty()){
            editLastName.setError("LastName is required");
            editLastName.requestFocus();
        }
        else if (driverPhone.isEmpty() || (driverPhone.length() < 10)) {
            editPhoneNumber.setError("Enter a valid Phone Number");
            editPhoneNumber.requestFocus();
        }
        else if (driverID.isEmpty()){
            editDriverID.setError("Driver Id is required");
            editDriverID.requestFocus();
        }
        else if (vehicleID.isEmpty()){
            editVehicleID.setError("LastName is required");
            editVehicleID.requestFocus();
        }

        //add driver info when user clicks add button

    }
}