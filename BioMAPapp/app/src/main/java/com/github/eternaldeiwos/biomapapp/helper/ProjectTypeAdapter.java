package com.github.eternaldeiwos.biomapapp.helper;

import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;

import com.github.eternaldeiwos.biomapapp.model.Project;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by glinklater on 2016/05/28.
 */

public class ProjectTypeAdapter extends TypeAdapter<Map<String, Project>> {

    @Override
    public void write(JsonWriter out, Map<String, Project> value) throws IOException {
        // not needed
    }

    @Override
    public Map<String, Project> read(JsonReader in) throws IOException {
        HashMap<String, Project> map = new HashMap<>();

        Gson gson = new Gson();
        JsonObject o = gson.fromJson(in, JsonObject.class);
        JsonArray projects = o.getAsJsonArray("projects");

        Iterator<JsonElement> iterator = projects.iterator();
        for (JsonObject i = iterator.next().getAsJsonObject(); iterator.hasNext(); i = iterator.next().getAsJsonObject()) {
            Project project = new Project(
                    i.get("Project_acronym").getAsString(),
                    i.get("Database_name").getAsString(),
                    i.get("Description").getAsString(),
                    i.get("Date_started").getAsString()
            );
            map.put(project.getDb_name(), project);
        }

        return map;
    }
}
