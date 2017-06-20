package com.dickson.buzconnect.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.dickson.buzconnect.R;
import com.dickson.buzconnect.models.Listing;
import com.dickson.buzconnect.util.OnListingSelectedListener;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by dickson on 6/20/17.
 */

public class ListingListFragment extends Fragment {
    //member variables
    private ListingListAdapter mAdapter;
    public ArrayList<Listing> mListings = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;
    private OnListingSelectedListener mOnRestaurantSelectedListener;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    public ListingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();
        //instructs fragment to include menu options
        setHasOptionsMenu(true);
    }
}
