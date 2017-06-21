package com.dickson.buzconnect.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dickson.buzconnect.models.Listing;
import com.dickson.buzconnect.util.OnListingSelectedListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dickson on 6/21/17.
 */

public class ListingListAdapter extends RecyclerView.Adapter<ListingListAdapter.ListingViewHolder>{
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    private ArrayList<Listing> mListings = new ArrayList<>();
    private Context mContext;
    private OnListingSelectedListener mOnListingSelectedListener;

    public ListingListAdapter(Context context, ArrayList<Listing> listings,  OnListingSelectedListener listingSelectedListener) {
        mContext = context;
        mListings = listings;
        mOnListingSelectedListener = listingSelectedListener;
    }
    //listadapter oncreate view
    @Override
    public ListingListAdapter.ListingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_list_item, parent, false);
        ListingViewHolder viewHolder = new ListingViewHolder(view, mListings, mOnListingSelectedListener);
        return viewHolder;
    }
    //onBindView holder to check the position of listing and binding views on it
    @Override
    public void onBindViewHolder(ListingListAdapter.ListingViewHolder  holder, int position) {
        holder.bindListing(mListings.get(position));
    }
    //get item count for the listings
    @Override
    public int getItemCount() {
        return mListings.size();
    }
    //creating a new class(ListingViewHolder) inside ListingListAdapter to bind views and
    //setting onClick listener

    /**
     *
     */
    public class ListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.listingImageView) ImageView mListingImageView;
        @Bind(R.id.listingNameTextView) TextView mNameTextView;
        @Bind(R.id.categoryTextView) TextView mCategoryTextView;
        @Bind(R.id.ratingTextView) TextView mRatingTextView;

        //member variable integer for orientation
        private int mOrientation;
        private ArrayList<Listing> mListings = new ArrayList<>();
        private Context mContext;
        private OnListingSelectedListener mListingSelectedListener;

        public ListingViewHolder(View itemView, ArrayList<Listing> listings,
                                 OnListingSelectedListener listingSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            //Determines the current orientation of the device:
            mOrientation = itemView.getResources().getConfiguration().orientation;
            // Checks if the recorded orientation matches Android's landscape configuration.
            // if so, we create a new DetailFragment to display in our special landscape layout:
            mListings = listings;
            mListingSelectedListener = listingSelectedListener;
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }

        }

}
