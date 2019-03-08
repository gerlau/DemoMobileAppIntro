package com.example.android.documentlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class PagerAdapter extends SmartFragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /*
    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(position + 1);
    }
    */

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                return FragmentA.newInstance(R.drawable.ic_calendar, "ONLINE RESERVATION ", "Fast & reliable reservation");
            case 1:
                return FragmentA.newInstance(R.drawable.ic_taxi, "TRANSPORT", "Safe and reliable rides are provided by our experienced and pet friendly drivers");
            case 2:
                return FragmentA.newInstance(R.drawable.ic_dog, "COMFORT", "Comfortable environment for your dog’s needs and wants");
            default:
                return null;
        }
    }
}
