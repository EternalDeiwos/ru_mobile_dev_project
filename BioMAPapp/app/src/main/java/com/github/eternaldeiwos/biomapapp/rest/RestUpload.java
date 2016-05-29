package com.github.eternaldeiwos.biomapapp.rest;

import com.github.eternaldeiwos.biomapapp.helper.API;
import com.github.eternaldeiwos.biomapapp.model.Record;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by glinklater on 2016/05/29.
 */

public class RestUpload {
    private static IRestUpload uploadService;

    public interface IRestUpload {
        @Multipart
        @POST("insertrecord")
        Call<ResponseBody> upload(
                @Part("images[]") RequestBody image,
                @Part("sound") RequestBody sound,
                @Part("username") String username,
                @Part("userid") String adu_number,
                @Part("email") String email,
                @Part("project") String project_name,
                @Part("observers") String observers,
                @Part("country") String country,
                @Part("province") String province,
                @Part("nearesttown") String town,
                @Part("locality") String locality,
                @Part("minelev") int min_elevation,
                @Part("maxelev") int max_elevation,
                @Part("lat") float lat,
                @Part("long") float lng,
                @Part("accuracy") int accuracy,
                @Part("source") String gps_source,
                @Part("year") int year,
                @Part("month") int month,
                @Part("day") int day,
                @Part("note") String observer_note,
                @Part("userdet") String user_identification,
                @Part("nest_count") int nest_count,
                @Part("nest_site") String nest_site,
                @Part("roadkill") int roadkill_bool,
                @Part("taxonid") String taxonid,
                @Part("taxonname") String scientific_name,
                @Part("institution_code") String institution,
                @Part("collection") String collection_code,
                @Part("recordbasis") String basis_of_record, // SIGHTING or VMUS:CAMERA TRAP or VMUS
                @Part("recordstatus") String status // 1) blank, 2) naturally occuring,
                // 3) re-introduced, 4) introduced, 5) feral, 6) cultivated, 7) exotic
        );
    }

    private static IRestUpload getInstance() {
        if (uploadService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API.ADU_VMUS_URL)
                    .build();

            uploadService = retrofit.create(RestUpload.IRestUpload.class);
        }
        return uploadService;
    }

    public static void uploadRecord(Record record, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = buildRequest(record, getInstance());
        call.enqueue(callback);
    }

    public static boolean uploadRecord(Record record) {
        try {
            Response response = buildRequest(record, getInstance()).execute().raw();
            System.err.println(response.body().string());
            return response.code() == 200;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }

    private static Call<ResponseBody> buildRequest(Record record, IRestUpload uploadService) {
        // TODO
//        return uploadService.upload(
//
//        );
        return null;
    }
}
