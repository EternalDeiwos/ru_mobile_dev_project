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
        @POST("insertrecord")
        Call<ResponseBody> upload(
                @Part("images[]") MultipartBody.Part image1,
                @Part("images[]") MultipartBody.Part image2,
                @Part("images[]") MultipartBody.Part image3,
                @Part("sound") MultipartBody.Part sound,
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
                @Part("recordstatus") RequestBody recordstatus // 1) blank, 2) naturally occuring,
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
        HashMap<String, RequestBody> fields = new HashMap<>();

        // put images into multipart parts
        for (int i = 0; i < r.images.length; i++) {
            parts.add(getPartFromFileUri(context, "images[]", r.images[i]));
        }

        // put sound into multipart parts
        parts.add(getPartFromFileUri(context, "sound", r.sound));

        // and the rest of the mundane data into RequestBody objects
        for (Field f : Record.class.getFields()) {
            String field_name = f.getName();
            String val;
            switch (field_name) {
                case "images":
                case "sound":
                    break; // Handled above => ignore here
                case "roadkill":
                    int roadkill = 0;
                    try {
                        roadkill = f.getBoolean(r)
                                ? 1
                                : 0;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } finally {
                        fields.put(field_name, getRequestBody(context, roadkill + ""));
                    }
                    break;
                case "minelev":
                case "maxelev":
                case "year":
                case "month":
                case "day":
                case "nestcount":
                case "accuracy":
                    val = null;
                    try {
                        val = f.getInt(r) + "";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } finally {
                        fields.put(field_name, getRequestBody(context, val));
                    }
                    break;
                case "lat":
                case "lng":
                    val = null;
                    try {
                        val = String.format(java.util.Locale.US, "%.5f", f.getFloat(r));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } finally {
                        if (field_name.equals("lng")) field_name = "long";
                        fields.put(field_name, getRequestBody(context, val));
                    }
                    break;
                case "recordstatus":
                    val = null;
                    try {
                        val = f.get(r).toString();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } finally {
                        fields.put(field_name, getRequestBody(context, val));
                    }
                    break;
                default:
                    val = null;
                    try {
                        val = (String) f.get(r);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } finally {
                        fields.put(field_name, getRequestBody(context, val));
                    }
                    break;
            }
        }

        return uploadService.upload(
                parts.get(0),
                parts.get(1),
                parts.get(2),
                parts.get(3),
                fields.get("username"),
                fields.get("userid"),
                fields.get("email"),
                fields.get("project"),
                fields.get("observers"),
                fields.get("country"),
                fields.get("province"),
                fields.get("nearesttown"),
                fields.get("locality"),
                fields.get("minelev"),
                fields.get("maxelev"),
                fields.get("lat"),
                fields.get("long"),
                fields.get("accuracy"),
                fields.get("source"),
                fields.get("year"),
                fields.get("month"),
                fields.get("day"),
                fields.get("note"),
                fields.get("userdet"),
                fields.get("nest_count"),
                fields.get("nest_site"),
                fields.get("roadkill"),
                fields.get("taxonid"),
                fields.get("taxonname"),
                fields.get("institution_code"),
                fields.get("collection"),
                fields.get("recordbasis"),
                fields.get("recordstatus")
        );
    }

    private static MultipartBody.Part getPartFromFileUri(Context context, String key, Uri fileUri) {
        if (fileUri == null) return null;
        File file = FileUtils.getFile(context, fileUri);
        RequestBody request = RequestBody.create(formdatatype, file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), request);
        return part;
    }

    private static RequestBody getRequestBody(Context context, String value) {
        if (value == null) return null;
        RequestBody body = RequestBody.create(formdatatype, value);
        return body;
    }
}

