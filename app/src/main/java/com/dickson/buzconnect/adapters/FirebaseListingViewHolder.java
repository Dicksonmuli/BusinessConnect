package com.dickson.buzconnect.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView openStateTextView = (TextView) mView.findViewById(R.id.ratingTextView);


        //checking which image to retrieve from Firebase
        if (!restaurant.getImageUrl().contains("http")) {
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(restaurant.getImageUrl());
                mRestaurantImageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // This block of code should already exist, we're just moving it to the 'else' statement:
            Picasso.with(mContext)
                    .load(restaurant.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mRestaurantImageView);
//            nameTextView.setText(restaurant.getName());
//            categoryTextView.setText(restaurant.getCategories().get(0));
//            ratingTextView.setText("Rating: " + restaurant.getRating() + "/5");
        }
        nameTextView.setText(restaurant.getName());
        categoryTextView.setText(restaurant.getCategories().get(0));
        ratingTextView.setText("Rating: " + restaurant.getRating() + "/5");
    }
}
