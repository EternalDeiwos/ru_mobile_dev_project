package com.github.prawncake.biomapapp;

import android.app.Activity;
import android.content.Context;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.github.eternaldeiwos.biomapapp.LocationProvider;
import com.github.eternaldeiwos.biomapapp.R;
import com.github.eternaldeiwos.biomapapp.SelectLocationActivity;
import com.github.eternaldeiwos.biomapapp.model.AddressComponent;
import com.github.eternaldeiwos.biomapapp.model.Location;
import com.github.eternaldeiwos.biomapapp.model.LocationEntry;
import com.github.eternaldeiwos.biomapapp.model.LocationType;
import com.github.eternaldeiwos.biomapapp.rest.RestReverseGeocode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRecordActivity extends BaseActivity{
    public static final int ACTION_GET_LOCATION_FROM_MAP = 101;
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";

    String userChosenTask;
    int imageNum;
    TextView theText;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;

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

        LocationProvider.requestSingleUpdate(this, new LocationProvider.LocationCallback() {
            @Override
            public void onNewLocationAvailable(final android.location.Location location) {
                RestReverseGeocode.getLocation(
                        (float) location.getLatitude(),
                        (float) location.getLongitude(),
                        new Callback<Location>() {
                    @Override
                    public void onResponse(Call<Location> call, Response<Location> response) {
                        Location loc = response.body();
                        LocationEntry bestLocationEntry = loc.getBestEntry();

                        AddressComponent town = bestLocationEntry.getBestAddressComponent(
                                new LocationType[] {
                                        LocationType.SUBLOCALITY,
                                        LocationType.LOCALITY,
                                        LocationType.TOWN,
                                        LocationType.DISTRICT
                                });

                        AddressComponent province = bestLocationEntry
                                .getBestAddressComponent(LocationType.PROVINCE);

                        AddressComponent country = bestLocationEntry
                                .getBestAddressComponent(LocationType.COUNTRY);

                        Log.d("LOCATION", bestLocationEntry.address);

                        EditText mTown, mProvince, mCountry, mGPS;
                        mTown = (EditText) findViewById(R.id.Town);
                        mProvince = (EditText) findViewById(R.id.Province);
                        mCountry = (EditText) findViewById(R.id.Country);
                        mGPS = (EditText) findViewById(R.id.GPS);

                        if (mGPS != null)
                            mGPS.setText((location.getLatitude() + "," + location.getLongitude()));
                        if (mTown != null)
                            mTown.setText(town.long_name);
                        if (mProvince != null)
                            mProvince.setText(province.long_name);
                        if (mCountry != null)
                            mCountry.setText(country.long_name);
                    }

                    @Override
                    public void onFailure(Call<Location> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    public void onSelectLocationClick(View v) {
        Intent intent = new Intent(mContext, SelectLocationActivity.class);
        startActivityForResult(intent, ACTION_GET_LOCATION_FROM_MAP);
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
            switch (requestCode) {
                case SELECT_FILE:
                    onSelectFromGalleryResult(data);
                    break;
                case REQUEST_CAMERA:
                    onCaptureImageResult(data);
                    break;
                case ACTION_GET_LOCATION_FROM_MAP:
                    onGoogleMapsResult(data);
                    break;
            }
        }
    }

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

        if (pic1 != null)
            savedInstanceState.putString("Picture1", pic1.getText().toString());
        if (pic2 != null)
            savedInstanceState.putString("Picture2", pic2.getText().toString());
        if (pic3 != null)
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
        if (pic1 != null)
            pic1.setText(savedInstanceState.getString("Picture1"));
        if (pic2 != null)
            pic2.setText(savedInstanceState.getString("Picture2"));
        if (pic3 != null)
            pic3.setText(savedInstanceState.getString("Picture3"));
    }

    public void onGoogleMapsResult(Intent intent) {
        Bundle results = intent.getExtras();
        final float lat = results.getFloat(KEY_LAT);
        final float lng = results.getFloat(KEY_LNG);
        Log.d("MAPS", String.format(Locale.US, "lat: %.6f; lng: %.6f;", lat, lng));
        RestReverseGeocode.getLocation(lat, lng, new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location loc = response.body();
                LocationEntry bestLocationEntry = loc.getBestEntry();

                AddressComponent town = bestLocationEntry.getBestAddressComponent(
                        new LocationType[] {
                                LocationType.SUBLOCALITY,
                                LocationType.LOCALITY,
                                LocationType.TOWN,
                                LocationType.DISTRICT
                        });

                AddressComponent province = bestLocationEntry
                        .getBestAddressComponent(LocationType.PROVINCE);

                AddressComponent country = bestLocationEntry
                        .getBestAddressComponent(LocationType.COUNTRY);

                Log.d("LOCATION", bestLocationEntry.address);

                EditText mTown, mProvince, mCountry, mGPS;
                mTown = (EditText) findViewById(R.id.Town);
                mProvince = (EditText) findViewById(R.id.Province);
                mCountry = (EditText) findViewById(R.id.Country);
                mGPS = (EditText) findViewById(R.id.GPS);

                if (mGPS != null)
                    mGPS.setText(String.format(Locale.US, "%.6f,%.6f", lat, lng));
                if (mTown != null)
                    mTown.setText(town.long_name);
                if (mProvince != null)
                    mProvince.setText(province.long_name);
                if (mCountry != null)
                    mCountry.setText(country.long_name);
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
