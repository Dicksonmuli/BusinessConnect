package com.dickson.buzconnect.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dickson.buzconnect.Constants;
import com.dickson.buzconnect.models.Listing;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by dickson on 6/20/17.
 */

public class ListingDetailFragment extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    @Bind(R.id.listingImageView) ImageView mImageLabel;
    @Bind(R.id.listingNameTextView) TextView mNameLabel;
    @Bind(R.id.categoryTextView) TextView mCategoriesLabel;
    @Bind(R.id.ratingTextView) TextView mRatingLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.openTimeTextView) TextView mOpenTimeLabel;
    @Bind(R.id.openStateTextView) TextView mOpenStateLabel;
    @Bind(R.id.savelistingButton) Button mSaveListingButton;
    @Bind(R.id.viewImageButton) Button mViewImageButton;

    //  listing object
    private Listing mRestaurant;
    private ArrayList<Listing> mRestaurants;
    private int mPosition;
    private String mSource;
    private static final int REQUEST_IMAGE_CAPTURE = 111;

    public static ListingDetailFragment newInstance(ArrayList<Listing> restaurants,
                                                       Integer position, String source) {
        //wrapping listings with parcels for serialization
        ListingDetailFragment restaurantDetailFragment = new ListingDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_KEY_LISTINGS, Parcels.wrap(restaurants));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        unwrapping listing on onCreate
        mRestaurants = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_LISTINGS));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mRestaurant = mRestaurants.get(mPosition);
//        invoke the menu items
        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
    }


}
