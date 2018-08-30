package com.chd.chd56lc.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyInvestFragmentAdapter extends FragmentPagerAdapter {
    String[] tab_array;
    Fragment[] fragments;

    public MyInvestFragmentAdapter(FragmentManager fm, String[] tab_array, Fragment[] fragments) {
        super(fm);
        this.tab_array = tab_array;
        this.fragments = fragments;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return fragments.length;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_array[position];
    }
}
