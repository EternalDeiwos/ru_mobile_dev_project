package com.github.eternaldeiwos.biomapapp.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by glinklater on 2016/05/30.
 */

public class Database extends SugarRecord {
    public String project;

    public Database() {}

    public Database(String project) {
        this.project = project;
    }
}
