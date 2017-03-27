package com.kfive.hopebible.helpers;

import android.support.v4.app.Fragment;

import com.kfive.hopebible.fragments.HBHomeAppShelve;
import com.kfive.hopebible.fragments.HbHomeLanding;

import java.util.ArrayList;

/**
 * Created by apple on 10/28/14.
 */
public class HomeSwipeHelper {


    public ArrayList FragmentList = new ArrayList<Fragment>();

    public HomeSwipeHelper() {
        //We add our fragments here
        HbHomeLanding homeLanding = new HbHomeLanding();
        HBHomeAppShelve nextView = new HBHomeAppShelve();

        FragmentList.add(homeLanding);
        FragmentList.add(nextView);
    }


    //OK..so the code is intresting :)..for every fragment we add to an arraylist
    //so when the position is requested the fragment is returned..ho hustle
    public Fragment getFragment(int position){


        //we cast to fragment because the arraylist definition above was generic
        return  (Fragment)FragmentList.get(position);

    }
    //here we get the number of fragments available in the fragment list
    public int fragmentCount(){
        return FragmentList.size();
    }


}
