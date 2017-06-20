package com.dickson.buzconnect;

/**
 * Created by dickson on 6/19/17.
 */

public class Constants {

    public static final String YP_API_KEY = BuildConfig.YP_API_KEY;
    public static final String YP_BASE_URL = "http://api2.yp.com/listings/v1/search?searchloc=";
    public static final String YP_LOCATION_QUERY_PARAMETER = "location";
    public static final String YP_TERM_QUERY_PARAMETER = "term";
    public static final String YP_FORMAT_PARAMETER = "&format=json&key=";
    //        a constant to save to shared preferences
    public static final String PREFERENCES_LOCATION_KEY = "location";

    public static final String FIREBASE_CHILD_SEARCHED_LOCATION = "searchedLocation";
    public static final String FIREBASE_CHILD_BUSINESSES = "businesses";
    //        index string constant to reference the 'index' key of Restaurant objects
    public static final String FIREBASE_QUERY_INDEX = "index";

    public static final String EXTRA_KEY_POSITION = "position";
    public static final String EXTRA_KEY_LISTINGS = "restaurants";
    // navigation constants
    public static final String KEY_SOURCE = "source";
    public static final String SOURCE_SAVED ="saved";
    public static final String SOURCE_FIND = "find";
}
