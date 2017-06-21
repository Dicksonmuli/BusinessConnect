package com.dickson.buzconnect.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dickson.buzconnect.models.Listing;
import com.dickson.buzconnect.util.OnListingSelectedListener;

import java.util.ArrayList;

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
        holder.bindListing(mListings.get(position))
    }
    //get item count for the listings
}
