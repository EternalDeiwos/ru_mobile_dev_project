package com.github.eternaldeiwos.biomapapp;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.os.Bundle;

/**
 * Created by glinklater on 2016/05/30.
 */

public class LocationProvider {
    public static interface LocationCallback {
        public void onNewLocationAvailable(Location location);
    }

    public static void requestSingleUpdate(final Context context, final LocationCallback callback) {
        final LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            try {
                locationManager.requestSingleUpdate(criteria, new SingleLocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        callback.onNewLocationAvailable(location);
                    }
                }, null);
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            try {
                locationManager.requestSingleUpdate(criteria, new SingleLocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        callback.onNewLocationAvailable(location);
                    }
                }, null);
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
    }

    // Neaten up the above code by getting rid of the unused methods below
    public static abstract class SingleLocationListener implements LocationListener {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
