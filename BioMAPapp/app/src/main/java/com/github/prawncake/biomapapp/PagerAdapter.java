package com.github.prawncake.biomapapp;

/**
 * Created by g11f0364 on 2016-05-29.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.github.prawncake.biomapapp.CreateRecordFragments.LocationFragment;
import com.github.prawncake.biomapapp.CreateRecordFragments.MediaFragment;
import com.github.prawncake.biomapapp.CreateRecordFragments.SpecimenFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LocationFragment tab1 = new LocationFragment();
                return tab1;
            case 1:
                SpecimenFragment tab2 = new SpecimenFragment();
                return tab2;
            case 2:
                MediaFragment tab3 = new MediaFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}