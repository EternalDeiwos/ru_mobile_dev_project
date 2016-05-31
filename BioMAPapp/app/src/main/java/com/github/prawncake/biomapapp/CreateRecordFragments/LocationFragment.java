package com.github.prawncake.biomapapp.CreateRecordFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.eternaldeiwos.biomapapp.R;
import com.github.prawncake.biomapapp.CreateRecordActivity;

public class LocationFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }


}
