package com.example.headstart.Home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * fragments are stored in this PagerAdapter class
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private final int mNumOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int NumOfTabs) {
        super(fm, behavior);
        this.mNumOfTabs = NumOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TrackingFragment();
            case 1:
                return new HomeFragment();
            case 2:
                return new NotificationFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}