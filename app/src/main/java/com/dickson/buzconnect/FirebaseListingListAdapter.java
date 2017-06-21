package com.dickson.buzconnect;

import android.content.Context;

import com.dickson.buzconnect.models.Listing;
import com.dickson.buzconnect.util.ItemTouchHelperAdapter;
import com.dickson.buzconnect.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

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
}
