package com.dickson.buzconnect.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dickson.buzconnect.Constants;
import com.dickson.buzconnect.R;
import com.dickson.buzconnect.adapters.ListingPagerAdapter;
import com.dickson.buzconnect.models.Listing;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dickson on 6/20/17.
 */

public class ListingDetailActivity extends AppCompatActivity{
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private ListingPagerAdapter adapterViewPager;
    ArrayList<Listing> mRestaurants = new ArrayList<>();
    private String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_detail);
        ButterKnife.bind(this);

        mSource = getIntent().getStringExtra(Constants.KEY_SOURCE);
//        pull out our ArrayList<Restaurant> Parcelable using the unwrap() method
        mRestaurants = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_LISTINGS));
        int startingPosition =getIntent().getIntExtra(Constants.EXTRA_KEY_POSITION, 0);

//        instructing ViewPager to use adapterViewPager adapter.
//        And set the current item to the position of the item that was just clicked on
        adapterViewPager = new ListingPagerAdapter(getSupportFragmentManager(), mRestaurants, mSource);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
