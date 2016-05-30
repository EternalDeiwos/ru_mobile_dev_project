package com.github.eternaldeiwos.biomapapp.helper;

import com.github.eternaldeiwos.biomapapp.model.AddressComponent;
import com.github.eternaldeiwos.biomapapp.model.Location;
import com.github.eternaldeiwos.biomapapp.model.LocationEntry;
import com.github.eternaldeiwos.biomapapp.model.LocationType;
import com.github.eternaldeiwos.biomapapp.model.ResponseType;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by glinklater on 2016/05/30.
 */

/* Example API Response
{
   "results" : [
      {
         "address_components" : [
            {
               "long_name" : "Unnamed Road",
               "short_name" : "Unnamed Road",
               "types" : [ "route" ]
            },
            {
               "long_name" : "Western District",
               "short_name" : "Western District",
               "types" : [ "administrative_area_level_2", "political" ]
            },
            {
               "long_name" : "Eastern Cape",
               "short_name" : "EC",
               "types" : [ "administrative_area_level_1", "political" ]
            },
            {
               "long_name" : "South Africa",
               "short_name" : "ZA",
               "types" : [ "country", "political" ]
            }
         ],
         "formatted_address" : "Unnamed Road, South Africa",
         "geometry" : {
            "bounds" : {
               "northeast" : {
                  "lat" : -33.4846467,
                  "lng" : 26.6564026
               },
               "southwest" : {
                  "lat" : -33.488707,
                  "lng" : 26.650749
               }
            },
            "location" : {
               "lat" : -33.4865467,
               "lng" : 26.6532814
            },
            "location_type" : "GEOMETRIC_CENTER",
            "viewport" : {
               "northeast" : {
                  "lat" : -33.4846467,
                  "lng" : 26.6564026
               },
               "southwest" : {
                  "lat" : -33.488707,
                  "lng" : 26.650749
               }
            }
         },
         "place_id" : "ChIJJy-2XmJOZB4RImymRO7FhN4",
         "types" : [ "route" ]
      },
      ...
   },
   status: "OK"
}
 */

public class LocationTypeAdapter extends TypeAdapter<Location> {
    @Override
    public void write(JsonWriter out, Location value) throws IOException {
        // not needed
    }

    @Override
    public Location read(JsonReader in) throws IOException {
        Gson gson = new Gson();
        JsonObject o = gson.fromJson(in, JsonObject.class);
        List<LocationEntry> entries = new LinkedList<>();

        Location location = new Location();

        if ((location.status = ResponseType.getEnum(o.get("status").getAsString()))
                .equals(ResponseType.OK)) {
            JsonArray a = o.getAsJsonArray("results");
            location.entries = parseLocationEntries(a);
        }
        return location;
    }

    private List<AddressComponent> parseAddressComponents(JsonArray a) {
        List<AddressComponent> list = new LinkedList<>();

        for (JsonElement e : a) {
            List<LocationType> types = new LinkedList<>();
            JsonObject o = e.getAsJsonObject();

            JsonArray typesJson = o.getAsJsonArray("types");
            for (JsonElement te : typesJson) {
                types.add(LocationType.getEnum(te.getAsString()));
            }

            list.add(new AddressComponent(
                    o.get("long_name").getAsString(),
                    o.get("short_name").getAsString(),
                    types
            ));
        }
        return list;
    }

    private List<LocationEntry> parseLocationEntries(JsonArray a) {
        List<LocationEntry> list = new LinkedList<>();

        Iterator<JsonElement> iterator = a.iterator();
        for (JsonElement e = iterator.next(); iterator.hasNext(); e = iterator.next()) {
            JsonObject o = e.getAsJsonObject();
            JsonArray typesJson = o.getAsJsonArray("types");
            List<LocationType> types = new ArrayList<>(typesJson.size());
            for (JsonElement te : typesJson) {
                types.add(LocationType.getEnum(te.getAsString()));
            }

            list.add(new LocationEntry(
                    o.get("formatted_address").getAsString(),
                    types,
                    parseAddressComponents(o.getAsJsonArray("address_components"))
            ));
        }
        return list;
    }
}
