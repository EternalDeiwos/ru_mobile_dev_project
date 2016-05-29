package com.github.eternaldeiwos.biomapapp.model;

import com.github.eternaldeiwos.biomapapp.helper.API;

/**
 * Created by glinklater on 2016/05/28.
 */

public class Project {
    private String acronym;
    private String db_name;
    private String description;
    private String date_started;

    public Project() {
    }

    public Project(String acronym, String db_name, String description, String date_started) {
        this.acronym = acronym;
        this.db_name = db_name;
        this.description = description;
        this.date_started = date_started;
    }

    public String getProjectID() { return db_name; }

    public String getImageURL() {
        return API.ADU_VMUS_URL + "images/" + db_name + "_logo.png";
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_started() {
        return date_started;
    }

    public void setDate_started(String date_started) {
        this.date_started = date_started;
    }

    public String getAcronym() {

        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    @Override
    public String toString() {
        return acronym;
    }
}
