package com.github.prawncake.biomapapp.CreateRecordFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eternaldeiwos.github.com.biomapapp.R;

public class SpecimenFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_specimen, container, false);
    }

}
