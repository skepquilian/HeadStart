package com.example.headstart.MaintenanceSchedule;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.headstart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TasksActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "TasksActivity";
//    private EditText taskName;
    private EditText taskDate;
    private Spinner driverSpinner;
    private ArrayList<String> driverList;
    private ArrayAdapter<String> driverAdapter;
    private DatabaseReference driverDatabaseRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_layout_activity);

        ImageButton closeBTN = findViewById(R.id.close_tasks_activityBTN);
        closeBTN.setOnClickListener(this);

        taskDate = findViewById(R.id.task_date_id);
        taskDate.setOnClickListener(this);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        //take driver info to firebase database
        //get users id as child(Foreign Key)
        driverDatabaseRef = FirebaseDatabase.getInstance().getReference("User Drivers")
                //get users id as child(Foreign Key)
                .child(auth.getCurrentUser().getUid());

        driverList = new ArrayList<String>();

        addDriver();
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        if (itemId == R.id.close_tasks_activityBTN){
            onBackPressed();
        }
        else if (itemId == R.id.task_date_id){
            addDate();
        }
    }

    private void addDriver() {
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
                    String drivers = driverFirstname + " "+ driversLastname;
                    Log.i(TAG, "onDataChange: Driver FullName is added to list for Selection");
                    driverList.add(drivers);
                }
                driverSpinner = findViewById(R.id.driver_spinner);
                driverAdapter = new ArrayAdapter<String>(TasksActivity.this, android.R.layout.simple_spinner_item, driverList);
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
        taskDate.setText(datePickedString);

    }
}
