package com.example.headstart.Home;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.headstart.Drivers.DriversActivity;
import com.example.headstart.Map.MapActivity;
import com.example.headstart.R;
import com.example.headstart.Settings.SettingsActivity;
import com.example.headstart.Trucks.TrucksActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //method for swapping between fragments(tracking, home, notifications)
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            //home activity
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
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, 3);

        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        //An array containing icons from the drawable directory
        final int [] ICONS = new int[]{
                R.drawable.ic_truck_icon,
                R.drawable.ic_notify,
        };


        tabLayout.getTabAt(0).setIcon(ICONS[0]).setText("Track");;
        tabLayout.getTabAt(1).setText("HeadStart");
        tabLayout.getTabAt(2).setIcon(ICONS[1]);


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

    }
}