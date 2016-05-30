package com.github.eternaldeiwos.biomapapp;

import android.content.Intent;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.prawncake.biomapapp.BaseActivity;
import com.github.prawncake.biomapapp.CreateRecordActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class SelectLocationActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker mMarker;
    private MarkerOptions mMarkerOptions;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        button = (FloatingActionButton) findViewById(R.id.mapOkButton);
        button.setFocusable(false);
        button.setFocusableInTouchMode(false);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LocationProvider.requestSingleUpdate(this, new LocationProvider.LocationCallback() {
            @Override
            public void onNewLocationAvailable(Location location) {
                LatLng my_location = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(my_location));
                mMarkerOptions = new MarkerOptions()
                        .position(my_location)
                        .title("My Location");
                mMarker = mMap.addMarker(mMarkerOptions);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishWithResult();
                    }
                });
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMarker.remove();
                mMarkerOptions = new MarkerOptions()
                        .title("New Position")
                        .position(latLng);
                mMarker = mMap.addMarker(mMarkerOptions);
            }
        });

    }

    private void finishWithResult() {
        Bundle response = new Bundle();
        response.putFloat(CreateRecordActivity.KEY_LAT, (float) mMarker.getPosition().latitude);
        response.putFloat(CreateRecordActivity.KEY_LNG, (float) mMarker.getPosition().longitude);
        Intent intent = new Intent();
        intent.putExtras(response);
        setResult(RESULT_OK, intent);
        finish();
    }
}
