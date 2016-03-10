package com.kfive.hopebible.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kfive.hopebible.helpers.HomeSwipeHelper;

/**
 * Created by apple on 10/28/14.
 */
public class HomeSwipeAdapter extends FragmentStatePagerAdapter {

    //    lets add our helper
    HomeSwipeHelper homeSwipeHelper;

    public HomeSwipeAdapter(FragmentManager fm) {

        super(fm);
        homeSwipeHelper = new HomeSwipeHelper();
    }

    @Override
    public Fragment getItem(int i) {
        return   homeSwipeHelper.getFragment(i);
    }

    @Override
    public int getCount() {

        return homeSwipeHelper.fragmentCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
