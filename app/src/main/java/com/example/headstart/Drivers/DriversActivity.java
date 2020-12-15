package com.example.headstart.Drivers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.headstart.Home.HomeActivity;
import com.example.headstart.Map.MapActivity;
import com.example.headstart.PagerAdapter;
import com.example.headstart.R;
import com.example.headstart.Settings.SettingsActivity;
import com.example.headstart.Trucks.TrucksActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class DriversActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

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
            //map activity
            startActivity(new Intent(this, MapActivity.class));
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
     * this adds tabs to home activity, responsible for swapping btw fragments
     */
    private void setupViewPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        //add Fragments (home, tracking, notification) to PagerAdapter
        pagerAdapter.addFragment(new DriverFragment());
        pagerAdapter.addFragment(new AddDriverFragment());

        //pass fragment to viewPager container
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);

        //Tabs Link to viewPager
        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

//        //An array containing icons from the drawable directory
//        final int[] ICONS = new int[]{
//                R.drawable.ic_truck,
//                R.drawable.ic_notify,
//        };

        tabLayout.getTabAt(0).setText("My Drivers");
        tabLayout.getTabAt(1).setText("Add Drivers");
    }

    /**
     * menu Item select focus function
     */
    private void updateNavigationBarState() {
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
    }


}