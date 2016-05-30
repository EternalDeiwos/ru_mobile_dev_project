package com.github.eternaldeiwos.biomapapp.rest;

import com.github.eternaldeiwos.biomapapp.helper.API;
import com.github.eternaldeiwos.biomapapp.helper.LocationTypeAdapter;
import com.github.eternaldeiwos.biomapapp.model.Location;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by glinklater on 2016/05/30.
 */

public class RestReverseGeocode {
    private static IRestReverseGeocode geocodeService;

    public interface IRestReverseGeocode {
        @GET("json")
        Call<Location> reverseGeocode(
                @Query("latlng") String latlng,
                @Query("key") String api_key
        );
    }

    private static IRestReverseGeocode getInstance() {
        if (geocodeService == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Location.class, new LocationTypeAdapter());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API.GOOGLE_MAPS_URL)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();

            geocodeService = retrofit.create(IRestReverseGeocode.class);
        }
        return geocodeService;
    }

    public static Location getLocation(float lat, float lng) {
        String latlng = String.format(Locale.US, "%.6f,%.6f", lat, lng);
        Call<Location> call = getInstance().reverseGeocode(latlng, API.GOOGLE_API_KEY);
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getLocation(float lat, float lng, Callback<Location> callback) {
        String latlng = String.format(Locale.US, "%.6f,%.6f", lat, lng);
        Call<Location> call = getInstance().reverseGeocode(latlng, API.GOOGLE_API_KEY);
        call.enqueue(callback);
    }
}
