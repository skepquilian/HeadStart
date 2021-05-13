package com.example.headstart.Drivers;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.headstart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverDialog {
    private AlertDialog alertDialog;
    private final Activity myActivity;

    private final DatabaseReference driverDatabaseRef;
    FirebaseAuth auth;

    private TextInputEditText editFirstName, editLastName, editPhoneNumber,
            editEmail, editDriverID, editVehicleID;

    public DriverDialog(Activity activity) {
        auth = FirebaseAuth.getInstance();

        //take driver info to firebase database
        driverDatabaseRef = FirebaseDatabase.getInstance().getReference("User Drivers")
                //get users id as child(Foreign Key)
                .child(auth.getCurrentUser().getUid());
        myActivity = activity;
    }

    /**
     * Add driver info Dialog function
     */
    public void addDriverDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(myActivity);

        final View addDriverDialog = myActivity.getLayoutInflater().inflate(R.layout.dialog_add_driver, null);


        //EditText boxes
        editFirstName = addDriverDialog.findViewById(R.id.driverFirstName);
        editLastName = addDriverDialog.findViewById(R.id.driverLastName);
        editPhoneNumber = addDriverDialog.findViewById(R.id.driverPhoneNumber);
        editEmail = addDriverDialog.findViewById(R.id.driverEmailAddress);
        editDriverID = addDriverDialog.findViewById(R.id.driver_Id);
        editVehicleID = addDriverDialog.findViewById(R.id.vehicle_Id);

        //
        alertDialogBuilder.setView(addDriverDialog);
        alertDialog = alertDialogBuilder.create();

        //make root parent view transparent
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        //add button
        FloatingActionButton addDriverFloatBtn = addDriverDialog.findViewById(R.id.addDriver_float_Btn);
        addDriverFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //perform this function
                addDriver();
            }
        });

        //cancel button
        FloatingActionButton cancelFloatBtn = addDriverDialog.findViewById(R.id.cancel_float_Btn);
        cancelFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    /**
     * adding driver to database
     */
    public void addDriver() {
        String driverFirstName = editFirstName.getText().toString().toUpperCase().trim();
        String driverLastName = editLastName.getText().toString().toUpperCase().trim();
        String driverPhone = editPhoneNumber.getText().toString().trim();
        String driverEmail = editEmail.getText().toString().trim();
        String driverID = editDriverID.getText().toString().trim();
        String vehicleID = editVehicleID.getText().toString().trim();

        //If Statements to validate these Inputs..Check validation
        if (driverFirstName.isEmpty()) {
            editFirstName.setError("FirstName is required");
            editFirstName.requestFocus();
        } else if (driverLastName.isEmpty()) {
            editLastName.setError("LastName is required");
            editLastName.requestFocus();
        } else if (driverPhone.isEmpty() || (driverPhone.length() < 10)) {
            editPhoneNumber.setError("Enter a valid Phone Number");
            editPhoneNumber.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(driverEmail).matches() && driverEmail.isEmpty()) {
            editEmail.setError("Please provide valid email");
            editEmail.requestFocus();
        } else if (driverID.isEmpty()) {
            editDriverID.setError("Driver Id is required");
            editDriverID.requestFocus();
        } else if (vehicleID.isEmpty()) {
            editVehicleID.setError("LastName is required");
            editVehicleID.requestFocus();
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            try {
                // User is signed in

                //Create unique string id and stores in driver_Id
                String driver_Id = driverDatabaseRef.push().getKey();

                //add driver info when user clicks add button
                //Create Driver info with these below
                final Drivers drivers = new Drivers(
                        driverFirstName,
                        driverLastName,
                        driverPhone,
                        driverEmail,
                        driverID,
                        vehicleID
                );

                driverDatabaseRef.child(driver_Id)
                        .setValue(drivers).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            editFirstName.setText("");
                            editLastName.setText("");
                            editPhoneNumber.setText("");
                            editEmail.setText("");
                            editDriverID.setText("");
                            editVehicleID.setText("");

                            Toast.makeText(myActivity, "Added successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(myActivity, "Error Please Try again", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            } catch (Exception e) {
                Toast.makeText(myActivity, "Error " + e, Toast.LENGTH_LONG).show();
            }

            // TODO " work on float BTN to handle submission error "


        } else {
            // No user is signed in
            Toast.makeText(myActivity, "Failed....Login to get Access", Toast.LENGTH_LONG).show();
        }

    }
}
