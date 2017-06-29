package com.yelpapp.stevenwu.app.service;

import com.yelpapp.stevenwu.app.models.Business;

/**
 * Created by Steven on 6/29/2017.
 */

public class YelpStorage {
    private static Business[] mBusinesses;

    public static void setBusinesses(Business[] businesses) {
        mBusinesses = businesses;
    }

    public static Business[] getBusinesses() {
        return mBusinesses;
    }

    public static Business getBussinesById(String id) {
        if (mBusinesses != null) {
            for (Business b: mBusinesses) {
                if (id.equals(b.id)) {
                    return b;
                }
            }
        }
        return null;
    }
}
