package com.github.prawncake.biomapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import eternaldeiwos.github.com.biomapapp.R;

public class SettingsActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    public void onClickSave(View view)
    {
        //TODO: WHATEVER NEEDS TO BE ADDED TO PASS CHANGES IN THE SETTINGS BACK TO MAIN ACTIVITY
        Intent returnIntent = new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    public void onClickCancel(View view)
    {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED,returnIntent);
        finish();
    }
}
