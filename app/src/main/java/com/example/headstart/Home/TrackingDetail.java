package com.example.headstart.Home;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.MaintenanceSchedule.Schedules;
import com.example.headstart.PhoneTracker.TrackerObject;
import com.example.headstart.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrackingDetail extends Fragment implements View.OnClickListener {

    private static final String TAG = "TrackingDetail";
    MaterialButton addTracker;
    EditText editVehicleID;
    Spinner driverListSpinner;
    BottomSheetDialog bottomSheetDialog;
    FirebaseAuth auth;
    RecyclerView recyclerView;

    private ArrayList<String> driverList;
    private ArrayList<String> offlineList;
    private ArrayAdapter<String> driverAdapter;

    private ArrayList<TrackerObject> trackerList;
    private TrackerAdapter trackerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);

        auth = FirebaseAuth.getInstance();
        addTracker = view.findViewById(R.id.add_trackerBTN);
        addTracker.setOnClickListener(this);
        driverListSpinner = view.findViewById(R.id.driver_spinner);

        trackerList = new ArrayList<>();
        trackerAdapter = new TrackerAdapter(getContext(), trackerList);
        recyclerView = view.findViewById(R.id.tracker_gridView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));


        DatabaseReference trackerDatabaseRef = FirebaseDatabase.getInstance().getReference("Tracker")
                .child(auth.getCurrentUser().getUid());
        trackerDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trackerList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TrackerObject trackerObject = dataSnapshot.getValue(TrackerObject.class);
                    trackerList.add(trackerObject);
                }
                recyclerView.setAdapter(trackerAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_error_dialog);

        return view;
    }

    @Override
    public void onClick(View v) {
        int itemID = v.getId();
        if (itemID == R.id.add_trackerBTN) {
            addTrackersDailog();
        }
    }


    public void addTrackersDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alertDialog;
        final View addDriverDialog = getActivity().getLayoutInflater().inflate(R.layout.add_tracker_dailog, null);

        //EditText boxes
        editVehicleID = addDriverDialog.findViewById(R.id.vehicle_Id);
        driverListSpinner = addDriverDialog.findViewById(R.id.driver_spinner);

        driverList = new ArrayList<>();
        offlineList = new ArrayList<>();

        Log.i(TAG, "addTrackerDailog: User is on Connected to Internet");
        addDriverSpinner();
        Log.i(TAG, "addTrackerDailog: User is not Internet connected");
        isOfflineSpinner();

        //
        builder.setView(addDriverDialog);
        alertDialog = builder.create();

        //make root parent view transparent
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        //add button
        FloatingActionButton addDriverFloatBtn = addDriverDialog.findViewById(R.id.addTracker_float_Btn);
        addDriverFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //perform this function
                submitTracker();
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


    private void submitTracker() {
        DatabaseReference trackerDatabaseRef = FirebaseDatabase.getInstance().getReference("Tracker")
                .child(auth.getCurrentUser().getUid());

        String trackerDevice = editVehicleID.getText().toString().toUpperCase().trim();
        final String associateDriver = driverListSpinner.getSelectedItem().toString();

        if (trackerDevice.isEmpty()) {
            Log.i(TAG, "Tracking: BottomSheet Error message 1");
            bottomSheetDialog.show();
            //dismissDialog after seconds
            Log.i(TAG, "Tracking: Error message dismissed");
            dismissErrorDialog();
        }
        if (associateDriver == driverListSpinner.getItemAtPosition(0)) {
            Log.i(TAG, "Tracking: BottomSheet Error message 1");
            bottomSheetDialog.show();
            //dismissDialog after seconds
            Log.i(TAG, "Tracking: Error message dismissed");
            dismissErrorDialog();
        } else {
            String tracker_id = trackerDatabaseRef.push().getKey();

            final TrackerObject trackerObject = new TrackerObject(
                    trackerDevice,
                    associateDriver
            );
            trackerDatabaseRef.child(tracker_id)
                    .setValue(trackerObject).addOnCompleteListener(task -> {
                if (task.isComplete()) {
                    editVehicleID.setText("");
                    Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_LONG).show();
                    driverListSpinner.setSelection(0);
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     *dismissing bottom dialog
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
                driverListSpinner.setSelection(0);
                driverAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, driverList);
                driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                driverAdapter.notifyDataSetChanged();
                Log.i(TAG, "onDataChange: Adapter is ready");
                driverListSpinner.setAdapter(driverAdapter);

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
        driverAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, offlineList);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.i(TAG, "onDataChange: Adapter is ready");
        driverListSpinner.setAdapter(driverAdapter);
    }
}
