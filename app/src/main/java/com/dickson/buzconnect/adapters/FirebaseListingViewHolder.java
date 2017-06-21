package com.dickson.buzconnect.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by dickson on 6/21/17.
 */

public class FirebaseListingViewHolder {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    //declaring imageView public so grant access to adapter to enable drag & drop
    public ImageView mListingImageView;

    View mView;
    Context mContext;
}
