package com.example.headstart.MaintenanceSchedule;

import android.app.DatePickerDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.headstart.R;
import com.example.headstart.Utility.NetworkChangeListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TasksActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    private static final String TAG = "TasksActivity";
    private EditText task_name;
    private EditText task_date;
    private Spinner driverSpinner;
    private BottomSheetDialog bottomSheetDialog;
    private ArrayList<String> driverList;
    private ArrayList<String> offlineList;
    private ArrayAdapter<String> driverAdapter;
    private FirebaseAuth auth;

    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_layout_activity);

        ImageButton closeBTN = findViewById(R.id.close_tasks_activityBTN);
        closeBTN.setOnClickListener(this);

        task_name = findViewById(R.id.task_name_id);
        task_date = findViewById(R.id.task_date_id);
        task_date.setOnClickListener(this);

        driverSpinner = findViewById(R.id.driver_spinner);

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_error_dialog);

        FloatingActionButton submitTaskBTN = findViewById(R.id.submit_task_float_id);
        submitTaskBTN.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        driverList = new ArrayList<>();
        offlineList = new ArrayList<>();

        Log.i(TAG, "onCreate: User is on Connected to Internet");
        addDriverSpinner();

        Log.i(TAG, "onCreate: User is not Internet connected");
        isOfflineSpinner();
    }

    /**
     * @param v onclick view listener
     */
    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        if (itemId == R.id.close_tasks_activityBTN) {
            onBackPressed();
        } else if (itemId == R.id.task_date_id) {
            addDate();
        } else if (itemId == R.id.submit_task_float_id) {
            scheduleMaintenance();
        }
    }

    /**
     * function for maintenance schedule
     */
    private void scheduleMaintenance() {
        DatabaseReference scheduleDatabaseRef = FirebaseDatabase.getInstance().getReference("Schedules")
                .child(auth.getCurrentUser().getUid());

        //checks if phone is connected to the internet
        Log.i(TAG, "onRefresh: checks if Internet connected");
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, intentFilter);

        final String taskName = task_name.getText().toString().trim();
        final String taskDate = task_date.getText().toString();
        final String taskDriverName = driverSpinner.getSelectedItem().toString();

        //If Statements to validate these Inputs..Check validation
        if (taskName.isEmpty()) {
            Log.i(TAG, "scheduleMaintenance: BottomSheet Error message 1");
            bottomSheetDialog.show();
            //dismissDialog after seconds
            Log.i(TAG, "scheduleMaintenance: Error message dismissed");
            dismissErrorDialog();
        } else if (taskDate.isEmpty()) {
            Log.i(TAG, "scheduleMaintenance: BottomSheet Error message 1");
            bottomSheetDialog.show();
            //dismissDialog after seconds
            Log.i(TAG, "scheduleMaintenance: Error message dismissed");
            dismissErrorDialog();
        }
        //Todo : error is here .... check if isInternet connected
        if (taskDriverName == driverSpinner.getItemAtPosition(0)) {
            Log.i(TAG, "scheduleMaintenance: BottomSheet Error message 1");
            bottomSheetDialog.show();
            //dismissDialog after seconds
            Log.i(TAG, "scheduleMaintenance: Error message dismissed");
            dismissErrorDialog();
        } else {
            String schedule_id = scheduleDatabaseRef.push().getKey();

            final Schedules schedules = new Schedules(
                    taskName,
                    taskDate,
                    taskDriverName
            );
            scheduleDatabaseRef.child(schedule_id)
                    .setValue(schedules).addOnCompleteListener(task -> {
                if (task.isComplete()) {
                    task_name.setText("");
                    task_date.setText("");
                    driverSpinner.setSelection(0);

                    Toast.makeText(TasksActivity.this, "Added successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TasksActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    /**
     *
     */
    private void dismissErrorDialog() {
        long secondsCount = 2000;
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(() -> {
            bottomSheetDialog.dismiss();
        }, secondsCount);
    }

    /**
     * function for adding Driver name to Spinner dropdown
     */
    private void addDriverSpinner() {
        //take driver info to firebase database
        DatabaseReference driverDatabaseRef = FirebaseDatabase.getInstance().getReference("User Drivers")
                //get users id as child(Foreign Key)
                .child(auth.getCurrentUser().getUid());

        driverDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //before data is fetched from firebase, clear list
                Log.i(TAG, "onDataChange: list cleared in adapter. user has added or updated info");
                driverList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.i(TAG, "onDataChange: firstname and lastname Retrieved");
                    String driverFirstname = dataSnapshot.child("firstName").getValue().toString().toUpperCase();
                    String driversLastname = dataSnapshot.child("lastName").getValue().toString().toUpperCase();
                    Log.i(TAG, "onDataChange: Driver full name is Concatenated");
                    String drivers = driverFirstname + " " + driversLastname;
                    Log.i(TAG, "onDataChange: Driver FullName is added to list for Selection");
                    driverList.add(drivers);
                }
                //Default value in dropdown
                driverList.add(0, "Choose Driver");
                driverSpinner.setSelection(0);
                driverAdapter = new ArrayAdapter<>(TasksActivity.this, android.R.layout.simple_spinner_item, driverList);
                driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                driverAdapter.notifyDataSetChanged();
                Log.i(TAG, "onDataChange: Adapter is ready");
                driverSpinner.setAdapter(driverAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void isOfflineSpinner() {
        Log.i(TAG, "isOffline: User is offline, show offline list to user.");
        //Default value in dropdown
        offlineList.add(0, "You are Offline");
        offlineList.add(1, "Driver Data is unavailable");
        driverAdapter = new ArrayAdapter<>(TasksActivity.this, android.R.layout.simple_spinner_item, offlineList);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.i(TAG, "onDataChange: Adapter is ready");
        driverSpinner.setAdapter(driverAdapter);
    }

    /**
     * function for adding date for scheduling
     */
    private void addDate() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String datePickedString = DateFormat.getDateInstance().format(calendar.getTime());
        task_date.setText(datePickedString);
    }
}
