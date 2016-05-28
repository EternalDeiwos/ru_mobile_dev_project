package com.github.eternaldeiwos.biomapapp.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by glinklater on 2016/05/28.
 */

public class Permission {
    private String app_name;
    private boolean insert = false;
    private boolean select = false;
    private boolean update = false;
    private boolean delete = false;
    private boolean user_required = true;
    private boolean trusted = false;

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isUser_required() {
        return user_required;
    }

    public void setUser_required(boolean user_required) {
        this.user_required = user_required;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public Permission(String app_name, boolean insert, boolean select, boolean update, boolean delete, boolean user_required, boolean trusted) {
        this.app_name = app_name;
        this.insert = insert;
        this.select = select;
        this.update = update;
        this.delete = delete;
        this.user_required = user_required;
        this.trusted = trusted;
    }

    public Permission() {

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(app_name);
        sb.append(": ");
        sb.append(insert);
        sb.append(select);
        sb.append(update);
        sb.append(delete);

        return sb.toString();
    }
}
