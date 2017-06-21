package com.dickson.buzconnect.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.dickson.buzconnect.Constants;
import com.dickson.buzconnect.R;
import com.dickson.buzconnect.models.Listing;
import com.dickson.buzconnect.ui.ListingDetailActivity;
import com.dickson.buzconnect.ui.ListingDetailFragment;
import com.dickson.buzconnect.util.ItemTouchHelperAdapter;
import com.dickson.buzconnect.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dickson on 6/21/17.
 */

public class FirebaseListingListAdapter extends FirebaseRecyclerAdapter<Listing, FirebaseListingViewHolder>
 implements ItemTouchHelperAdapter{
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    //member variable for orientation
    private int mOrientation;

    private ChildEventListener mChildEventListener;
    private ArrayList<Listing> mListings = new ArrayList<>();

    /**
     * constructor with the folling parameters
     * @param modelClass
     * @param modelLayout
     * @param viewHolderClass
     * @param ref
     * @param onStartDragListener
     * @param context
     */
    public FirebaseListingListAdapter(Class<Listing> modelClass, int modelLayout,
                                         Class<FirebaseListingViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();//returns the DatabaseReference
        mOnStartDragListener = onStartDragListener;
        mContext = context;

//        adding listings with ChildEventListener
        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mListings.add(dataSnapshot.getValue(Listing.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void populateViewHolder(final FirebaseListingViewHolder viewHolder, Listing model, int position) {
        viewHolder.bindListing(model);
        //setting the orientation
        mOrientation = viewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }

        //setting ontouch listener on mListingImageView
        viewHolder.mListingImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }

        });
//        adding onclick listener
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = viewHolder.getAdapterPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    createDetailFragment(itemPosition);
                }else {
                    Intent intent = new Intent(mContext, ListingDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                    intent.putExtra(Constants.EXTRA_KEY_LISTINGS, Parcels.wrap(mListings));
                    //added to include source
                    intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_SAVED);
                    mContext.startActivity(intent);
                }
            }
        });
    }
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //updates the order of mListings ArrayList items passing in the list
        Collections.swap(mListings, fromPosition, toPosition);
        //notifies the adapter that the underlying data has been changed
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }
    @Override
    public void onItemDismiss(int position) {
        //removes the item from mRestaurants at the given position
        mListings.remove(position);
        //getRef() returns the DatabaseReference and .removeValue() deletes the object from firebase
        getRef(position).removeValue();

    }
    //re-assigning the 'index' property for each listing object in our array list and saving it to Firebase:
    private void setIndexInFirebase() {
        for (Listing listing : mListings) {
            int index = mListings.indexOf(listing);
            DatabaseReference ref = getRef(index);
//            replacing setValue to index
            ref.child("index").setValue(Integer.toString(index));

//            listing.setIndex(Integer.toString(index));
//            ref.setValue(restaurant);

        }

    }
    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }
    private void createDetailFragment(int position) {
        // Creates new ListingDetailFragment with the given position:
        ListingDetailFragment detailFragment = ListingDetailFragment.newInstance(mListings,
                position, Constants.SOURCE_SAVED);
        // Gathers necessary components to replace the FrameLayout in the layout with the RestaurantDetailFragment:
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        //  Replaces the FrameLayout with the RestaurantDetailFragment:
        ft.replace(R.id.restaurantDetailContainer, detailFragment);
        // Commits these changes:
        ft.commit();
    }

}
