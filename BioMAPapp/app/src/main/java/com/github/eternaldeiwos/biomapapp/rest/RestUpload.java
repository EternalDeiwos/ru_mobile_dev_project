package com.github.eternaldeiwos.biomapapp.rest;

import android.content.Context;
import android.net.Uri;

import com.github.eternaldeiwos.biomapapp.helper.API;
import com.github.eternaldeiwos.biomapapp.model.Record;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private static MediaType formdatatype;

    public interface IRestUpload {
        @Multipart
        @POST("api/v1/insertrecord")
        Call<ResponseBody> upload(
                @Part MultipartBody.Part image1,
                @Part MultipartBody.Part image2,
                @Part MultipartBody.Part image3,
                @Part MultipartBody.Part sound,
                @Part("username") RequestBody username,
                @Part("userid") RequestBody userid,
                @Part("email") RequestBody email,
                @Part("project") RequestBody project,
                @Part("observers") RequestBody observers,
                @Part("country") RequestBody country,
                @Part("province") RequestBody province,
                @Part("nearesttown") RequestBody nearesttown,
                @Part("locality") RequestBody locality,
                @Part("minelev") RequestBody minelev,
                @Part("maxelev") RequestBody maxelev,
                @Part("lat") RequestBody lat,
                @Part("long") RequestBody lng,
                @Part("accuracy") RequestBody accuracy,
                @Part("source") RequestBody source,
                @Part("year") RequestBody year,
                @Part("month") RequestBody month,
                @Part("day") RequestBody day,
                @Part("note") RequestBody note, // observer note
                @Part("userdet") RequestBody userdet, // user identification
                @Part("nest_count") RequestBody nestcount,
                @Part("nest_site") RequestBody nestsite,
                @Part("roadkill") RequestBody roadkill_bool,
                @Part("taxonid") RequestBody taxonid,
                @Part("taxonname") RequestBody taxonname,
                @Part("institution_code") RequestBody institution_code,
                @Part("collection") RequestBody collection_code,
                @Part("recordbasis") RequestBody recordbasis, // SIGHTING or VMUS:CAMERA TRAP or VMUS
                @Part("recordstatus") RequestBody recordstatus, // 1) blank, 2) naturally occuring,
                // 3) re-introduced, 4) introduced, 5) feral, 6) cultivated, 7) exotic
                @Part("token") RequestBody token,
                @Part("API_KEY") RequestBody api_key
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

    public static void uploadRecord(Context context, Record record, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = buildRequest(context, record, getInstance());
        call.enqueue(callback);
    }

    public static boolean uploadRecord(Context context, Record record) {
        try {
            Response response = buildRequest(context, record, getInstance()).execute().raw();
            System.err.println(response.body().string());
            return response.code() == 200;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }

    private static Call<ResponseBody> buildRequest(
            Context context,
            Record r,
            IRestUpload uploadService
    ) {
        formdatatype = MediaType.parse("multipart/form-data");
        LinkedList<MultipartBody.Part> parts = new LinkedList<>();

        // put images into multipart parts
        for (int i = 0; i < 3; i++) {
            parts.add(r.images.length > i ? getPartFromFileUri(context, "images[]", r.images[i]) : MultipartBody.Part.createFormData("images[]", ""));
        }

        // put sound into multipart parts
        parts.add(r.sound != null ? getPartFromFileUri(context, "sound", r.sound)
                : MultipartBody.Part.createFormData("sound", ""));

        System.err.println("ERMAHGERD!!! " + r.token);
        r.debug();

        // and the rest of the mundane data into RequestBody objects
        return uploadService.upload(
                parts.get(0),
                parts.get(1),
                parts.get(2),
                parts.get(3),
                getRequestBody(context, r.username),
                getRequestBody(context, r.userid),
                getRequestBody(context, r.email),
                getRequestBody(context, r.project),
                getRequestBody(context, r.observers),
                getRequestBody(context, r.country),
                getRequestBody(context, r.province),
                getRequestBody(context, r.nearesttown),
                getRequestBody(context, r.locality),
                getRequestBody(context, r.minelev + ""),
                getRequestBody(context, r.maxelev + ""),
                getRequestBody(context, r.lat + ""),
                getRequestBody(context, r.lng + ""),
                getRequestBody(context, r.accuracy + ""),
                getRequestBody(context, r.source + ""),
                getRequestBody(context, r.year + ""),
                getRequestBody(context, r.month + ""),
                getRequestBody(context, r.day + ""),
                getRequestBody(context, r.note + ""),
                getRequestBody(context, r.userdet + ""),
                getRequestBody(context, r.nestcount + ""),
                getRequestBody(context, r.nestsite + ""),
                getRequestBody(context, r.roadkill ? 1 + "" : 0 + ""),
                getRequestBody(context, r.taxonid + ""),
                getRequestBody(context, r.taxonname + ""),
                getRequestBody(context, r.institution_code + ""),
                getRequestBody(context, r.collection_code + ""),
                getRequestBody(context, r.recordbasis + ""),
                getRequestBody(context, r.recordstatus.getValue()),
                getRequestBody(context, r.token),
                getRequestBody(context, API.API_KEY)
        );
    }

    private static MultipartBody.Part getPartFromFileUri(Context context, String key, Uri fileUri)
    {
        if (fileUri == null) return null;
        File file = FileUtils.getFile(context, fileUri);
        RequestBody request = RequestBody.create(formdatatype, file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), request);
        return part;
    }

    private static RequestBody getRequestBody(Context context, String value)
    {
        if (value == null) return RequestBody.create(formdatatype, "");
        RequestBody body = RequestBody.create(formdatatype, value);
        return body;
    }
}

