package com.github.eternaldeiwos.biomapapp.helper;

import com.github.eternaldeiwos.biomapapp.model.Taxonomy;
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

/**
 * Created by glinklater on 2016/05/29.
 */

public class TaxonomyTypeAdapter extends TypeAdapter<Map<String, Taxonomy>> {
    @Override
    public void write(JsonWriter out, Map<String, Taxonomy> value) throws IOException {
        // not used
    }

    @Override
    public Map<String, Taxonomy> read(JsonReader in) throws IOException {
        HashMap<String, Taxonomy> map = new HashMap<>();

        Gson gson = new Gson();
        JsonObject o = gson.fromJson(in, JsonObject.class);
        JsonArray a = o.getAsJsonArray();

        Iterator<JsonElement> iterator = a.iterator();
        for (JsonObject i = iterator.next().getAsJsonObject(); iterator.hasNext(); i = iterator.next().getAsJsonObject()) {
            Taxonomy taxonomy = new Taxonomy(
                    i.get("taxon_id").getAsString(),
                    i.get("taxon_name").getAsString(),
                    i.get("rank").getAsString(),
                    i.get("common_name").getAsString()
            );
            map.put(taxonomy.toString(), taxonomy);
        }

        return map;
    }
}
