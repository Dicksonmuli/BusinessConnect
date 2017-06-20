package com.dickson.buzconnect.services;

import com.dickson.buzconnect.Constants;
import com.dickson.buzconnect.models.Listing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dickson on 6/19/17.
 */

public class YpService {
    private static OkHttpClient client = new OkHttpClient();
    public static void findListings(String location, String term, Callback callback) {


//        using HttpUrl class to construct the URL we'll send our request to
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.YP_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString() + "&term=" + Constants.YP_TERM_QUERY_PARAMETER
                + Constants.YP_FORMAT_PARAMETER + Constants.YP_API_KEY;

//       create request using the created url
        Request request= new Request.Builder()
                .url(url)
                .build();
//        execute this request
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    //        parse JSON data and return a list of restaurants
    public ArrayList<Listing> processResults(Response response) {
        ArrayList<Listing> listings = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject ypJSON = new JSONObject(jsonData);
                JSONArray businessesJSON = ypJSON.getJSONObject("searchResults").getJSONObject("searchListings").
                        getJSONArray("searchListing");
                for (int i = 0; i < businessesJSON.length(); i++) {
                    JSONObject listingJSON = businessesJSON.getJSONObject(i);
                    String name = listingJSON.getString("businessName");
                    String city = listingJSON.getString("city");
                    String category = listingJSON.getString("categories");
                    String phone = listingJSON.optString("phone", "Phone not available");
                    String website = listingJSON.getString("url");
                    double rating = listingJSON.getDouble("averageRating");
                    String imageUrl = listingJSON.optString("adImage");
                    double latitude = listingJSON.getDouble("latitude");
                    double longitude = listingJSON.getDouble("longitude");
                    String couponUrl = listingJSON.optString("couponURL");
                    String address = listingJSON.getString("street");
                    String openHours = listingJSON.getString("openHours");
                    String openStatus = listingJSON.getString("openStatus");

                    Listing listing = new Listing(name, city, category, phone, website, rating,
                            imageUrl, latitude, longitude, couponUrl, address, openHours, openStatus);
                    listings.add(listing);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listings;
    }
}
