package com.github.eternaldeiwos.biomapapp.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by glinklater on 2016/05/28.
 */

public class User {
    private boolean success = false;
    private String token;
    private String name;
    private String surname;
    private String email;
    private String adu_number;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdu_number() {
        return adu_number;
    }

    public void setAdu_number(String adu_number) {
        this.adu_number = adu_number;
    }

    public User() {

    }

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
