package com.dickson.buzconnect.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dickson.buzconnect.Constants;
import com.dickson.buzconnect.R;
import com.dickson.buzconnect.adapters.ListingListAdapter;
import com.dickson.buzconnect.models.Listing;
import com.dickson.buzconnect.services.YpService;
import com.dickson.buzconnect.util.OnListingSelectedListener;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private String mRecentTerm;
    private OnListingSelectedListener mOnRestaurantSelectedListener;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    public static final String TAG = ListingListFragment.class.getSimpleName();

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listing_list, container, false);
        ButterKnife.bind(this, view);

        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
        mRecentTerm = mSharedPreferences.getString(Constants.PREFERENCES_TERM_KEY, null);

//        if (mRecentAddress != null && mRecentTerm != null) {
            getListings(mRecentAddress, mRecentTerm);
//        }

        // Inflate the layout for this fragment
        return view;
    }
    @Override
    // Method is now void, menu inflater is now passed in as argument:
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Call super to inherit method from parent:
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
//                addToSharedPreferences(query);
//                getListings(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    //adding to sharedPreferences
    private void addToSharedPreferences(String location, String term) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
        mEditor.putString(Constants.PREFERENCES_TERM_KEY, term).apply();
    }

    /**
     * in order to allow fragments and activities to communicate
     * we need to capture an instance of our interface
     * and cast it into the context of the activities we need to communicate with
     * @param context
     */
    //onAttach lifecyle
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnRestaurantSelectedListener = (OnListingSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }
    public void getListings(String location, String term) {
        final YpService yelpService = new YpService();

        yelpService.findListings(location, term, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mListings = yelpService.processResults(response);

                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    // Line above states 'getActivity()' instead of previous 'RestaurantListActivity.this'
                    // because fragments do not have own context, and must inherit from corresponding activity.

                    @Override
                    public void run() {
                        mAdapter = new ListingListAdapter(getActivity(), mListings, mOnRestaurantSelectedListener);
                        // Line above states `getActivity()` instead of previous
                        // 'getApplicationContext()' because fragments do not have own context,
                        // must instead inherit it from corresponding activity.

                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        // Line above states 'new LinearLayoutManager(getActivity());' instead of previous
                        // 'new LinearLayoutManager(RestaurantListActivity.this);' when method resided
                        // in RestaurantListActivity because Fragments do not have context
                        // and must instead inherit from corresponding activity.

                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}
