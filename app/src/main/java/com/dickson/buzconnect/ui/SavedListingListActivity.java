package com.dickson.buzconnect.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.dickson.buzconnect.R;

/**
 * Created by dickson on 6/20/17.
 */

public class SavedListingListActivity extends AppCompatActivity {
    private TextView mTextMessage;
    //refactoring this activity to a fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saved_listing_list);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    Intent intent = new Intent(SavedListingListActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent2 = new Intent(SavedListingListActivity.this, ListingListActivity.class);
                    startActivity(intent2);
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_saved:
                    Intent intent3 = new Intent(SavedListingListActivity.this, SavedListingListActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }

    };
}