package com.dickson.buzconnect.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dickson.buzconnect.Constants;
import com.dickson.buzconnect.R;
import com.dickson.buzconnect.models.Listing;
import com.dickson.buzconnect.util.OnListingSelectedListener;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by dickson on 6/20/17.
 */

public class ListingListActivity extends AppCompatActivity implements OnListingSelectedListener {
    //member variables
    private Integer mPosition;
    ArrayList<Listing> mListings;
    String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);

        if (savedInstanceState != null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mListings = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_LISTINGS));
                mSource = savedInstanceState.getString(Constants.KEY_SOURCE);

                if (mPosition != null && mListings != null) {
                    Intent intent = new Intent(this, ListingDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_LISTINGS, Parcels.wrap(mListings));
                    intent.putExtra(Constants.KEY_SOURCE, mSource);
                    startActivity(intent);
                }

            }
        }

    }
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
}
