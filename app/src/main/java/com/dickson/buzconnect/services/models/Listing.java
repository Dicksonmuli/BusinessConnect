package com.dickson.buzconnect.services.models;

import org.parceler.Parcel;

/**
 * Created by dickson on 6/19/17.
 */

@Parcel
public class Listing {
    String name ;
    String city;
    String category;
    String phone;
    String website;
    double rating;
    String imageUrl;
    double latitude;
    double longitude;
    String couponUrl;
    String address;
    String openHours;
    String openStatus;
    //firebase push id
    private String pushId;
    String index;

    //empty constructor for Parceler
    public Listing() {}

    public Listing(String name, String city, String category, String phone, String website,
                   double rating, String imageUrl, double latitude, double longitude,
                   String couponUrl, String address, String openHours, String openStatus) {
        this.name =name;
        this.city = city;
        this.category = category;
        this.phone = phone;
        this.website = website;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.couponUrl = couponUrl;
        this.address = address;
        this.openHours = openHours;
        this.openStatus = openStatus;
    }

    public String getName() {
        return name;
    }
    public String getCity() {
        return city;
    }
    public String getCategory() {
        return category;
    }
    public String getPhone() {
        return phone;
    }
    public String getWebsite() {
        return website;
    }
    public double getRating() {
        return rating;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitute() {
        return longitude;
    }
    public String getCouponUrl() {
        return couponUrl;
    }
    public String getAddress() {
        return address;
    }
    public String getOpenHours() {
        return openHours;
    }
    public String getOpenStatus() {
        return openStatus;
    }

}
