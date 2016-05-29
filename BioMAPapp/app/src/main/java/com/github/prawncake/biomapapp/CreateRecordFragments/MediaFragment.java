package com.github.prawncake.biomapapp.CreateRecordFragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.eternaldeiwos.biomapapp.R;
import com.github.prawncake.biomapapp.DatePickerFragment;

public class MediaFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_media, container, false);
    }

}
