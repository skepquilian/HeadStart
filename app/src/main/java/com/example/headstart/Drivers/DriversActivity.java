package com.example.headstart.Drivers;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriversActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private static final String TAG = "DriversActivity";
    private BottomNavigationView bottomNavigationView;
    DriverAdapter driverAdapter;
    private ProgressBar progressBar;

    RecyclerView recyclerView;

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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        auth = FirebaseAuth.getInstance();

        //take driver info to firebase database
        //get users id as child(Foreign Key)
        //private ArrayList<Drivers> driverList;
        DatabaseReference driverDatabaseRef = FirebaseDatabase.getInstance().getReference("User Drivers")
                //get users id as child(Foreign Key)
                .child(auth.getCurrentUser().getUid());


        //driverList = new ArrayList<>();
        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Drivers> options
                = new FirebaseRecyclerOptions.Builder<Drivers>()
                .setQuery(driverDatabaseRef, Drivers.class)
                .build();
        driverAdapter = new DriverAdapter(options);
        recyclerView.setAdapter(driverAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
        Log.i(TAG, "onStart: Refresh page when user swipe down");
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            //checks if phone is connected to the internet
            Log.i(TAG, "onRefresh: checks if Internet connected");
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(networkChangeListener, intentFilter);
            if (swipeRefreshLayout.isRefreshing()) {
                Log.i(TAG, "onRefresh: Driver list is not empty");
                driverAdapter.startListening();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        driverAdapter.startListening();

        //When data is not fetched show progressBar with fetch data textView
        progressBar = findViewById(R.id.dr_progressBar);
        progressBar.setVisibility(View.GONE);
        TextView fetchDataTextView = findViewById(R.id.fetchData);
        fetchDataTextView.setVisibility(View.GONE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_options_menu, menu);

        //Search Bar Menu
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
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