package com.github.eternaldeiwos.biomapapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.eternaldeiwos.biomapapp.helper.ImageDownloadHelper;
import com.github.eternaldeiwos.biomapapp.model.Database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by glinklater on 2016/05/30.
 */

public class DatabaseListAdapter extends BaseAdapter {
    List<Database> dbs;
    Activity mContext;

    public DatabaseListAdapter(Activity context) {
        mContext = context;
        refresh();
    }

    private void refresh() {
        try {
            dbs = Database.find(Database.class, "1 = 1");
        } catch (Exception e) {
            e.printStackTrace();
            dbs = new LinkedList<>();
        }
    }

    @Override
    public int getCount() {
        return dbs.size();
    }

    @Override
    public Object getItem(int position) {
        return dbs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dbs.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Database db = (Database) this.getItem(position);
        LayoutInflater inflater = mContext.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.single_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(db.toString());
        new ImageDownloadHelper(imageView).execute(db.getImageURI());

        return rowView;
    }
}
