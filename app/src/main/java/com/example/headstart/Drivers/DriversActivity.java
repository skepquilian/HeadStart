package com.example.headstart.Drivers;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.headstart.Home.HomeActivity;
import com.example.headstart.MaintenanceSchedule.MaintenanceActivity;
import com.example.headstart.Map.MapActivity;
import com.example.headstart.R;
import com.example.headstart.Settings.SettingsActivity;
import com.example.headstart.Utility.NetworkChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DriversActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private static final String TAG = "DriversActivity";
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private ArrayList<Drivers> driverList;
    private DatabaseReference driverDatabaseRef;
    private TextView fetchDataTextView;

    SwipeRefreshLayout swipeRefreshLayout;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    DriverDialog driverDialog = new DriverDialog(this);

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        FloatingActionButton addDriverFloatBtn = findViewById(R.id.addDriver_fab);
        addDriverFloatBtn.setOnClickListener(this);

        FloatingActionButton mapFloatBtn = findViewById(R.id.map_floatBar);
        mapFloatBtn.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        //take driver info to firebase database
        driverDatabaseRef = FirebaseDatabase.getInstance().getReference("User Drivers")
                //get users id as child(Foreign Key)
                .child(auth.getCurrentUser().getUid());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        driverList = new ArrayList<Drivers>();

    }


    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();

        Log.i(TAG, "onStart: Refresh page when user swipe down");
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //checks if phone is connected to the internet
                Log.i(TAG, "onRefresh: checks if Internet connected");
                IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                registerReceiver(networkChangeListener, intentFilter);
                if (swipeRefreshLayout.isRefreshing()) {
                    if (!driverList.isEmpty()) {
                        Log.i(TAG, "onRefresh: Driver list is not empty");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
        //Load data
        driverDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //before data is fetched from firebase, clear list
                Log.i(TAG, "onDataChange: data is cleared in adapter. User adds new driver ");
                driverList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Drivers drivers = dataSnapshot.getValue(Drivers.class);
                    Log.i(TAG, "onDataChange: adds drivers to list ");
                    driverList.add(drivers);

                    //When data is not fetched show progressBar with fetch data textView
                    // progressBar = findViewById(R.id.progressBar);
                    // progressBar.setVisibility(View.GONE);
                    fetchDataTextView = findViewById(R.id.fetchData);
                    fetchDataTextView.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
                DriverAdapter driverAdapter = new DriverAdapter(DriversActivity.this, driverList);
                recyclerView.setAdapter(driverAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //
            }
        });
    }


    /**
     * overridePending Remove inter-activity transition to avoid
     * screen tossing on tapping bottom navigation items
     */
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStop() {
        //TODO I am to check -- network state when app stops
        //unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        int itemId = v.getId();

        if (itemId == R.id.addDriver_fab) {
//            startActivity(new Intent(DriversActivity.this, AddDriverActivity.class));
            driverDialog();
        } else if (itemId == R.id.map_floatBar) {
            startActivity(new Intent(DriversActivity.this, MapActivity.class));
        }

    }

    /**
     * Add driver info Dialog function
     */
    private void driverDialog() {
        Log.i(TAG, "driverDialog: calls dialog class with its function");
        driverDialog.addDriverDialog();
    }


    /**
     * Bottom Navigation bar
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            //home activity
            startActivity(new Intent(this, HomeActivity.class));
            return true;
        } else if (itemId == R.id.nav_maintenance) {
            //truck activity
            startActivity(new Intent(this, MaintenanceActivity.class));
            return true;
        } else if (itemId == R.id.nav_map) {
            //truck activity
            return true;
        } else if (itemId == R.id.nav_drivers) {
            //drivers activity
            startActivity(new Intent(this, DriversActivity.class));
            return true;
        } else if (itemId == R.id.nav_settings) {
            //settings activity
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return false;
    }


    /**
     * menu Item select focus function
     */
    private void updateNavigationBarState() {
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DriversActivity.this, HomeActivity.class));
        finish();
    }

}