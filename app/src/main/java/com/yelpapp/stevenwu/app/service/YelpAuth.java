package com.yelpapp.stevenwu.app.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Steven on 6/28/2017.
 */

public class YelpAuth {
    static OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                
                Request request = original.newBuilder()
                        .header("Authorization",
                                "Bearer rl0ypeuzhTwErGl3YSu7_vinE2iNXXKT8Yn-wX8d-wWEjonhf6IOCpaGNyKyYiKKb9cfQnlbbQcDMhxPXixlbcDRxuR3bX5Oy764W4hHRB6b4mtUbaq8B1JQ-RZUWXYx")

                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }
}
