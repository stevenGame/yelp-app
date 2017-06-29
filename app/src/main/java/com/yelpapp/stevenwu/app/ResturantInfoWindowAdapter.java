package com.yelpapp.stevenwu.app;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.yelpapp.stevenwu.app.models.Business;
import com.yelpapp.stevenwu.app.service.YelpFactory;
import com.yelpapp.stevenwu.app.service.YelpStorage;

/**
 * Created by Steven on 6/28/2017.
 */

public class ResturantInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    // These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
    // "title" and "snippet".
    private final View mWindow;


    public ResturantInfoWindowAdapter(Activity context) {
        mWindow = context.getLayoutInflater().inflate(R.layout.custom_info_window, null);

    }
    @Override
    public View getInfoWindow(Marker marker) {
        render(marker, mWindow);
        return mWindow;
    }
    private void render(Marker marker, View view) {
        String Id =  marker.getTitle();

        Business business = YelpStorage.getBussinesById(Id);
        if(business != null) {
            TextView titleUi = ((TextView) view.findViewById(R.id.name));
            titleUi.setText(business.name);
            TextView address = ((TextView) view.findViewById(R.id.address));
            if (business.location != null) {
                address.setText(business.location.getFormatAddress());
            }
            TextView ratingText = ((TextView) view.findViewById(R.id.rating));
            ratingText.setText(Float.toString(business.rating));
        }

    }
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
