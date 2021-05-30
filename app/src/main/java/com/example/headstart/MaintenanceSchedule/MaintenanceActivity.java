package com.example.headstart.MaintenanceSchedule;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headstart.Drivers.DriversActivity;
import com.example.headstart.Home.HomeActivity;
import com.example.headstart.Map.MapActivity;
import com.example.headstart.R;
import com.example.headstart.Settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MaintenanceActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private static final String TAG = "MaintenanceActivity";
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;
    private ArrayList<Schedules> scheduleList;
    private DatabaseReference scheduleDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        FloatingActionButton mapFloatBtn = findViewById(R.id.map_floatBar);
        mapFloatBtn.setOnClickListener(this);

        TextView taskTextViewBTN = findViewById(R.id.task_id);
        taskTextViewBTN.setOnClickListener(this);

        scheduleList = new ArrayList<>();


        scheduleAdapter = new ScheduleAdapter(MaintenanceActivity.this, scheduleList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseAuth auth = FirebaseAuth.getInstance();

        scheduleDatabaseRef = FirebaseDatabase.getInstance().getReference("Schedules")
                .child(auth.getCurrentUser().getUid());

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();

        scheduleDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scheduleList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Schedules schedules = dataSnapshot.getValue(Schedules.class);
                    scheduleList.add(schedules);
                }
                recyclerView.setAdapter(scheduleAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_options_menu, menu);

        //Search Bar Menu
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //Sorting Bar  Menu
        MenuItem sortItem = menu.findItem(R.id.sort);
        sortItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i(TAG, "onMenuItemClick: PopupMenu is populated");
                View sort = findViewById(R.id.sort);
                PopupMenu popupMenu = new PopupMenu(MaintenanceActivity.this, sort);
                Log.i(TAG, "onMenuItemClick: Populated by sort menu items");
                popupMenu.inflate(R.menu.sort_menu);
                popupMenu.show();

                Log.i(TAG, "onMenuItemClick: on MenuItem clicked Listener");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.by_date) {
                            Collections.sort(scheduleList, Schedules.SchedulesA_ZAscending);
                            Log.i(TAG, "onMenuItemClick: sorting By Date");
                            Toast.makeText(MaintenanceActivity.this, "Sorted By date", Toast.LENGTH_LONG).show();
                        } else if (itemId == R.id.name_ascending) {
                            Collections.sort(scheduleList, Schedules.SchedulesZ_ADescending);
                            Log.i(TAG, "onMenuItemClick: sorting By Name Ascending");
                            Toast.makeText(MaintenanceActivity.this, "Ascending ", Toast.LENGTH_LONG).show();
                        } else if (itemId == R.id.name_descending) {
                            Log.i(TAG, "onMenuItemClick: sorting By Name Descending");
                            Toast.makeText(MaintenanceActivity.this, "Descending", Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }
                });
                return false;
            }
        });
        return true;
    }

    /**
     * Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
     */
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }


    @Override
    public void onClick(View v) {
        int item = v.getId();

        if (item == R.id.map_floatBar) {
            startActivity(new Intent(this, MapActivity.class));
            //TODO call toast here
        } else if (item == R.id.task_id) {
            startActivity(new Intent(this, TasksActivity.class));
        }
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
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MaintenanceActivity.this, HomeActivity.class));
        finish();
    }

}