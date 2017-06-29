package com.yelpapp.stevenwu.app.models;

public class Location {
    public String city;
    public String country;
    public String address2;
    public String address3;
    public String state;
    public String address1;
    public String zip_code;

    public String getFormatAddress() {
        return String.format("%s, %s", address1, city);
    }
}
