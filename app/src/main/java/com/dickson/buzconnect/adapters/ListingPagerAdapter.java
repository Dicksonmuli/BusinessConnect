package com.dickson.buzconnect.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dickson.buzconnect.models.Listing;
import com.dickson.buzconnect.ui.ListingDetailFragment;

import java.util.ArrayList;

/**
 * Created by dickson on 6/21/17.
 */

public class ListingPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Listing> mListings;
    private String mSource;

    public ListingPagerAdapter(FragmentManager fm, ArrayList<Listing> listings, String source) {
        super(fm);
        mListings = listings;
        mSource = source;
    }

    //overriding getItem
    @Override
    public Fragment getItem(int position) {
        return ListingDetailFragment.newInstance(mListings, position, mSource);
    }
    @Override
    public int getCount() {
        return mListings.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mListings.get(position).getName();
    }
}
