package com.dickson.buzconnect.util;

import com.dickson.buzconnect.services.models.Listing;

import java.util.ArrayList;

/**
 * Created by dickson on 6/19/17.
 */

public interface OnListingSelectedListener {
    /**
     *
     * @param position
     * @param listings
     * @param source - source will be the String name of the activity the user views our reusable fragment,
     *               from; Either "ListingListActivity"
     */
    public void onListingSelected(Integer position,
                                     ArrayList<Listing> listings, String source);

}
