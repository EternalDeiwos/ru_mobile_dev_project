package com.github.eternaldeiwos.biomapapp.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.github.eternaldeiwos.biomapapp.helper.API;
import com.github.eternaldeiwos.biomapapp.helper.PermissionTypeAdapter;
import com.github.eternaldeiwos.biomapapp.helper.UserTypeAdapter;
import com.github.eternaldeiwos.biomapapp.model.Permission;
import com.github.eternaldeiwos.biomapapp.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;

/**
 * Created by glinklater on 2016/05/27.
 */
public class RestUser {
    private static IRestUser userService;

    public interface IRestUser {

        @GET("validation/user/login")
        Call<User> getUser(@Query("API_KEY") String api_key,
                     @Query("userid") String adu_number,
                     @Query("email") String email_addr,
                     @Query("passid") String md5_pass
        );

        @GET("validation/data/access")
        Call<Permission> validateUserPrivileges(@Query("token") String token);
    }

    private static IRestUser getInstance() {
        if (userService == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(User.class, new UserTypeAdapter());
            gsonBuilder.registerTypeAdapter(Permission.class, new PermissionTypeAdapter());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API.ADU_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .build();

            userService = retrofit.create(IRestUser.class);
        }
        return userService;
    }

    public static void getUser(
            String adu_number,
            String email,
            String enc_password,
            Callback<User> callback
    ) {
        Call<User> call = getInstance().getUser(API.API_KEY, adu_number, email, enc_password);
        call.enqueue(callback);
    }

    public static User getUser(
            String adu_number,
            String email,
            String enc_password
    ) {
        Call<User> call = getInstance().getUser(API.API_KEY, adu_number, email, enc_password);
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getPrivileges(
            String token,
            Callback<Permission> callback
    ) {
        Call<Permission> call = getInstance().validateUserPrivileges(token);
        call.enqueue(callback);
    }
}
