package com.github.eternaldeiwos.biomapapp.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.github.eternaldeiwos.biomapapp.helper.API;
import com.google.android.gms.vision.text.Text;
import com.orm.SugarRecord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by glinklater on 2016/05/30.
 */

public class Database extends SugarRecord {
    public String project;
    public String name;

    public Database() {}

    public Database(String project, String name) {
        this.name = name;
        this.project = project;
    }

    @Override
    public String toString() {
        return name;
    }

    public Uri getImageURI()
    {
        return Uri.parse(API.ADU_VMUS_URL + "images/" + project + "_logo.png");
    }
}
