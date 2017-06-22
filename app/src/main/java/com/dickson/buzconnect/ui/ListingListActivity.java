package com.dickson.buzconnect.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dickson.buzconnect.Constants;
import com.dickson.buzconnect.R;
import com.dickson.buzconnect.adapters.ListingListAdapter;
import com.dickson.buzconnect.models.Listing;
import com.dickson.buzconnect.services.YpService;
import com.dickson.buzconnect.util.OnListingSelectedListener;

import org.parceler.Parcels;

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

public class ListingListActivity extends AppCompatActivity implements OnListingSelectedListener {
    //member variables
    private Integer mPosition;
    ArrayList<Listing> mListings = new ArrayList<>();
    String mSource;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private ListingListAdapter mAdapter;

    private TextView mTextMessage;

    public static final String TAG = ListingListActivity.class.getSimpleName();
    private OnListingSelectedListener mOnRestaurantSelectedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);
        ButterKnife.bind(this);
        Intent intent1 = getIntent();
        String location = intent1.getStringExtra("location");
        String term = intent1.getStringExtra("term");
        getListings(location, term);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        if (savedInstanceState != null) {
//            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
//                mListings = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_LISTINGS));
//                mSource = savedInstanceState.getString(Constants.KEY_SOURCE);
//
//                if (mPosition != null && mListings != null) {
//                    Intent intent = new Intent(this, ListingDetailActivity.class);
//
//                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
//                    intent.putExtra(Constants.EXTRA_KEY_LISTINGS, Parcels.wrap(mListings));
//                    intent.putExtra(Constants.KEY_SOURCE, mSource);
//                    intent.putExtra("location", location);
//                    intent.putExtra("term", term);
//                    startActivity(intent);
//                }
//
//            }
//        }

    }
    //bottom navigation intents
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(ListingListActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent2 = new Intent(ListingListActivity.this, ListingListActivity.class);
                    startActivity(intent2);
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_saved:
                    Intent intent3 = new Intent(ListingListActivity.this, SavedListingListActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }

    };
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mPosition !=null && mListings != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_LISTINGS, Parcels.wrap(mListings));
            outState.putString(Constants.KEY_SOURCE, mSource);
        }
    }
    //overriding interface - item selected
    @Override
    public void onListingSelected(Integer position, ArrayList<Listing> listings,String source) {
        mPosition = position;
        mListings = listings;
        mSource = source;
    }
    public void getListings(String location, String term) {
        final YpService ypService = new YpService();

        ypService.findListings(location, term, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mListings = ypService.processResults(response);
                ListingListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //running the list of output
                        mAdapter = new ListingListAdapter(getApplicationContext(), mListings, mOnRestaurantSelectedListener);
                       try {
                           mRecyclerView.setAdapter(mAdapter);
                           RecyclerView.LayoutManager layoutManager =
                                   new LinearLayoutManager(ListingListActivity.this);
                           mRecyclerView.setLayoutManager(layoutManager);
                           mRecyclerView.setHasFixedSize(true);
                       }catch (NullPointerException e) {
                           e.printStackTrace();
                       }


                        String[] restaurantNames = new String[mListings.size()];
                        for (int i = 0; i < restaurantNames.length; i++) {
                            restaurantNames[i] = mListings.get(i).getName();
                        }

//                        ArrayAdapter adapter = new ArrayAdapter(ListingListActivity.this,
//                                android.R.layout.simple_list_item_1, restaurantNames);
//                        mListView.setAdapter(adapter);

                        for (Listing restaurant : mListings) {
                            Log.d(TAG, "Name: " + restaurant.getName());
                            Log.d(TAG, "Phone: " + restaurant.getPhone());
                            Log.d(TAG, "Website: " + restaurant.getWebsite());
                            Log.d(TAG, "Image url: " + restaurant.getImageUrl());
                            Log.d(TAG, "Rating: " + Double.toString(restaurant.getRating()));
                            Log.d(TAG, "Address: " + restaurant.getAddress());
                            Log.d(TAG, "Categories: " + restaurant.getCategory().toString());
                        }
                    }
                });
//                mAdapter = new RestaurantListAdapter(getApplicationContext(), mRestaurants);
//                mRecyclerView.setAdapter(mAdapter);
//                RecyclerView.LayoutManager layoutManager =
//                        new LinearLayoutManager(RestaurantsActivity.this);
//                mRecyclerView.setLayoutManager(layoutManager);
//                mRecyclerView.setHasFixedSize(true);

//                getActivity().runOnUiThread(new Runnable() {
//                    // Line above states 'getActivity()' instead of previous 'RestaurantListActivity.this'
//                    // because fragments do not have own context, and must inherit from corresponding activity.
//
//                    @Override
//                    public void run() {
//                        mAdapter = new ListingListAdapter(getActivity(), mListings, mOnRestaurantSelectedListener);
//                        // Line above states `getActivity()` instead of previous
//                        // 'getApplicationContext()' because fragments do not have own context,
//                        // must instead inherit it from corresponding activity.
//
//                        mRecyclerView.setAdapter(mAdapter);
//                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//                        // Line above states 'new LinearLayoutManager(getActivity());' instead of previous
//                        // 'new LinearLayoutManager(RestaurantListActivity.this);' when method resided
//                        // in RestaurantListActivity because Fragments do not have context
//                        // and must instead inherit from corresponding activity.
//
//                        mRecyclerView.setLayoutManager(layoutManager);
//                        mRecyclerView.setHasFixedSize(true);
//                    }
//                });
            }
        });
    }
}
