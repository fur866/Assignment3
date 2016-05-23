package com.example.fahad.assignment3.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fahad.assignment3.R;

/**
 * Created by Fahad on 23/05/2016.
 */
public class SatelliteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.satellite_fragment_view, container,false);
        return view;
    }
}
