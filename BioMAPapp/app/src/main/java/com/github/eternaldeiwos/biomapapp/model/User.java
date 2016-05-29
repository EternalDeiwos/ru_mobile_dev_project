package com.github.eternaldeiwos.biomapapp.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.orm.SugarRecord;

import java.io.IOException;

/**
 * Created by glinklater on 2016/05/28.
 */

public class User extends SugarRecord {
    public boolean success = false;
    public String token;
    public String name;
    public String surname;
    public String email;
    public String adu_number;

    public User() { /* default */ }

    public User(boolean success, String token, String name, String surname, String email, String adu_number) {
        this.success = success;
        this.token = token;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.adu_number = adu_number;
    }

    @Override
    public String toString() {
        return this.name + " " + this.surname;
    }
}
