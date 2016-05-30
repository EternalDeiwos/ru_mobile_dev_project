package com.github.eternaldeiwos.biomapapp.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by glinklater on 2016/05/30.
 */

public class ImageDownloadHelper extends AsyncTask<Uri, Void, Bitmap> {
    private ImageView imageView;

    public ImageDownloadHelper(ImageView view) {
        imageView = view;
    }

    @Override
    protected Bitmap doInBackground(Uri... uris) {
        Uri uri = uris[0];
        Bitmap bm = null;
        try {
            InputStream in = new URL(uri.toString()).openStream();
            bm = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
