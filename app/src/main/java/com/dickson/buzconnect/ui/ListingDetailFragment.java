package com.dickson.buzconnect.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dickson.buzconnect.Constants;
import com.dickson.buzconnect.R;
import com.dickson.buzconnect.models.Listing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    @Bind(R.id.couponUrlTexView) TextView mCouponUrlLabel;

    //  listing object
    private Listing mListing;
    private ArrayList<Listing> mListings;
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
        mListings = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_LISTINGS));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mListing = mListings.get(mPosition);
//        invoke the menu items
        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
    }
    //    listing object used to set our ImageView and TextViews in onCreateView on onCreate view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating the layout view for this fragment
        View view = inflater.inflate(R.layout.fragment_listing_detail, container, false);
        ButterKnife.bind(this, view);


        if (!mListing.getImageUrl().contains("http")) {
            try {
                Bitmap image = decodeFromFirebaseBase64(mListing.getImageUrl());
                mImageLabel.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //  moved it to the 'else' statement:
            Picasso.with(view.getContext())
                    .load(mListing.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mImageLabel);
        }

        mNameLabel.setText(mListing.getName());
        mCategoriesLabel.setText( mListing.getCategory());
        mRatingLabel.setText(Double.toString(mListing.getRating()) );
        mPhoneLabel.setText(mListing.getPhone());
        mAddressLabel.setText( mListing.getAddress());
        mOpenTimeLabel.setText( mListing.getOpenHours());
        mOpenStateLabel.setText( mListing.getOpenStatus());
        mCouponUrlLabel.setText( mListing.getOpenStatus());

        mWebsiteLabel.setOnClickListener( this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener( this);
        mCouponUrlLabel.setOnClickListener(this);
        mViewImageButton.setOnClickListener( this);

        //setting click listener to saveListingButton button
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mSaveListingButton.setVisibility(View.GONE);
        } else {
            mSaveListingButton.setOnClickListener(this);
        }


        return view;
    }
    /**
     *  take the encoded image's string,
     *  and use the built-in firebase utility to decode it back into a byte array
     * @param image
     * @return
     * @throws IOException
     */
    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    @Override
    public void onClick(View v) {
        //    implicit intent
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mListing.getWebsite()));
            startActivity(webIntent);
        }
        if (v == mCouponUrlLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mListing.getWebsite()));
            startActivity(webIntent);
        }
        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mListing.getPhone()));
            startActivity(phoneIntent);
        }
        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mListing.getLatitude()
                            + "," + mListing.getLongitude()
                            + "?q=(" + mListing.getName() + ")"));
            startActivity(mapIntent);
        }
        //explicit intent
        if (v == mSaveListingButton) {
            //getting the current user by user id when saveRest button is clicked
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_BUSINESSES)
                    .child(uid);
            /** add the pushID of the Listing to be saved before setting the
             * value at given reference
             */
            DatabaseReference pushRef = restaurantRef.push();
            String pushId = pushRef.getKey();
            mListing.setPushId(pushId);
            pushRef.setValue(mListing);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
    //inflating photo menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            inflater.inflate(R.menu.menu_photo, menu);
        } else {
            inflater.inflate(R.menu.menu_main, menu);
        }
    }
    //overriding item selected method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
                onLaunchCamera();
            default:
                break;
        }
        return false;
    }
    //launches the camera
    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * overriding onActivityResult triggered by startActivityForResult(),
     * in order to snag our picture
     * @param requestCode - represents the REQUEST_IMAGE_CAPTURE value in onLaunchCamera
     * @param resultCode -represents the status of the activity
     * @param data - is an Intent object that includes intent extras
     *             containing the information being returned
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageLabel.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    /**
     * encoding Bitmap image and saving to the firebase
     * creating ByteArrayOutputStream
     * @param bitmap
     */
    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        compressing the image and determining the quality (100)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//        locate the node containing the current image URL for this specific restaurant on this
//        specific user's saved restaurants list, and overwrite it with our new, encoded image.
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_BUSINESSES)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mListing.getPushId())
                .child("imageUrl");
        ref.setValue(imageEncoded);
    }




}
