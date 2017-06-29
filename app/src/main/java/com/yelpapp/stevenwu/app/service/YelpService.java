package com.yelpapp.stevenwu.app.service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.yelpapp.stevenwu.app.models.ReviewResponse;
import com.yelpapp.stevenwu.app.models.SearchResponse;

/**
 * Created by Steven on 6/28/2017.
 */

public interface YelpService {
    @GET("businesses/search")
    Call<SearchResponse> businessSearch(@Query("term") String term,
                                        @Query("latitude") double lat,
                                        @Query("longitude") double lng);
    @GET("businesses/{id}/reviews")
    Call<ReviewResponse> reviews(@Path("id") String Id);
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.yelp.com/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(YelpAuth.getHttpClient())
            .build();
}
