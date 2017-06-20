package com.dickson.buzconnect.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dickson.buzconnect.R;

/**
 * Created by dickson on 6/20/17.
 */

public class SavedListingListActivity extends AppCompatActivity {

    //refactoring this activity to a fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saved_listing_list);
    }
}