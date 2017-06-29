package com.yelpapp.stevenwu.app.models;

import android.support.annotation.NonNull;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

/**
 * Created by Steven on 6/29/2017.
 */

public class Business {
    public float rating;
    public String price;
    public String phone;
    public String id;
    public boolean is_closed;
    public Category[] categories;
    public int review_count;
    public String name;
    public String url;
    public Coordinates coordinates;
    public String image_url;
    public Location location;
    public float distance;
    public String[] transactions;

    public String getCategoryDescription() {
        String desciption = "";
        ArrayList<String> titles = new ArrayList<String>();
        if (categories != null) {
            for (Category c : categories) {
                titles.add(c.title);
            }
          desciption =  StringUtils.join(titles, ",");
        }

        return desciption;
    }
}

