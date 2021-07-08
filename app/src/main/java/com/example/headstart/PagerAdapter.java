package com.example.headstart;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * fragments are stored in this PagerAdapter class
 */
public class PagerAdapter extends FragmentStateAdapter {

    /**
     * store fragments in a List, this holds fragment
     */
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    /**
     * @param fragment , method to add fragment to List
     */
    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

}