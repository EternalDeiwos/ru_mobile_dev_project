package com.github.eternaldeiwos.biomapapp.model;

import android.net.Uri;

import com.github.eternaldeiwos.biomapapp.helper.API;
import com.orm.SugarRecord;

import java.net.URI;

/**
 * Created by glinklater on 2016/05/28.
 */

public class Project extends SugarRecord {
    public String acronym;
    public String db_name;
    public String description;
    public String date_started;

    public Project() { /* default */ }

    public Project(String acronym, String db_name, String description, String date_started)
    {
        this.acronym = acronym;
        this.db_name = db_name;
        this.description = description;
        this.date_started = date_started;
    }

    public Uri getImageURI()
    {
        return Uri.parse(API.ADU_VMUS_URL + "images/" + db_name + "_logo.png");
    }

    public String getAcronym()
    {
        return this.acronym;
    }

    @Override
    public String toString() { return db_name; }
}
