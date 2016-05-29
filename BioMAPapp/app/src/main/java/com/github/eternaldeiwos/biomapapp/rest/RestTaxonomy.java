package com.github.eternaldeiwos.biomapapp.rest;

import com.github.eternaldeiwos.biomapapp.helper.API;
import com.github.eternaldeiwos.biomapapp.helper.TaxonomyTypeAdapter;
import com.github.eternaldeiwos.biomapapp.model.Project;
import com.github.eternaldeiwos.biomapapp.model.Taxonomy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by glinklater on 2016/05/29.
 */

public class RestTaxonomy {
    private static IRestTaxonomy taxonomyService;

    public interface IRestTaxonomy {
        @GET("api/v1/taxa")
        Call<Map<String, Taxonomy>> getProjects(@Query("project") String project_db_name);
    }

    private static IRestTaxonomy getInstance() {
        if (taxonomyService == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Type t = new TypeToken<Map<String, Taxonomy>>() {}.getType();
            gsonBuilder.registerTypeAdapter(t, new TaxonomyTypeAdapter());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API.ADU_VMUS_URL)
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .build();

            taxonomyService = retrofit.create(IRestTaxonomy.class);
        }
        return taxonomyService;
    }

    public static void getTaxonomy(Project project, Callback<Map<String, Taxonomy>> callback) {
        Call<Map<String, Taxonomy>> call = getInstance().getProjects(project.toString());
        call.enqueue(callback);
    }
}
