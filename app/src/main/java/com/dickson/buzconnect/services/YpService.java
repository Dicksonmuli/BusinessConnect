package com.dickson.buzconnect.services;

import com.dickson.buzconnect.Constants;

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
    public static void findBusinessespublic(String location, Callback callback) {
/

//        using HttpUrl class to construct the URL we'll send our request to
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YP_BASE_URL).newBuilder();
//        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

//       create request using the created url
        Request request= new Request.Builder()
                .url(url)
                .build();
//        execute this request
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    //        parse JSON data and return a list of restaurants
    public ArrayList<Restaurant> processResults(Response response) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject yelpJSON = new JSONObject(jsonData);
                JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
                for (int i = 0; i < businessesJSON.length(); i++) {
                    JSONObject restaurantJSON = businessesJSON.getJSONObject(i);
                    String name = restaurantJSON.getString("name");
                    String phone = restaurantJSON.optString("display_phone", "Phone not available");
                    String website = restaurantJSON.getString("url");
                    double rating = restaurantJSON.getDouble("rating");
                    String imageUrl = restaurantJSON.getString("image_url");
                    double latitude = restaurantJSON
                            .getJSONObject("coordinates").getDouble("latitude");
                    double longitude = restaurantJSON
                            .getJSONObject("coordinates").getDouble("longitude");
                    ArrayList<String> address = new ArrayList<>();
                    JSONArray addressJSON = restaurantJSON.getJSONObject("location")
                            .getJSONArray("display_address");
                    for (int y = 0; y < addressJSON.length(); y++) {
                        address.add(addressJSON.toString());
                    }

                    ArrayList<String> categories = new ArrayList<>();
                    JSONArray categoriesJSON = restaurantJSON.getJSONArray("categories");

                    for (int y = 0; y < categoriesJSON.length(); y++) {
                        categories.add(categoriesJSON.getJSONObject(y).getString("title").toString());
                    }
                    Restaurant restaurant = new Restaurant(name, phone, website, rating,
                            imageUrl, latitude, longitude, address, categories);
                    restaurants.add(restaurant);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}
