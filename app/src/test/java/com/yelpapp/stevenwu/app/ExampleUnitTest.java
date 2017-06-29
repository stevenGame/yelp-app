package com.yelpapp.stevenwu.app;

import android.util.Log;

import com.yelpapp.stevenwu.app.models.Business;
import com.yelpapp.stevenwu.app.models.ReviewResponse;
import com.yelpapp.stevenwu.app.models.SearchResponse;
import com.yelpapp.stevenwu.app.service.YelpFactory;
import com.yelpapp.stevenwu.app.service.YelpStorage;

import org.junit.Assert;
import org.junit.Test;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void yelpServiceTest() throws Exception {
        Call<SearchResponse> call;
        call = YelpFactory.searchResturant(40.7133937,-73.8293711);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    Business[] result = YelpFactory.getBussiness(response);
                    YelpStorage.setBusinesses(result);
                    Business b = YelpStorage.getBussinesById("corvina-peruvian-kitchen-queens-2");
                    assertNotNull(b);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

                Log.d("Error", t.getMessage());
            }
        });
        call.wait();
    }

    @Test
    public  void reviewTest() throws  Exception{
        Call<ReviewResponse> reivews = YelpFactory.reviews("corvina-peruvian-kitchen-queens-2");
        ReviewResponse review =  reivews.execute().body();

        assertNotNull(review);
    }
}