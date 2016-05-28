package com.github.eternaldeiwos.biomapapp.rest;

import com.github.eternaldeiwos.biomapapp.helper.API;
import com.github.eternaldeiwos.biomapapp.helper.ProjectTypeAdapter;
import com.github.eternaldeiwos.biomapapp.model.Project;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by glinklater on 2016/05/28.
 */

public class RestProject {
    private static IRestProject projectService;

    public interface IRestProject {
        @GET("api/v1/projects")
        Call<Map<String, Project>> getProjects();
    }

    private static IRestProject getInstance() {
        if (projectService == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Type t = new TypeToken<Map<String, Project>>() {}.getType();
            gsonBuilder.registerTypeAdapter(t, new ProjectTypeAdapter());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API.ADU_VMUS_URL)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .build();

            projectService = retrofit.create(IRestProject.class);
        }
        return projectService;
    }

    public static void getProjects(Callback<Map<String, Project>> callback) {
        Call<Map<String, Project>> call = getInstance().getProjects();
        call.enqueue(callback);
    }
}
