package com.github.prawncake.biomapapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.github.eternaldeiwos.biomapapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
public class CreateRecordActivity extends BaseActivity{
    public static final int ACTION_GET_LOCATION_FROM_MAP = 101;

    String userChosenTask;
    int imageNum;
    TextView theText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Location"));
        tabLayout.addTab(tabLayout.newTab().setText("Specimen"));
        tabLayout.addTab(tabLayout.newTab().setText("Media"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
    }



    public void showDatePickerDialog(View view)
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickImage1(View view)
    {
        imageNum = 1;
        selectImage();
    }

    public void onClickImage2(View view)
    {
        imageNum = 2;
        selectImage();
    }

    public void onClickImage3(View view)
    {
        imageNum = 3;
        selectImage();
    }

    public void selectImage()
    {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateRecordActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int item)
            {
                boolean result=Utility.checkPermission(CreateRecordActivity.this);
                if (items[item].equals("Take Photo"))
                {
                    userChosenTask="Take Photo";
                    cameraIntent();
                }
                else if (items[item].equals("Choose from Library"))
                {
                    userChosenTask="Choose from Library";
                    galleryIntent();
                }
                else if (items[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        // invoke the image gallery using an implict intent.
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        // where do we want to find the data?
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        // finally, get a URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        // set the data and type.  Get all image types.
        photoPickerIntent.setDataAndType(data, "image/*");

        // we will invoke this activity, and get something back from it.
        startActivityForResult(photoPickerIntent, SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(userChosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChosenTask.equals("Choose from Library"))
                        galleryIntent();
                }
                else
                {
                    //code for deny
                } break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data)
    {
        if (imageNum==1)
            theText = (TextView)findViewById(R.id.picture1);
        if (imageNum==2)
            theText = (TextView)findViewById(R.id.picture2);
        if (imageNum==3)
            theText = (TextView)findViewById(R.id.picture3);


        Uri imageUri = data.getData();
        String location = imageUri.toString();
        theText.setText(location);
    }

    private void onCaptureImageResult(Intent data)
    {
        if (imageNum==1)
            theText = (TextView)findViewById(R.id.picture1);
        if (imageNum==2)
            theText = (TextView)findViewById(R.id.picture2);
        if (imageNum==3)
            theText = (TextView)findViewById(R.id.picture3);

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try
        {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        theText.setText(Uri.fromFile(destination).toString());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        TextView pic1 = (TextView)findViewById(R.id.picture1);
        TextView pic2 = (TextView)findViewById(R.id.picture2);
        TextView pic3 = (TextView)findViewById(R.id.picture3);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putString("Picture1", pic1.getText().toString());
        savedInstanceState.putString("Picture2", pic2.getText().toString());
        savedInstanceState.putString("Picture3", pic3.getText().toString());

        // etc.
        super.onSaveInstanceState(savedInstanceState);
    }
    //onRestoreInstanceState
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        TextView pic1 = (TextView)findViewById(R.id.picture1);
        TextView pic2 = (TextView)findViewById(R.id.picture2);
        TextView pic3 = (TextView)findViewById(R.id.picture3);
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        pic1.setText(savedInstanceState.getString("Picture1"));
        pic2.setText(savedInstanceState.getString("Picture2"));
        pic3.setText(savedInstanceState.getString("Picture3"));
    }
}
