package com.github.eternaldeiwos.biomapapp.helper;

import com.github.eternaldeiwos.biomapapp.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by glinklater on 2016/05/28.
 */

public class UserTypeAdapter extends TypeAdapter<User> {
    @Override
    public void write(JsonWriter out, User value) throws IOException {
        // don't need
    }

    @Override
    public User read(JsonReader in) throws IOException {
        Gson gson = new Gson();
        JsonObject o = gson.fromJson(in, JsonObject.class);
        o = o.getAsJsonObject("registered");
        JsonObject status = o.getAsJsonObject("status");
        JsonObject data = o.getAsJsonObject("data");

        return new User(
                status.get("result").getAsString().equals("success"),
                status.get("token").getAsString(),
                data.get("Name").getAsString(),
                data.get("Surname").getAsString(),
                data.get("Email").getAsString(),
                data.get("ADUNumber").getAsString()
        );
    }
}
