package com.example.headstart;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * fragments are stored in this PagerAdapter class
 */
public class PagerAdapter extends FragmentPagerAdapter {

   // private final int mNumOfTabs;

    /**
     * store fragments in a List, this holds fragment
     */
    private final List<Fragment> mFragmentList = new ArrayList<Fragment>();

    public PagerAdapter(@NonNull FragmentManager fm, int behavior ){
        super(fm, behavior);
       // this.mNumOfTabs = NumOfTabs; , int NumOfTabs)
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * @param fragment , method to add fragment to List
     */
    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }
}