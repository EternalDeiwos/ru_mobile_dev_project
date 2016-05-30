package com.github.prawncake.biomapapp;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.eternaldeiwos.biomapapp.R;
import com.github.eternaldeiwos.biomapapp.helper.ImageDownloadHelper;
import com.github.eternaldeiwos.biomapapp.model.Project;

import java.io.File;
import java.util.Map;

/**
 * Created by g11f0364 on 2016-05-22.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class custom_database_list extends ArrayAdapter<String>
{

    private final Activity context;
    private final String[] projectnames;
    private final Uri[] imageId;

    public custom_database_list(Activity context,String[] projectnames,Uri[] imageId)
    {
        super(context, R.layout.single_list_item, projectnames);
        this.context = context;
        this.projectnames = projectnames;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.single_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(projectnames[position]);

        new ImageDownloadHelper(imageView).execute(imageId[position]);
        return rowView;
    }
}
