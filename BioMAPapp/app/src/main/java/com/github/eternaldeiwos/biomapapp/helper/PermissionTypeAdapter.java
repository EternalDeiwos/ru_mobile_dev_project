package com.github.eternaldeiwos.biomapapp.helper;

import com.github.eternaldeiwos.biomapapp.model.Permission;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by glinklater on 2016/05/28.
 */

public class PermissionTypeAdapter extends TypeAdapter<Permission> {
    @Override
    public void write(JsonWriter out, Permission value) throws IOException {
        // don't need
    }

    @Override
    public Permission read(JsonReader in) throws IOException {
        Gson gson = new Gson();
        JsonObject o = gson.fromJson(in, JsonObject.class);
        o = o.getAsJsonObject("permissions");

        JsonObject app_permission = o.getAsJsonObject("app_permission");

        return new Permission(
                app_permission.get("app_name").getAsString(),
                app_permission.get("insert").getAsInt() > 0,
                app_permission.get("select").getAsInt() > 0,
                app_permission.get("update").getAsInt() > 0,
                app_permission.get("delete").getAsInt() > 0,
                app_permission.get("user_required").getAsInt() > 0,
                app_permission.get("trusted_source").getAsInt() > 0
        );
    }
}
