package com.developers.t_free;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Amanjeet Singh on 16-Jul-16.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new Home();
            case 1:
                return new Payment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
