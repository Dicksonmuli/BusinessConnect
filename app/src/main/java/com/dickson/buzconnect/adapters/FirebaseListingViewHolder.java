package com.dickson.buzconnect.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dickson.buzconnect.R;
import com.dickson.buzconnect.models.Listing;
import com.dickson.buzconnect.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by dickson on 6/21/17.
 */

public class FirebaseListingViewHolder extends RecyclerView.ViewHolder
    implements ItemTouchHelperViewHolder{
    //member variables
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    //declaring imageView public so grant access to adapter to enable drag & drop
    public ImageView mListingImageView;

    View mView;
    Context mContext;

    public FirebaseListingViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
    }
    //binding Listings
    public void bindListing(Listing listing) {
        mListingImageView = (ImageView) mView.findViewById(R.id.listingImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.listingNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);
        TextView openStateTextView = (TextView) mView.findViewById(R.id.openStateTextView);


        //checking which image to retrieve from Firebase
        if (!listing.getImageUrl().contains("http")) {
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(listing.getImageUrl());
                mListingImageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Take the photo from API if no captured photo found
            Picasso.with(mContext)
                    .load(listing.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mListingImageView);

        }
        nameTextView.setText(listing.getName());
        categoryTextView.setText(listing.getCategory());
        ratingTextView.setText("Rating: " + listing.getRating());
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
    public void onItemSelected() {
        //programmatic approach of animation
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);

    }

    @Override
    public void onItemClear() {
//        programmatic approach of animation
       itemView.animate()
               .alpha(1f)
               .scaleX(1f)
               .scaleY(1f);

    }
}
