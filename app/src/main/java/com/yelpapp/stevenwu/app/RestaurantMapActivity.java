package com.yelpapp.stevenwu.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yelpapp.stevenwu.app.models.Business;
import com.yelpapp.stevenwu.app.models.Coordinates;
import com.yelpapp.stevenwu.app.models.SearchResponse;
import com.yelpapp.stevenwu.app.service.YelpFactory;
import com.yelpapp.stevenwu.app.service.YelpStorage;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of Restaurants. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RestaurantDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RestaurantMapActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnInfoWindowClickListener {


    private GoogleMap mMap;

    private final LatLng startPoint = new LatLng(40.7625605, -73.818851); // New York
    private HashMap<String, Marker> mMarkers = new HashMap<>();
    private boolean inClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startPoint));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14.0f));
        mMap.setOnMarkerClickListener(this);
        mMap.setInfoWindowAdapter(new ResturantInfoWindowAdapter(this));
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnCameraIdleListener(this);
        getResturant(startPoint);
    }

    private void getResturant(LatLng latLng) {
        final Activity ctx = this;


        Call<SearchResponse> call = YelpFactory.searchResturant(latLng.latitude,
                latLng.longitude);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    addMarker(response);
                }
               // mProgressDialog.hide();
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(ctx, "Some Error happen", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addMarker(Response<SearchResponse> response) {
        clearMakers();
        Business[] businesses = YelpFactory.getBussiness(response);
        YelpStorage.setBusinesses(businesses);

        for (Business business : businesses) {
            if (business != null && business.coordinates != null) {
                Coordinates coord = business.coordinates;
                LatLng latLng = new LatLng(coord.latitude, coord.longitude);
                MarkerOptions opts = new MarkerOptions()
                        .position(latLng)
                        .title(business.id);
                Marker m = mMap.addMarker(opts);
                mMarkers.put(business.id, m);
            }

        }
    }

    private void clearMakers() {
        if (!mMarkers.isEmpty()) {
            mMap.clear();
            mMarkers.clear();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        inClick = true;
        return false;
    }

    @Override
    public void onCameraIdle() {
        // call this function when map bounds changed
        if (!inClick) {
            getResturant(mMap.getCameraPosition().target);
        }
        inClick = false;
    }



    @Override
    public void onInfoWindowClick(Marker marker) {
        Bundle arguments = new Bundle();
        arguments.putString(RestaurantDetailFragment.ARG_ID, marker.getTitle());
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra(RestaurantDetailFragment.ARG_ID, marker.getTitle());
        this.startActivity(intent);
    }

}
