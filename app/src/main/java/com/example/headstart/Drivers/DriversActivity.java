package com.example.headstart.Drivers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.headstart.Home.HomeActivity;
import com.example.headstart.Map.MapActivity;
import com.example.headstart.R;
import com.example.headstart.Settings.SettingsActivity;
import com.example.headstart.Trucks.TrucksActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DriversActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton addDriverFloatBtn, cancelFloatBtn,mapFloatBtn;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    private EditText editFirstName, editLastName, editPhoneNumber,
            editEmail, editDriverID, editVehicleID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        addDriverFloatBtn = findViewById(R.id.addDriver_fab);
        addDriverFloatBtn.setOnClickListener(this);

        mapFloatBtn = findViewById(R.id.map_floatBar);
        mapFloatBtn.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
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
        int itemId = v.getId();

        if (itemId == R.id.addDriver_fab) {
//            startActivity(new Intent(DriversActivity.this, AddDriverActivity.class));
            addDriverDialog();
        }
        if (itemId == R.id.map_floatBar) {
            startActivity(new Intent(DriversActivity.this, MapActivity.class));
        }

    }

    /**
     * Add driver info popUp function
     */
    private void addDriverDialog() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        final View addDriverDialog = getLayoutInflater().inflate(R.layout.activity_add_driver, null);


        //Text boxes
        editFirstName = addDriverDialog.findViewById(R.id.driverFirstName);
        editLastName = addDriverDialog.findViewById(R.id.driverLastName);
        editPhoneNumber = addDriverDialog.findViewById(R.id.driverPhoneNumber);
        editEmail = addDriverDialog.findViewById(R.id.driverEmailAddress);
        editDriverID = addDriverDialog.findViewById(R.id.driver_Id);
        editVehicleID = addDriverDialog.findViewById(R.id.vehicle_Id);

        //
        alertDialogBuilder.setView(addDriverDialog);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        cancelFloatBtn = addDriverDialog.findViewById(R.id.cancel_float_Btn);
        cancelFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

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
        } else if (itemId == R.id.nav_trucks) {
            //truck activity
            startActivity(new Intent(this, TrucksActivity.class));
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