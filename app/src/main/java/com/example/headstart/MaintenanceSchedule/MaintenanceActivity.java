package com.example.headstart.MaintenanceSchedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

import java.util.ArrayList;

public class MaintenanceActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private TextView taskTextViewBTN;
    private RecyclerView recyclerView;
    private ArrayList<Schedules> scheduleList;

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

        taskTextViewBTN = findViewById(R.id.task_id);
        taskTextViewBTN.setOnClickListener(this);


        scheduleList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(MaintenanceActivity.this, scheduleList);
        recyclerView.setAdapter(scheduleAdapter);
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