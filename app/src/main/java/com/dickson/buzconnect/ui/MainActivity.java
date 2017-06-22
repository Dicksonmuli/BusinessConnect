package com.dickson.buzconnect.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dickson.buzconnect.Constants;
import com.dickson.buzconnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //member variables
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    CarouselView carouselView;
    private TextView mTextMessage;

    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};

    //binding views
    @Bind(R.id.itemSearchEditText) EditText mItemSearchEditText;
    @Bind(R.id.findListingButton) Button mFindListingButton;
    @Bind(R.id.locationEditText) EditText mLocationEditText;
//    @Bind(R.id.navigation_saved) Button mSavedListingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //setting onClick listeners in main activity
        mFindListingButton.setOnClickListener(this);
//        mSavedListingButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName());
                }else {

                }
            }
        };


    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
    //overriding onstart
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    //overriding onStop
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    //overriding onClick method
    @Override
    public void onClick(View v) {
        if(v == mFindListingButton) {
                String location = mLocationEditText.getText().toString();
                String term = mItemSearchEditText.getText().toString();
//                   saveLocationtoFirebase(location, term);
                   if(!(location).equals("")) {
                       addToSharedPreferences(location);
                   }
            Intent intent = new Intent(MainActivity.this, ListingListActivity.class);
            intent.putExtra("location", location);
            intent.putExtra("term", term);
            startActivity(intent);

        }
//        if (v == mSavedListingButton) {
//            Intent intent = new Intent(MainActivity.this, SavedListingListActivity.class);
//            startActivity(intent);
//        }
    }
    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
        mEditor.putString(Constants.PREFERENCES_TERM_KEY, location).apply();
    }
    //inflate overflow menu in the main activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //    action for item selected in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        if (id == R.id.navigation_saved) {
            Intent intent = new Intent(MainActivity.this, SavedListingListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //logout method
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        //returning to login activity after the user logs out
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent2 = new Intent(MainActivity.this, ListingListActivity.class);
                    startActivity(intent2);
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_saved:
                    Intent intent3 = new Intent(MainActivity.this, SavedListingListActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }

    };
}
