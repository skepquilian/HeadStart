package com.example.headstart.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.headstart.Drivers.DriversActivity;
import com.example.headstart.Map.MapActivity;
import com.example.headstart.PagerAdapter;
import com.example.headstart.R;
import com.example.headstart.Settings.SettingsActivity;
import com.example.headstart.MaintenanceSchedule.MaintenanceActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        FloatingActionButton floatingActionButton = findViewById(R.id.map_floatBar);
        floatingActionButton.setOnClickListener(this);


        //To hide actionBar
        getSupportActionBar().hide();

        //responsible for swapping between fragments(tracking, home, notifications)
        setupViewPager();
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

        //This sends user to map activity anytime user clicks middle float bar
        if (itemId == R.id.map_floatBar){
            startActivity(new Intent(HomeActivity.this, MapActivity.class));
            Toast.makeText(this, "Real time Location", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            //home activity
            return true;
        } else if (itemId == R.id.nav_trucks) {
            //truck activity
            startActivity(new Intent(this, MaintenanceActivity.class));
            return true;

        } else if (itemId == R.id.nav_map) {
            //truck activity
            return true;
        }else if (itemId == R.id.nav_drivers) {
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
     * this adds tabs to home activity, responsible for swapping btw fragments
     */
    private void setupViewPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        //add Fragments (home, tracking, notification) to PagerAdapter
        pagerAdapter.addFragment(new TrackingDetail());
        pagerAdapter.addFragment(new HomeFragment());
        pagerAdapter.addFragment(new NotificationFragment());

        //pass fragment to viewPager container
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);

        //Tabs Link to viewPager
        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        //An array containing icons from the drawable directory
        final int[] ICONS = new int[]{
                R.drawable.ic_action_truck,
                R.string.app_name,
                R.drawable.ic_notifications,
        };


        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setText(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);


        //set the home fragment to be current page when activity is loaded
        viewPager.setCurrentItem(1);
    }

    /**
     * menu Item select focus function
     */
    private void updateNavigationBarState() {
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}