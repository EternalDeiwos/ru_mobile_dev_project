package com.github.prawncake.biomapapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.eternaldeiwos.biomapapp.Authenticator;
import com.github.eternaldeiwos.biomapapp.AuthenticatorActivity;
import com.github.eternaldeiwos.biomapapp.LocationProvider;
import com.github.eternaldeiwos.biomapapp.R;
import com.github.eternaldeiwos.biomapapp.SelectLocationActivity;
import com.github.eternaldeiwos.biomapapp.model.AddressComponent;
import com.github.eternaldeiwos.biomapapp.model.Location;
import com.github.eternaldeiwos.biomapapp.model.LocationEntry;
import com.github.eternaldeiwos.biomapapp.model.LocationType;
import com.github.eternaldeiwos.biomapapp.model.Record;
import com.github.eternaldeiwos.biomapapp.rest.RestReverseGeocode;
import com.github.eternaldeiwos.biomapapp.rest.RestUpload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import okhttp3.ResponseBody;
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
    Activity mContext;

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
                if(tab.getText()=="Location")
                    setLocationEditTexts();
                if(tab.getText()=="Specimen")
                    setSpecimenTexts();
//                if(tab.getText()=="Media")
//                    setMediaTexts();

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

    public void submitRecord(View v)
    {
        Record submission = pullDataFromActivity();
//        submission.debug();
        if (verifyEssentialData(submission))
            RestUpload.uploadRecord(this, submission, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
                {
                    int res = response.code();
                    Log.d("RESPONSE", res + "");
                    ResponseBody body = response.body();
                    try {
                        System.err.println(body != null ? body.string() : response.message());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.err.println(body != null ? response.raw().message() : "");
                    if (res==200)
                    {
                        Toast.makeText(mContext, "Yippy", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, "Didn't work :(", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t)
                {
                    t.printStackTrace();
                }
            });
    }

    public boolean verifyEssentialData(Record r)
    {
        return true;
    }

    String mGPS;
    String mCountry ;
    String mProvince;
    String mTown ;
    String mLocality;

    String mSpecies ;
    String mSpeciesDescription;
//    String mDate;
    String mNestCount;

    public void setSpecimenTexts()
    {
        mSpecies = ((EditText) findViewById(R.id.Species)).getText().toString();
        mSpeciesDescription = ((EditText) findViewById(R.id.Species)).getText().toString();
        mNestCount =((EditText) findViewById(R.id.NestCount)).getText().toString();

    }
    public void setLocationEditTexts()
    {
        mCountry = ((EditText) findViewById(R.id.Country)).getText().toString();
        mProvince = ((EditText) findViewById(R.id.Province)).getText().toString();
        mTown = ((EditText) findViewById(R.id.Town)).getText().toString();
        mLocality = ((EditText) findViewById(R.id.Locality)).getText().toString();
        mGPS = ((EditText) findViewById(R.id.GPS)).getText().toString();

    }

    public Record pullDataFromActivity()
    {
        final Record r = new Record();
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccountsByType(AuthenticatorActivity.ACCOUNT_TYPE);
        Account acc = null;
        if (accounts != null && accounts.length > 0) acc = accounts[0];
        else throw new IllegalArgumentException();

//        EditText mCountry = (EditText) findViewById(R.id.Country);
//        EditText mProvince = (EditText) findViewById(R.id.Province);
//        EditText mTown = (EditText) findViewById(R.id.Town);
//        EditText mLocality = (EditText) findViewById(R.id.Locality);
//        EditText mSpecies = (EditText) findViewById(R.id.Species);
//        EditText mSpeciesDescription = (EditText) findViewById(R.id.SpeciesDescription);
//        EditText mGPS = (EditText) findViewById(R.id.GPS);
        EditText mDate = (EditText) findViewById(R.id.CaptureDate);
//        EditText mNestCount = (EditText) findViewById(R.id.NestCount);

        TextView mPicture1 = (TextView) findViewById(R.id.picture1);
        TextView mPicture2 = (TextView) findViewById(R.id.picture2);
        TextView mPicture3 = (TextView) findViewById(R.id.picture3);

        Spinner mStatus = (Spinner) findViewById(R.id.Status);
        Spinner mNestSite = (Spinner) findViewById(R.id.NestSite);
        CheckBox mRoadkill = (CheckBox) findViewById(R.id.Roadkill);

        // Meta
        r.username = am.getUserData(acc, Authenticator.KEY_USER_NAME) + " " +
                am.getUserData(acc, Authenticator.KEY_USER_SURNAME);
        r.userid = am.getUserData(acc, Authenticator.KEY_ADU_NUMBER);
        r.email = am.getUserData(acc, Authenticator.KEY_USER_EMAIL);
        r.project = getIntent().getStringExtra("project_acronym");
        r.token = "";

        AccountManagerFuture<Bundle> b = am.getAuthToken(acc, AuthenticatorActivity.ARG_AUTH_TYPE, null, this, null, null);
        try
        {
            r.token = b.getResult().getString(AccountManager.KEY_AUTHTOKEN);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //pics
        int count = 0;
        String[] pics_s = new String[3];
        String pic_default = "Click to add picture";
        pics_s[0] = mPicture1.getText().toString();
        pics_s[1] = mPicture2.getText().toString();
        pics_s[2] = mPicture3.getText().toString();
        for (String s : pics_s) {
            if (!(TextUtils.isEmpty(s) || s.equals(pic_default))) count++;
        }
        r.images = new Uri[count];
        count = 0;
        for (String s : pics_s) {
            if (!(TextUtils.isEmpty(s) || s.equals(pic_default)))
                r.images[count++] = Uri.parse(s);
        }

        // Text fields
//        r.country = mCountry != null ? mCountry.getText().toString() : "";
        r.country = mCountry;
        r.province = mProvince;
        r.nearesttown = mTown;
        r.locality = mLocality;
        r.userdet = mSpecies;
        r.note = mSpeciesDescription;

        // gps
        String[] latlng = mGPS != null ? mGPS.split(",") : null;
        if (latlng != null) {
            r.lat = Float.parseFloat(latlng[0]);
            r.lng = Float.parseFloat(latlng[1]);
        }
        r.source = Record.GPS_SOURCE_PHONE;

        // date
        String[] date = mDate.getText().toString().split("/");
        if (date != null) {
            r.day = Integer.parseInt(date[0]);
            r.month = Integer.parseInt(date[1]);
            r.year = Integer.parseInt(date[2]);
        }

        // misc
        r.nestcount = Integer.parseInt(mNestCount);
        r.nestsite = mNestSite != null ? mNestSite.getSelectedItem().toString() : "";
        r.roadkill = mRoadkill.isChecked();
        r.recordbasis = Record.BASIS_PHOTO;
        r.recordstatus = Record.OccurrenceStatus.getEnum(mStatus.getSelectedItem().toString());

        //nulling
        r.collection_code = "";
        r.institution_code = "";
        r.observers = "";
        r.taxonid = "";
        r.taxonname = "";

        return r;
    }
}
