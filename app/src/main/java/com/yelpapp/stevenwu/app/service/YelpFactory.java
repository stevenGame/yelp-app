package com.yelpapp.stevenwu.app.service;

import com.yelpapp.stevenwu.app.models.Business;
import com.yelpapp.stevenwu.app.models.Review;
import com.yelpapp.stevenwu.app.models.ReviewResponse;
import com.yelpapp.stevenwu.app.models.SearchResponse;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Steven on 6/29/2017.
 */

public class YelpFactory {
    public static YelpService service;

    private YelpFactory() {
        // disable new key word for factory class
    }

    public static YelpService getInstence() {
        if (service == null) {
            service = YelpService.retrofit.create(YelpService.class);
        }
        return service;
    }

    public static Call<SearchResponse> searchResturant(double lat, double lng){
        Call<SearchResponse> call = getInstence().businessSearch("restaurants", lat, lng);
        return call;
    }
    public static Business[] getBussiness(Response<SearchResponse> response) {
        if (response != null && response.body() != null) {
            return response.body().businesses;
        }
        return null;
    }

    public static Call<ReviewResponse> reviews(String id) {
        return getInstence().reviews(id);
    }
    public  static Review[] getReviews(Response<ReviewResponse> response) {
        if(response != null && response.body() != null) {
            return response.body().reviews;
        }
        return null;
    }
}
