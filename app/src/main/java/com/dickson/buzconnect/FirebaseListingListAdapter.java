package com.dickson.buzconnect;

import android.content.Context;

import com.dickson.buzconnect.models.Listing;
import com.dickson.buzconnect.util.ItemTouchHelperAdapter;
import com.dickson.buzconnect.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

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
}
